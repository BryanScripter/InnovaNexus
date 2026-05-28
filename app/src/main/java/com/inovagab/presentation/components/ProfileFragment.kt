package com.inovagab.presentation.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.inovagab.InnovaNexusApplication
import com.inovagab.R
import com.inovagab.databinding.FragmentProfileBinding
import com.inovagab.databinding.IncludeScreenHeaderBinding
import com.inovagab.databinding.ItemProfileInfoBinding
import com.inovagab.utils.SessionManager
import com.inovagab.utils.UiHelpers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val header = IncludeScreenHeaderBinding.bind(binding.header.root)
        header.tvHeaderTitle.text = getString(R.string.profile)
        header.btnBack.isVisible = false

        val session = SessionManager(requireContext())
        val app = requireActivity().application as InnovaNexusApplication
        viewLifecycleOwner.lifecycleScope.launch {
            val user = session.getUserId()?.let { app.userRepository.getUserById(it) }
            user?.let {
                binding.tvName.text = it.name
                binding.tvCargo.text = it.cargo
                binding.tvInitials.text = UiHelpers.initials(it.name)
                bindInfoCard(binding.cardEmail.root, R.drawable.ic_mail, "Email", it.email, R.color.primary_10, R.color.primary)
                bindInfoCard(binding.cardCargo.root, R.drawable.ic_briefcase, "Cargo", it.cargo, R.color.info_10, R.color.info)
                bindInfoCard(binding.cardScore.root, R.drawable.ic_trophy, "Pontuação", getString(R.string.points_profile, it.pontuacao), R.color.warning_10, R.color.warning)
            }
        }

        binding.cardLogout.setOnClickListener {
            session.clearSession()
            findNavController().popBackStack(R.id.loginFragment, false)
        }
    }

    private fun bindInfoCard(
        cardRoot: View,
        icon: Int,
        label: String,
        value: String,
        bgColor: Int,
        tint: Int
    ) {
        val card = ItemProfileInfoBinding.bind(cardRoot)
        card.ivIcon.setImageResource(icon)
        card.ivIcon.setColorFilter(ContextCompat.getColor(requireContext(), tint))
        card.ivIcon.setBackgroundColor(ContextCompat.getColor(requireContext(), bgColor))
        card.tvLabel.text = label
        card.tvValue.text = value
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
