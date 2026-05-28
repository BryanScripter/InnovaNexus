package com.inovagab.presentation.leadership.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.inovagab.InnovaNexusApplication
import com.inovagab.R
import com.inovagab.databinding.FragmentDashboardBinding
import com.inovagab.databinding.IncludeScreenHeaderBinding
import com.inovagab.databinding.ItemDashboardChartBinding
import com.inovagab.databinding.ItemDashboardChartPieBinding
import com.inovagab.databinding.ItemDashboardKpiBinding
import com.inovagab.domain.model.ProjectStatus
import com.inovagab.utils.UiHelpers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val header = IncludeScreenHeaderBinding.bind(binding.header.root)
        header.tvHeaderTitle.text = getString(R.string.executive_dashboard)
        header.btnBack.visibility = View.VISIBLE
        header.btnBack.setOnClickListener { findNavController().navigateUp() }

        val app = requireActivity().application as InnovaNexusApplication
        viewLifecycleOwner.lifecycleScope.launch {
            val dashboard = app.dashboardRepository.getDashboard()
            val projects = app.projectRepository.observeProjects().first()

            bindKpi(binding.kpiRoi.root, R.drawable.ic_trending, R.color.primary_5, R.color.primary,
                getString(R.string.roi_total), "${dashboard.roiTotal.toInt()}%")
            bindKpi(binding.kpiEconomy.root, R.drawable.ic_coins, R.color.accent_5, R.color.accent,
                getString(R.string.economy), UiHelpers.formatCurrency(dashboard.economiaGerada))
            bindKpi(binding.kpiProjects.root, R.drawable.ic_briefcase, R.color.info_5, R.color.info,
                getString(R.string.proj_active_short), dashboard.projetosAtivos.toString())
            bindKpi(binding.kpiIdeas.root, R.drawable.ic_lightbulb, R.color.warning_5, R.color.warning,
                getString(R.string.approved_short), dashboard.ideiasAprovadas.toString())

            setupRoiChart(projects)
            setupPieChart(projects)
            setupLineChart()
        }
    }

    private fun bindKpi(
        includeRoot: View,
        iconRes: Int,
        bgColorRes: Int,
        iconTintRes: Int,
        label: String,
        value: String
    ) {
        val kpi = ItemDashboardKpiBinding.bind(includeRoot)
        kpi.cardKpi.setCardBackgroundColor(ContextCompat.getColor(requireContext(), bgColorRes))
        kpi.ivKpiIcon.setImageResource(iconRes)
        kpi.ivKpiIcon.setColorFilter(ContextCompat.getColor(requireContext(), iconTintRes))
        kpi.tvKpiLabel.text = label
        kpi.tvKpiValue.text = value
    }

    private fun setupRoiChart(projects: List<com.inovagab.domain.model.Project>) {
        val chartBinding = ItemDashboardChartBinding.bind(binding.chartRoi.root)
        chartBinding.tvChartTitle.text = getString(R.string.roi_by_project)
        val barChart = BarChart(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        chartBinding.chartContainer.addView(barChart)

        val entries = projects.mapIndexed { index, project ->
            val roi = ((project.retornoEsperado - project.investimento) / project.investimento * 100).toFloat()
            BarEntry(index.toFloat(), roi)
        }
        val labels = projects.map { it.nome.split(" ").first() }
        val dataSet = BarDataSet(entries, "").apply {
            color = ContextCompat.getColor(requireContext(), R.color.primary)
            setDrawValues(false)
        }
        barChart.data = BarData(dataSet)
        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.axisRight.isEnabled = false
        barChart.xAxis.apply {
            valueFormatter = com.github.mikephil.charting.formatter.IndexAxisValueFormatter(labels)
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 11f
            granularity = 1f
        }
        barChart.invalidate()
    }

    private fun setupPieChart(projects: List<com.inovagab.domain.model.Project>) {
        val pieBinding = ItemDashboardChartPieBinding.bind(binding.chartPie.root)
        val grouped = linkedMapOf(
            ProjectStatus.PLANEJAMENTO to projects.count { it.status == ProjectStatus.PLANEJAMENTO },
            ProjectStatus.EXECUCAO to projects.count { it.status == ProjectStatus.EXECUCAO },
            ProjectStatus.CONCLUIDO to projects.count { it.status == ProjectStatus.CONCLUIDO },
            ProjectStatus.PAUSADO to projects.count { it.status == ProjectStatus.PAUSADO }
        ).filter { it.value > 0 }

        val entries = grouped.map { PieEntry(it.value.toFloat(), it.key.label) }
        val colors = grouped.keys.map { status ->
            when (status) {
                ProjectStatus.PLANEJAMENTO -> ContextCompat.getColor(requireContext(), R.color.warning)
                ProjectStatus.EXECUCAO -> ContextCompat.getColor(requireContext(), R.color.info)
                ProjectStatus.CONCLUIDO -> ContextCompat.getColor(requireContext(), R.color.success)
                ProjectStatus.PAUSADO -> ContextCompat.getColor(requireContext(), R.color.muted_foreground)
            }
        }
        val dataSet = PieDataSet(entries, "").apply {
            this.colors = colors
            sliceSpace = 4f
            setDrawValues(false)
        }
        pieBinding.pieChartStatus.apply {
            data = PieData(dataSet)
            description.isEnabled = false
            legend.isEnabled = false
            holeRadius = 45f
            transparentCircleRadius = 50f
            invalidate()
        }
        pieBinding.legendContainer.removeAllViews()
        grouped.forEach { (status, count) ->
            val dotColor = when (status) {
                ProjectStatus.PLANEJAMENTO -> R.color.warning
                ProjectStatus.EXECUCAO -> R.color.info
                ProjectStatus.CONCLUIDO -> R.color.success
                ProjectStatus.PAUSADO -> R.color.muted_foreground
            }
            val item = layoutInflater.inflate(R.layout.item_chart_legend, pieBinding.legendContainer, false)
            item.findViewById<View>(R.id.legendDot).setBackgroundColor(ContextCompat.getColor(requireContext(), dotColor))
            (item.findViewById<android.widget.TextView>(R.id.legendLabel)).text = "${status.label} ($count)"
            pieBinding.legendContainer.addView(item)
        }
    }

    private fun setupLineChart() {
        val chartBinding = ItemDashboardChartBinding.bind(binding.chartLine.root)
        chartBinding.tvChartTitle.text = getString(R.string.ideas_per_month)
        val lineChart = LineChart(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        chartBinding.chartContainer.addView(lineChart)
        val months = listOf("Jan", "Fev", "Mar", "Abr", "Mai", "Jun")
        val values = listOf(5f, 8f, 12f, 10f, 15f, 18f)
        val entries = values.mapIndexed { i, v -> Entry(i.toFloat(), v) }
        val dataSet = LineDataSet(entries, "").apply {
            color = ContextCompat.getColor(requireContext(), R.color.primary)
            setCircleColor(ContextCompat.getColor(requireContext(), R.color.primary))
            lineWidth = 2f
            circleRadius = 4f
            setDrawValues(false)
        }
        lineChart.data = LineData(dataSet)
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.apply {
            valueFormatter = com.github.mikephil.charting.formatter.IndexAxisValueFormatter(months)
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 11f
            granularity = 1f
        }
        lineChart.axisLeft.setDrawGridLines(true)
        lineChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
