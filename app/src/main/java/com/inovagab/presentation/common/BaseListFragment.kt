package com.inovagab.presentation.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inovagab.databinding.FragmentScreenListBinding
import com.inovagab.databinding.IncludeScreenHeaderBinding

abstract class BaseListFragment : Fragment() {

    private var _binding: FragmentScreenListBinding? = null
    protected val binding get() = _binding!!

    protected abstract val screenTitle: String
    protected open val screenSubtitle: String? = null
    protected open val showBackButton: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreenListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val header = IncludeScreenHeaderBinding.bind(binding.header.root)
        header.tvHeaderTitle.text = screenTitle
        header.tvHeaderSubtitle.isVisible = screenSubtitle != null
        screenSubtitle?.let { header.tvHeaderSubtitle.text = it }
        header.btnBack.isVisible = showBackButton
        header.btnBack.setOnClickListener { findNavController().navigateUp() }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = createAdapter()
    }

    protected abstract fun createAdapter(): RecyclerView.Adapter<*>

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
