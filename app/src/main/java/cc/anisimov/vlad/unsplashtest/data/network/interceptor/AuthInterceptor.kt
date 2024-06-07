package cc.anisimov.vlad.unsplashtest.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", "Client-ID $CLIENT_ID")
                .build()
        )
    }

    companion object {
        const val CLIENT_ID = "A_EZ1V9bRfQCRXwEmDK_0gVs6nRwXIbrxc8Rd7OB3N0"
    }
}