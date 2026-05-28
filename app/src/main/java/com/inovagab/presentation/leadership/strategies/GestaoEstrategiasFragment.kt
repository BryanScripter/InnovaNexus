package com.inovagab.presentation.leadership.strategies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.inovagab.InnovaNexusApplication
import com.inovagab.R
import com.inovagab.databinding.FragmentGestaoEstrategiasBinding
import com.inovagab.databinding.IncludeScreenHeaderBinding
import com.inovagab.domain.model.Strategy
import com.inovagab.presentation.adapters.StrategyAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GestaoEstrategiasFragment : Fragment() {

    private var _binding: FragmentGestaoEstrategiasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGestaoEstrategiasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val header = IncludeScreenHeaderBinding.bind(binding.header.root)
        val showBack = findNavController().previousBackStackEntry?.destination?.id == R.id.leadershipHomeFragment
        header.btnBack.isVisible = showBack
        header.btnBack.setOnClickListener { findNavController().navigateUp() }
        header.tvHeaderTitle.text = getString(R.string.strategic_management)
        val app = requireActivity().application as InnovaNexusApplication
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            app.strategyRepository.observeStrategies().collectLatest { strategies ->
                binding.recyclerView.adapter = StrategyAdapter(
                    strategies,
                    showActions = true,
                    onEdit = { showStrategyDialog(it) },
                    onDelete = { strategy ->
                        viewLifecycleOwner.lifecycleScope.launch {
                            app.strategyRepository.deleteStrategy(strategy)
                        }
                    }
                )
            }
        }
        binding.fabAdd.setOnClickListener { showStrategyDialog(null) }
    }

    private fun showStrategyDialog(strategy: Strategy?) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(if (strategy == null) R.string.add_strategy else R.string.edit_strategy)
            .setMessage("Funcionalidade de formulário preparada para API.")
            .setPositiveButton(R.string.save, null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
