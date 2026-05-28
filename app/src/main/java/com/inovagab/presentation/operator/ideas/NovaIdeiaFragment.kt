package com.inovagab.presentation.operator.ideas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.inovagab.R
import com.inovagab.databinding.FragmentNovaIdeiaBinding
import com.inovagab.databinding.IncludeScreenHeaderBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NovaIdeiaFragment : Fragment() {

    private var _binding: FragmentNovaIdeiaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNovaIdeiaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val header = IncludeScreenHeaderBinding.bind(binding.header.root)
        header.tvHeaderTitle.text = getString(R.string.new_idea)
        header.btnBack.setOnClickListener { findNavController().navigateUp() }
        binding.btnSubmit.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                delay(1000)
                Toast.makeText(requireContext(), R.string.idea_sent_success, Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
