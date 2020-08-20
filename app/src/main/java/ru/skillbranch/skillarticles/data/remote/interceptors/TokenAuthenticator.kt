package ru.skillbranch.skillarticles.data.remote.interceptors

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Header
import retrofit2.http.POST
import ru.skillbranch.skillarticles.AppConfig
import ru.skillbranch.skillarticles.data.JsonConverter
import ru.skillbranch.skillarticles.data.local.PrefManager
import ru.skillbranch.skillarticles.data.remote.NetworkManager
import ru.skillbranch.skillarticles.data.remote.RestService
import ru.skillbranch.skillarticles.data.remote.req.RefreshReq
import ru.skillbranch.skillarticles.data.remote.res.AuthRes
import java.util.concurrent.TimeUnit

class TokenAuthenticator : Authenticator {
    private val preferences = PrefManager
    private val network = NetworkManager

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = preferences.refreshToken
        val auth = network.api.refreshToken(RefreshReq(refreshToken)).execute()
        val result = auth.body() ?: return response.request
        preferences.accessToken = "Bearer ${result.accessToken}"
        preferences.refreshToken = result.refreshToken
        return response.request.newBuilder()
            .header("Authorization", "Bearer ${result.accessToken}")
            .build()

    }

}


