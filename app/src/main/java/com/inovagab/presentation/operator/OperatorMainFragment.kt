package com.inovagab.presentation.operator

import com.inovagab.R
import com.inovagab.presentation.common.RoleContainerFragment

class OperatorMainFragment : RoleContainerFragment() {
    override val navGraphRes: Int = R.navigation.nav_operator
    override val bottomMenuRes: Int = R.menu.bottom_nav_operator
    override fun bottomNavDestinations(): Set<Int> = setOf(
        R.id.operatorHomeFragment,
        R.id.minhasIdeiasFragment,
        R.id.profileFragment
    )
}
