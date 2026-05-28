package com.inovagab.presentation.manager.projects

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
import com.inovagab.presentation.adapters.ProjectAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProjetosFragment : Fragment() {

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
        header.tvHeaderTitle.text = getString(R.string.projects)
        val app = requireActivity().application as InnovaNexusApplication
        viewLifecycleOwner.lifecycleScope.launch {
            app.projectRepository.observeProjects().collectLatest { projects ->
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.adapter = ProjectAdapter(
                    projects,
                    showEditButton = true,
                    onEditClick = { project ->
                        findNavController().navigate(
                            R.id.action_to_editar_projeto,
                            Bundle().apply { putString("projectId", project.id) }
                        )
                    }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
