package dev.innersaavn.providers.saavn

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SaavnProviderIntegrationTest {

    private val http = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true; isLenient = true })
        }
        // For debugging HTTP, uncomment:
        // install(io.ktor.client.plugins.logging.Logging) {
        //     level = io.ktor.client.plugins.logging.LogLevel.ALL
        // }
    }

    private val provider = SaavnProvider(http)

    @Test
    fun search_returns_results_for_common_query() = runBlocking {
        val results = provider.search(query = "arijit singh", limit = 5)
        assertTrue("Expected some results", results.isNotEmpty())
    }

    @Test
    fun getByUrl_resolves_when_env_provided() = runBlocking {
        val url = System.getenv("JIOSAAVN_TEST_URL") ?: return@runBlocking
        val track = provider.getByUrl(url)
        assertNotNull("Expected a track for provided URL", track)
        assertTrue(track!!.title.isNotBlank())
    }
}
