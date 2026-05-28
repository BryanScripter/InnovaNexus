package com.inovagab.utils

import android.content.Context
import com.inovagab.domain.model.User
import com.inovagab.domain.model.UserRole

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    fun saveSession(user: User, token: String = "mock_token") {
        prefs.edit()
            .putString(Constants.KEY_AUTH_TOKEN, token)
            .putString(Constants.KEY_USER_ID, user.id)
            .putString(Constants.KEY_USER_ROLE, user.role.value)
            .putString(Constants.KEY_USER_NAME, user.name)
            .putString(Constants.KEY_USER_EMAIL, user.email)
            .apply()
    }

    fun getUserId(): String? = prefs.getString(Constants.KEY_USER_ID, null)

    fun getUserRole(): UserRole? = prefs.getString(Constants.KEY_USER_ROLE, null)?.let {
        UserRole.from(it)
    }

    fun getUserName(): String? = prefs.getString(Constants.KEY_USER_NAME, null)

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean = getUserId() != null
}
