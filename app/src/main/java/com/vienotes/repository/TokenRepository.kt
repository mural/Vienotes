package com.vienotes.repository

import TokenGenMutation
import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.vienotes.manager.CoroutinesManager

class TokenRepository(
    val context: Context,
    private val coroutinesManager: CoroutinesManager,
    private val apolloClient: ApolloClient
) {

    suspend fun getNewToken(): String {
        var token = ""
        try {
            val response = apolloClient.mutate(TokenGenMutation()).await()
            token = response.data?.generateAccessToken ?: ""


        } catch (e: ApolloException) {
            // handle protocol errors
            e.printStackTrace()
        }
        return token
    }

}
//
//private fun generateToken() {
//    val apolloClient = ApolloClient.builder()
//        .serverUrl("https://380odjc5vi.execute-api.us-east-1.amazonaws.com/dev/graphql")
//        .build()
//    coroutineScope.launch {
//        val response = try {
//            apolloClient.mutate(TokenGenMutation()).await()
//        } catch (e: ApolloException) {
//            // handle protocol errors
//            e.printStackTrace()
//            return@launch
//        }
//
//        val launch = response.data?.generateAccessToken
//        if (launch == null || response.hasErrors()) {
//            // handle application errors
//            Log.d(this.toString(), "token response error")
//            return@launch
//        }
//
//        // launch now contains a typesafe model of your data
//        Log.d(this.toString(), "Token: ${response.data!!.generateAccessToken}")
//
//        MainActivity.accessTokenTemporal = response.data?.generateAccessToken!!
//    }
//}