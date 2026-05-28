package com.inovagab.presentation.operator.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.inovagab.InnovaNexusApplication
import com.inovagab.R
import com.inovagab.databinding.FragmentOperatorHomeBinding
import com.inovagab.presentation.adapters.MenuAdapter
import com.inovagab.presentation.components.MenuItem
import com.inovagab.utils.SessionManager
import com.inovagab.utils.UiHelpers

class OperatorHomeFragment : Fragment() {

    private var _binding: FragmentOperatorHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOperatorHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val session = SessionManager(requireContext())
        val app = requireActivity().application as InnovaNexusApplication

        binding.tvGreeting.text = getString(R.string.good_morning).let {
            UiHelpers.greeting(requireContext())
        }
        binding.tvUserName.text = UiHelpers.firstName(session.getUserName().orEmpty())

        app.authRepository.let {
            binding.tvIdeasSent.text = "12"
            binding.tvIdeasApproved.text = "8"
            binding.tvScore.text = getString(R.string.points_format, 850)
        }

        val menuItems = listOf(
            MenuItem("estrategias", getString(R.string.company_strategies), getString(R.string.company_strategies_desc),
                R.drawable.ic_target, R.color.primary_10, R.color.primary),
            MenuItem("nova-ideia", getString(R.string.new_idea), getString(R.string.new_idea_desc),
                R.drawable.ic_lightbulb, R.color.accent_10, R.color.accent),
            MenuItem("minhas-ideias", getString(R.string.my_ideas), getString(R.string.my_ideas_desc),
                R.drawable.ic_file, R.color.info_10, R.color.info),
            MenuItem("ranking", getString(R.string.ranking), getString(R.string.ranking_desc),
                R.drawable.ic_trophy, R.color.warning_10, R.color.warning)
        )

        binding.rvMenu.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMenu.adapter = MenuAdapter(menuItems) { item ->
            val action = when (item.id) {
                "estrategias" -> R.id.action_to_estrategias
                "nova-ideia" -> R.id.action_to_nova_ideia
                "minhas-ideias" -> R.id.action_to_minhas_ideias
                else -> R.id.action_to_ranking
            }
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
