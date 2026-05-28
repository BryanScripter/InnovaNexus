package com.inovagab.presentation.components

import androidx.annotation.DrawableRes

data class MenuItem(
    val id: String,
    val title: String,
    val description: String,
    @DrawableRes val iconRes: Int,
    val iconBackgroundColorRes: Int,
    val iconTintRes: Int,
    val badgeCount: Int? = null
)
