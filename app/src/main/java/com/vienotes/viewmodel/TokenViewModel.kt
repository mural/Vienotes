package com.vienotes.viewmodel

import androidx.lifecycle.ViewModel
import com.vienotes.manager.CoroutinesManager
import com.vienotes.repository.TokenRepository

class TokenViewModel(
    private val coroutinesManager: CoroutinesManager,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    suspend fun getAccessToken(): String {

        return tokenRepository.getNewToken()
    }
}