package com.inovagab.presentation.manager

import com.inovagab.R
import com.inovagab.presentation.common.RoleContainerFragment

class ManagerMainFragment : RoleContainerFragment() {
    override val navGraphRes: Int = R.navigation.nav_manager
    override val bottomMenuRes: Int = R.menu.bottom_nav_manager
    override fun bottomNavDestinations(): Set<Int> = setOf(
        R.id.managerHomeFragment,
        R.id.gestaoIdeiasFragment,
        R.id.projetosFragment,
        R.id.profileFragment
    )
}
