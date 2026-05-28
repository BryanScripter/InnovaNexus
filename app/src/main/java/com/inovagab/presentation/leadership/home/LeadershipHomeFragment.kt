package com.inovagab.presentation.leadership.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.inovagab.InnovaNexusApplication
import com.inovagab.R
import com.inovagab.databinding.FragmentLeadershipHomeBinding
import com.inovagab.presentation.adapters.MenuAdapter
import com.inovagab.presentation.components.MenuItem
import com.inovagab.utils.SessionManager
import com.inovagab.utils.UiHelpers
import kotlinx.coroutines.launch

class LeadershipHomeFragment : Fragment() {

    private var _binding: FragmentLeadershipHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLeadershipHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val session = SessionManager(requireContext())
        binding.tvGreeting.text = UiHelpers.greeting(requireContext())
        binding.tvUserName.text = UiHelpers.firstName(session.getUserName().orEmpty())
        val app = requireActivity().application as InnovaNexusApplication
        viewLifecycleOwner.lifecycleScope.launch {
            val dashboard = app.dashboardRepository.getDashboard()
            binding.tvRoi.text = "${dashboard.roiTotal.toInt()}%"
            binding.tvActiveProjects.text = dashboard.projetosAtivos.toString()
            binding.tvEconomy.text = UiHelpers.formatCurrency(dashboard.economiaGerada)
        }
        val menuItems = listOf(
            MenuItem("estrategias", getString(R.string.nav_strategies), getString(R.string.manage_guidelines),
                R.drawable.ic_target, R.color.primary_10, R.color.primary),
            MenuItem("projetos", getString(R.string.projects), getString(R.string.projects_overview_desc),
                R.drawable.ic_briefcase, R.color.info_10, R.color.info),
            MenuItem("dashboard", getString(R.string.executive_dashboard), getString(R.string.executive_dashboard_desc),
                R.drawable.ic_chart, R.color.accent_10, R.color.accent)
        )
        binding.rvMenu.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMenu.adapter = MenuAdapter(menuItems) { item ->
            val action = when (item.id) {
                "estrategias" -> R.id.action_to_gestao_estrategias
                "projetos" -> R.id.action_to_projetos_lideranca
                else -> R.id.action_to_dashboard
            }
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
