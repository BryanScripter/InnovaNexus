package com.inovagab.presentation.operator.ideas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.inovagab.InnovaNexusApplication
import com.inovagab.R
import com.inovagab.databinding.FragmentScreenListBinding
import com.inovagab.databinding.IncludeScreenHeaderBinding
import com.inovagab.presentation.adapters.IdeaAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MinhasIdeiasFragment : Fragment() {

    private var _binding: FragmentScreenListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentScreenListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val header = IncludeScreenHeaderBinding.bind(binding.header.root)
        val showBack = findNavController().previousBackStackEntry?.destination?.id == R.id.operatorHomeFragment
        header.tvHeaderTitle.text = if (showBack) getString(R.string.my_ideas) else getString(R.string.nav_ideas)
        header.btnBack.isVisible = showBack
        header.btnBack.setOnClickListener { findNavController().navigateUp() }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val app = requireActivity().application as InnovaNexusApplication
        viewLifecycleOwner.lifecycleScope.launch {
            app.ideaRepository.observeIdeas().collectLatest { ideas ->
                binding.recyclerView.adapter = IdeaAdapter(ideas)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
