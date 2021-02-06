package com.vienotes.network

import android.content.Context
import com.vienotes.MainActivity
import com.vienotes.domain.UserSession
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(val context: Context, private val userSession: UserSession) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                userSession.getToken()
            )
            .build()

        return chain.proceed(request)
    }
}