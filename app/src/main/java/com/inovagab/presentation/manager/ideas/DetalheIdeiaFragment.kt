package com.inovagab.presentation.manager.ideas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.inovagab.InnovaNexusApplication
import com.inovagab.databinding.FragmentDetalheIdeiaBinding
import com.inovagab.databinding.IncludeScreenHeaderBinding
import com.inovagab.utils.UiHelpers
import kotlinx.coroutines.launch

class DetalheIdeiaFragment : Fragment() {

    private var _binding: FragmentDetalheIdeiaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetalheIdeiaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val header = IncludeScreenHeaderBinding.bind(binding.header.root)
        header.btnBack.setOnClickListener { findNavController().navigateUp() }
        header.tvHeaderTitle.text = "Detalhe da Ideia"
        val ideaId = arguments?.getString("ideaId") ?: return
        val app = requireActivity().application as InnovaNexusApplication
        viewLifecycleOwner.lifecycleScope.launch {
            app.ideaRepository.getIdeaById(ideaId)?.let { idea ->
                binding.tvTitle.text = idea.titulo
                binding.tvDescription.text = idea.descricao
                UiHelpers.applyIdeaStatusChip(requireContext(), binding.chipStatus, idea.status)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
