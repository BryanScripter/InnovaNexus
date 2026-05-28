package com.inovagab.presentation.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.inovagab.databinding.FragmentRoleContainerBinding

abstract class RoleContainerFragment : Fragment() {

    private var _binding: FragmentRoleContainerBinding? = null
    protected val binding get() = _binding!!

    @get:NavigationRes
    protected abstract val navGraphRes: Int

    @get:MenuRes
    protected abstract val bottomMenuRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoleContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = if (savedInstanceState == null) {
            NavHostFragment.create(navGraphRes).also { host ->
                childFragmentManager.beginTransaction()
                    .replace(com.inovagab.R.id.role_nav_host, host)
                    .setPrimaryNavigationFragment(host)
                    .commitNow()
            }
        } else {
            childFragmentManager.findFragmentById(com.inovagab.R.id.role_nav_host) as NavHostFragment
        }

        val navController = navHostFragment.navController
        binding.bottomNavigation.menu.clear()
        binding.bottomNavigation.inflateMenu(bottomMenuRes)
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.visibility =
                if (destination.id in bottomNavDestinations()) View.VISIBLE else View.GONE
        }
    }

    protected abstract fun bottomNavDestinations(): Set<Int>

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
