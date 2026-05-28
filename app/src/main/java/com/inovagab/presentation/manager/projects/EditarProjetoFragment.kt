package com.inovagab.presentation.manager.projects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.inovagab.InnovaNexusApplication
import com.inovagab.R
import com.inovagab.databinding.FragmentEditarProjetoBinding
import com.inovagab.databinding.IncludeScreenHeaderBinding
import kotlinx.coroutines.launch

class EditarProjetoFragment : Fragment() {

    private var _binding: FragmentEditarProjetoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditarProjetoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val header = IncludeScreenHeaderBinding.bind(binding.header.root)
        header.tvHeaderTitle.text = "Editar Projeto"
        header.btnBack.setOnClickListener { findNavController().navigateUp() }
        val projectId = arguments?.getString("projectId") ?: return
        val app = requireActivity().application as InnovaNexusApplication
        viewLifecycleOwner.lifecycleScope.launch {
            app.projectRepository.getProjectById(projectId)?.let { project ->
                binding.etName.setText(project.nome)
                binding.etDescription.setText(project.descricao)
                binding.sliderProgress.value = project.progresso.toFloat()
            }
        }
        binding.btnSave.setOnClickListener {
            Toast.makeText(requireContext(), R.string.project_saved, Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
