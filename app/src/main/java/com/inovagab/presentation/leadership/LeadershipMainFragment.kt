package com.inovagab.presentation.leadership

import com.inovagab.R
import com.inovagab.presentation.common.RoleContainerFragment

class LeadershipMainFragment : RoleContainerFragment() {
    override val navGraphRes: Int = R.navigation.nav_leadership
    override val bottomMenuRes: Int = R.menu.bottom_nav_leadership
    override fun bottomNavDestinations(): Set<Int> = setOf(
        R.id.leadershipHomeFragment,
        R.id.gestaoEstrategiasFragment,
        R.id.projetosLiderancaFragment,
        R.id.profileFragment
    )
}
