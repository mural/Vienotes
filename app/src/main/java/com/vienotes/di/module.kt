package com.vienotes.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.vienotes.domain.UserSession
import com.vienotes.manager.CoroutinesManager
import com.vienotes.network.AuthorizationInterceptor
import com.vienotes.repository.TasksRepository
import com.vienotes.repository.TokenRepository
import com.vienotes.viewmodel.TasksViewModel
import com.vienotes.viewmodel.TokenViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val homeModule = module {
    viewModel {
        TasksViewModel(get(), get())
    }
    viewModel {
        TokenViewModel(get(), get())
    }

    single { CoroutinesManager() }
    single { UserSession(get()) }
}

val networkModule = module {

    single { provideApolloClient(get(), get()) }

    single { TasksRepository(get()) }
    single { TokenRepository(get()) }

    factory { AuthorizationInterceptor(get(), get()) }
}

fun provideApolloClient(context: Context, userSession: UserSession): ApolloClient {
    return ApolloClient.builder()
        .serverUrl("https://380odjc5vi.execute-api.us-east-1.amazonaws.com/dev/graphql/")
        .okHttpClient(
            OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor(context, userSession))
                .build()
        )
        .build()
}
