package com.inovagab.presentation.operator.strategies

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
import com.inovagab.databinding.FragmentScreenListBinding
import com.inovagab.databinding.IncludeScreenHeaderBinding
import com.inovagab.presentation.adapters.StrategyAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EstrategiasFragment : Fragment() {

    private var _binding: FragmentScreenListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentScreenListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val header = IncludeScreenHeaderBinding.bind(binding.header.root)
        header.tvHeaderTitle.text = getString(R.string.strategies_title)
        header.tvHeaderSubtitle.text = getString(R.string.strategies_subtitle)
        header.tvHeaderSubtitle.visibility = View.VISIBLE
        binding.recyclerView.setPadding(0, 0, 0, 32)
        header.btnBack.setOnClickListener { findNavController().navigateUp() }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val app = requireActivity().application as InnovaNexusApplication
        viewLifecycleOwner.lifecycleScope.launch {
            app.strategyRepository.observeStrategies().collectLatest { strategies ->
                binding.recyclerView.adapter = StrategyAdapter(strategies)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
