package com.vienotes.repository

import TokenGenMutation
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.smartledge.vienotes.BuildConfig

class TokenRepository(
    private val apolloClient: ApolloClient
) {

    suspend fun getNewToken(): String {
        var token = ""
        try {
            val response = apolloClient.mutate(
                TokenGenMutation(
                    apiKey = BuildConfig.API_KEY,
                    userName = BuildConfig.API_USERNAME
                )
            ).await()
            token = response.data?.generateAccessToken ?: ""


        } catch (e: ApolloException) {
            // handle protocol errors
            e.printStackTrace()
        }
        return token
    }

}