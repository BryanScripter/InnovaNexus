package com.inovagab.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.inovagab.InnovaNexusApplication
import com.inovagab.R
import com.inovagab.databinding.ActivityLoginBinding
import com.inovagab.domain.model.UserRole
import com.inovagab.utils.SessionManager
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private val app get() = requireActivity().application as InnovaNexusApplication
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())

        binding.btnLogin.setOnClickListener { login(UserRole.OPERATOR) }
        binding.btnDemoOperator.setOnClickListener { login(UserRole.OPERATOR) }
        binding.btnDemoManager.setOnClickListener { login(UserRole.MANAGER) }
        binding.btnDemoLeadership.setOnClickListener { login(UserRole.LEADERSHIP) }
    }

    private fun login(role: UserRole) {
        viewLifecycleOwner.lifecycleScope.launch {
            setLoading(true)
            app.authRepository.login(
                binding.etEmail.text?.toString().orEmpty(),
                binding.etPassword.text?.toString().orEmpty(),
                role
            ).onSuccess { user ->
                sessionManager.saveSession(user)
                navigateToRole(role)
            }
            setLoading(false)
        }
    }

    private fun navigateToRole(role: UserRole) {
        val action = when (role) {
            UserRole.OPERATOR -> R.id.action_login_to_operator
            UserRole.MANAGER -> R.id.action_login_to_manager
            UserRole.LEADERSHIP -> R.id.action_login_to_leadership
        }
        findNavController().navigate(action)
    }

    private fun setLoading(loading: Boolean) {
        binding.btnLogin.isEnabled = !loading
        binding.btnDemoOperator.isEnabled = !loading
        binding.btnDemoManager.isEnabled = !loading
        binding.btnDemoLeadership.isEnabled = !loading
        binding.progressLogin.isVisible = loading
        binding.btnLogin.isVisible = !loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
