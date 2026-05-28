package com.inovagab.presentation.manager.projects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.inovagab.InnovaNexusApplication
import com.inovagab.R
import com.inovagab.databinding.FragmentIndicadoresBinding
import com.inovagab.databinding.IncludeScreenHeaderBinding
import com.inovagab.databinding.ItemDashboardChartBinding
import com.inovagab.databinding.ItemDashboardChartPieBinding
import com.inovagab.databinding.ItemDashboardKpiBinding
import com.inovagab.domain.model.IdeaStatus
import com.inovagab.domain.model.ProjectStatus
import com.inovagab.utils.UiHelpers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class IndicadoresFragment : Fragment() {

    private var _binding: FragmentIndicadoresBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentIndicadoresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val header = IncludeScreenHeaderBinding.bind(binding.header.root)
        header.tvHeaderTitle.text = getString(R.string.indicators)
        header.btnBack.visibility = View.VISIBLE
        header.btnBack.setOnClickListener { findNavController().navigateUp() }

        val app = requireActivity().application as InnovaNexusApplication
        viewLifecycleOwner.lifecycleScope.launch {
            val projects = app.projectRepository.observeProjects().first()
            val ideas = app.ideaRepository.observeIdeas().first()
            val totalInv = projects.sumOf { it.investimento }
            val totalRet = projects.sumOf { it.retornoEsperado }
            val roi = if (totalInv > 0) ((totalRet - totalInv) / totalInv * 100) else 0.0

            bindKpi(binding.kpiRoi.root, R.drawable.ic_trending, R.color.primary_5, R.color.primary, "ROI", "${roi.toInt()}%")
            bindKpi(binding.kpiEconomy.root, R.drawable.ic_coins, R.color.accent_5, R.color.accent, getString(R.string.economy), UiHelpers.formatCurrency(totalRet - totalInv))
            bindKpi(binding.kpiProjects.root, R.drawable.ic_briefcase, R.color.info_5, R.color.info, getString(R.string.projects), projects.size.toString())
            bindKpi(binding.kpiIdeas.root, R.drawable.ic_lightbulb, R.color.warning_5, R.color.warning, getString(R.string.approved_short), ideas.count { it.status == IdeaStatus.APROVADA }.toString())

            setupPie(projects)
            setupBar(projects)
        }
    }

    private fun bindKpi(root: View, icon: Int, bg: Int, tint: Int, label: String, value: String) {
        val kpi = ItemDashboardKpiBinding.bind(root)
        kpi.cardKpi.setCardBackgroundColor(ContextCompat.getColor(requireContext(), bg))
        kpi.ivKpiIcon.setImageResource(icon)
        kpi.ivKpiIcon.setColorFilter(ContextCompat.getColor(requireContext(), tint))
        kpi.tvKpiLabel.text = label
        kpi.tvKpiValue.text = value
        kpi.tvKpiValue.textSize = 20f
    }

    private fun setupPie(projects: List<com.inovagab.domain.model.Project>) {
        val pie = ItemDashboardChartPieBinding.bind(binding.chartPie.root)
        val grouped = ProjectStatus.entries.associateWith { s -> projects.count { it.status == s } }.filter { it.value > 0 }
        val entries = grouped.map { PieEntry(it.value.toFloat(), it.key.label.take(6)) }
        val colors = grouped.keys.map { status ->
            when (status) {
                ProjectStatus.PLANEJAMENTO -> ContextCompat.getColor(requireContext(), R.color.warning)
                ProjectStatus.EXECUCAO -> ContextCompat.getColor(requireContext(), R.color.info)
                ProjectStatus.CONCLUIDO -> ContextCompat.getColor(requireContext(), R.color.success)
                ProjectStatus.PAUSADO -> ContextCompat.getColor(requireContext(), R.color.muted_foreground)
            }
        }
        pie.pieChartStatus.data = PieData(PieDataSet(entries, "").apply { this.colors = colors; sliceSpace = 4f })
        pie.pieChartStatus.holeRadius = 50f
        pie.pieChartStatus.description.isEnabled = false
        pie.pieChartStatus.legend.isEnabled = false
        pie.pieChartStatus.invalidate()
    }

    private fun setupBar(projects: List<com.inovagab.domain.model.Project>) {
        val chartCard = ItemDashboardChartBinding.bind(binding.chartBar.root)
        chartCard.tvChartTitle.text = getString(R.string.projects_by_status)
        val barChart = BarChart(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        chartCard.chartContainer.addView(barChart)
        val data = listOf(
            "Planej." to projects.count { it.status == ProjectStatus.PLANEJAMENTO }.toFloat(),
            "Exec." to projects.count { it.status == ProjectStatus.EXECUCAO }.toFloat(),
            "Concl." to projects.count { it.status == ProjectStatus.CONCLUIDO }.toFloat(),
            "Paus." to projects.count { it.status == ProjectStatus.PAUSADO }.toFloat()
        )
        val entries = data.mapIndexed { i, pair -> BarEntry(i.toFloat(), pair.second) }
        val labels = data.map { it.first }
        barChart.data = BarData(BarDataSet(entries, "").apply {
            colors = listOf(
                ContextCompat.getColor(requireContext(), R.color.warning),
                ContextCompat.getColor(requireContext(), R.color.info),
                ContextCompat.getColor(requireContext(), R.color.success),
                ContextCompat.getColor(requireContext(), R.color.muted_foreground)
            )
            setDrawValues(false)
        })
        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.axisRight.isEnabled = false
        chartCard.chartContainer.layoutParams.height = (200 * resources.displayMetrics.density).toInt()
        barChart.xAxis.valueFormatter = com.github.mikephil.charting.formatter.IndexAxisValueFormatter(labels)
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.granularity = 1f
        barChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
