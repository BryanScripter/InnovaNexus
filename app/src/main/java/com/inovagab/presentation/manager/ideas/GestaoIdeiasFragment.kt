package com.inovagab.presentation.manager.ideas

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
import com.inovagab.domain.model.IdeaStatus
import com.inovagab.presentation.adapters.IdeaAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GestaoIdeiasFragment : Fragment() {

    private var _binding: FragmentScreenListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentScreenListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val header = IncludeScreenHeaderBinding.bind(binding.header.root)
        val showBack = findNavController().previousBackStackEntry?.destination?.id == R.id.managerHomeFragment
        header.btnBack.isVisible = showBack
        header.btnBack.setOnClickListener { findNavController().navigateUp() }
        header.tvHeaderTitle.text = getString(R.string.idea_management)
        val app = requireActivity().application as InnovaNexusApplication
        viewLifecycleOwner.lifecycleScope.launch {
            app.ideaRepository.observeIdeas().collectLatest { ideas ->
                val pending = ideas.count { it.status == IdeaStatus.EM_ANALISE }
                header.tvHeaderSubtitle.isVisible = true
                header.tvHeaderSubtitle.text = getString(R.string.ideas_pending_count, pending)
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.adapter = IdeaAdapter(ideas, showManagerMeta = true) { idea ->
                    findNavController().navigate(
                        R.id.action_to_detalhe_ideia,
                        Bundle().apply { putString("ideaId", idea.id) }
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
