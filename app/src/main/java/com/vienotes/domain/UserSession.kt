package com.vienotes.domain

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson

class UserSession(context: Context) {

    companion object {
        private const val USER_PREFERENCE = "com.vienotes.USER_PREFERENCE"
    }

    lateinit var user: User
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        getSavedUser()
    }

    private fun createUser(): User {
        return User(token = "")
    }

    fun getSavedUser(): User {
        val gson = Gson()
        val json: String? = preferences.getString(USER_PREFERENCE, "")

        json?.let {
            if (it.isNotEmpty()) {
                try {
                    user = gson.fromJson(json, User::class.java)
                } catch (e: IllegalStateException) {
                }
            }
        }
        if (!::user.isInitialized) {
            user = createUser()
        }
        return user
    }

    fun saveUser(): Boolean {
        val json = Gson().toJson(user)
        return preferences.edit().putString(USER_PREFERENCE, json).commit()
    }

    fun logout(): Boolean {
        user = createUser()
        return preferences.edit().remove(USER_PREFERENCE).commit()
    }

    fun saveUserToken(accessToken: String): Boolean {
        user.token = accessToken
        return saveUser()
    }

    fun getToken(): String {
        return user.token
    }

    fun isToken(): Boolean {
        return user.token.isNotEmpty()
    }

}