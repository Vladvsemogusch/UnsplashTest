package cc.anisimov.vlad.unsplashtest.data.network

import cc.anisimov.vlad.core.common.util.readResource
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

class UnsplashServiceTest {
    private lateinit var server: MockWebServer
    private lateinit var service: UnsplashService

    @Before
    fun setUp() {
        val jsonConfig = Json { ignoreUnknownKeys = true }

        server = MockWebServer().apply { start() }

        val okHttpClient =
            OkHttpClient
                .Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()

        val retrofit =
            Retrofit
                .Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(jsonConfig.asConverterFactory("application/json; charset=UTF-8".toMediaType()))
                .client(okHttpClient)
                .build()

        service = retrofit.create(UnsplashService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `parses list and sends page query`() =
        runTest {
            // Given
            val photoListJson = readResource("sample_photo_list.json")
            server.enqueue(
                MockResponse()
                    .setBody(photoListJson)
                    .setHeader("Content-Type", "application/json"),
            )

            // When
            val out = service.getLatestPhotos(page = 3)

            // Then
            assertEquals(1, out.size)
            assertEquals("1RSKs-JtG3Y", out.first().id)
            assertEquals("/photos?page=3", server.takeRequest().path)
        }

    // Checks serialization setup
    @Test
    fun `malformed JSON throws SerializationException`() =
        runTest {
            // Given
            server.enqueue(
                MockResponse()
                    .setBody("{ not valid json")
                    .setHeader("Content-Type", "application/json"),
            )

            // When/Then
            // Or assertFailsWith from kotlin.test
            try {
                service.getLatestPhotos(1)
                fail("Expected SerializationException")
            } catch (_: SerializationException) {
                // expected
            } catch (t: Throwable) {
                fail("Unexpected exception: $t")
            }
        }
}
