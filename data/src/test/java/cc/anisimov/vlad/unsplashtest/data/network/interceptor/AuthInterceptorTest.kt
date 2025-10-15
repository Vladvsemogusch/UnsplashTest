package cc.anisimov.vlad.unsplashtest.data.network.interceptor

import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Test

class AuthInterceptorTest {
    @Test
    fun `adds Authorization header`() =
        runTest {
            // Given
            val testServer = MockWebServer().apply { start() }
            val httpClient =
                OkHttpClient
                    .Builder()
                    .addInterceptor(AuthInterceptor())
                    .build()

            // When
            try {
                testServer.enqueue(MockResponse().setBody("{}"))
                val request = Request.Builder().url(testServer.url("/")).build()
                httpClient.newCall(request).execute().close()

                val recordedRequest = testServer.takeRequest()
                // Then (Client id should be stored in secure way)
                assertEquals(
                    "Client-ID ${AuthInterceptor.CLIENT_ID}",
                    recordedRequest.getHeader("Authorization"),
                )
            } finally {
                testServer.shutdown()
            }
        }
}
