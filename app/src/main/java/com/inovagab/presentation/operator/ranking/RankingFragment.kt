package com.inovagab.presentation.operator.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.inovagab.R
import com.inovagab.data.local.MockRankingData
import com.inovagab.databinding.FragmentRankingBinding
import com.inovagab.databinding.IncludeScreenHeaderBinding
import com.inovagab.presentation.adapters.RankingAdapter
import com.inovagab.utils.UiHelpers

class RankingFragment : Fragment() {

    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val header = IncludeScreenHeaderBinding.bind(binding.header.root)
        header.tvHeaderTitle.text = getString(R.string.ranking)
        header.btnBack.visibility = View.VISIBLE
        header.btnBack.setOnClickListener { findNavController().navigateUp() }

        val items = MockRankingData.items
        if (items.size >= 3) {
            binding.tvFirstInitial.text = UiHelpers.singleInitial(items[0].nome)
            binding.tvFirstName.text = UiHelpers.firstName(items[0].nome)
            binding.tvFirstPoints.text = getString(R.string.points_format, items[0].pontuacao)
            binding.tvSecondInitial.text = UiHelpers.singleInitial(items[1].nome)
            binding.tvSecondName.text = UiHelpers.firstName(items[1].nome)
            binding.tvSecondPoints.text = getString(R.string.points_format, items[1].pontuacao)
            binding.tvThirdInitial.text = UiHelpers.singleInitial(items[2].nome)
            binding.tvThirdName.text = UiHelpers.firstName(items[2].nome)
            binding.tvThirdPoints.text = getString(R.string.points_format, items[2].pontuacao)
        }

        binding.rvRankingList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRankingList.adapter = RankingAdapter(items.drop(3), startRank = 4)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
