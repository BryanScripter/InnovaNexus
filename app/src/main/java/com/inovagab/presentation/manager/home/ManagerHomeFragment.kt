package com.inovagab.presentation.manager.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.inovagab.InnovaNexusApplication
import com.inovagab.R
import com.inovagab.databinding.FragmentOperatorHomeBinding
import com.inovagab.domain.model.IdeaStatus
import com.inovagab.domain.model.ProjectStatus
import com.inovagab.presentation.adapters.MenuAdapter
import com.inovagab.presentation.components.MenuItem
import com.inovagab.utils.SessionManager
import com.inovagab.utils.UiHelpers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ManagerHomeFragment : Fragment() {

    private var _binding: FragmentOperatorHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOperatorHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val session = SessionManager(requireContext())
        val app = requireActivity().application as InnovaNexusApplication
        val ctx = requireContext()

        binding.tvGreeting.text = UiHelpers.greeting(requireContext())
        binding.tvUserName.text = UiHelpers.firstName(session.getUserName().orEmpty())
        binding.tvScoreLabel.visibility = View.GONE
        binding.chipTop10.visibility = View.GONE
        binding.tvScore.visibility = View.GONE
        binding.cardScore.visibility = View.GONE

        binding.cardStatFirst.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.warning_5))
        binding.cardStatSecond.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.info_5))
        binding.flStatIconFirst.setBackgroundResource(R.drawable.bg_circle_warning_10)
        binding.flStatIconSecond.setBackgroundResource(R.drawable.bg_circle_info_10)
        binding.ivStatIconFirst.setImageResource(R.drawable.ic_clock)
        binding.ivStatIconSecond.setImageResource(R.drawable.ic_rocket)
        binding.ivStatIconFirst.setColorFilter(ContextCompat.getColor(ctx, R.color.warning))
        binding.ivStatIconSecond.setColorFilter(ContextCompat.getColor(ctx, R.color.info))

        viewLifecycleOwner.lifecycleScope.launch {
            val ideas = app.ideaRepository.observeIdeas().first()
            val projects = app.projectRepository.observeProjects().first()
            val pending = ideas.count { it.status == IdeaStatus.EM_ANALISE }
            val active = projects.count { it.status == ProjectStatus.EXECUCAO }
            binding.tvIdeasSent.text = pending.toString()
            binding.tvIdeasApproved.text = active.toString()
            binding.tvStatLabel1.text = getString(R.string.pending_analysis)
            binding.tvStatLabel2.text = getString(R.string.active_projects)
            val menuItems = listOf(
                MenuItem("ideias", getString(R.string.pending_ideas), getString(R.string.pending_ideas_desc),
                    R.drawable.ic_file, R.color.warning_10, R.color.warning, pending),
                MenuItem("projetos", getString(R.string.projects), getString(R.string.projects_desc),
                    R.drawable.ic_briefcase, R.color.info_10, R.color.info, active),
                MenuItem("indicadores", getString(R.string.indicators), getString(R.string.indicators_desc),
                    R.drawable.ic_chart, R.color.accent_10, R.color.accent)
            )
            binding.rvMenu.layoutManager = LinearLayoutManager(requireContext())
            binding.rvMenu.adapter = MenuAdapter(menuItems) { item ->
                val action = when (item.id) {
                    "ideias" -> R.id.action_to_gestao_ideias
                    "projetos" -> R.id.action_to_projetos
                    else -> R.id.action_to_indicadores
                }
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
