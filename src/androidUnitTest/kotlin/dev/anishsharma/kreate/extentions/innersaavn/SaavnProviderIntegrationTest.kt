package dev.anishsharma.kreate.extentions.innersaavn

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

// Replace this import with the provider’s real package found via git grep
// import dev.anishsharma.kreate.extentions.innersaavn.saavn.SaavnProvider

class SaavnProviderIntegrationTest {

    private val http = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true; isLenient = true })
        }
    }

    // Use the fully-qualified name if you prefer to avoid a fragile import:
    private val provider = /* dev.anishsharma.kreate.extentions.innersaavn.saavn. */ SaavnProvider(http)

    @Test
    fun search_returns_results_for_common_query() = runBlocking {
        val results = provider.search(query = "arijit singh", limit = 3)
        assertTrue("Expected some results", results.isNotEmpty())
        // Don’t assert field names here; print the first item to see its properties
        println("First result type=${results.first()::class} value=${results.first()}")
    }

    @Test
    fun getByUrl_resolves_when_env_provided() = runBlocking {
        val url = System.getenv("JIOSAAVN_TEST_URL") ?: return@runBlocking
        val track = provider.getByUrl(url)
        assertNotNull("Expected a track for provided URL", track)
        // Check any non-empty label-like field commonly used by your model
        val label = track?.let { t ->
            // adapt these to your actual model fields after you inspect println output
            runCatching { t::class.members.find { it.name == "title" }?.call(t) as? String }.getOrNull()
                ?: runCatching { t::class.members.find { it.name == "name" }?.call(t) as? String }.getOrNull()
        } ?: ""
        println("Resolved track type=${track!!::class} label=$label value=$track")
        assertTrue("Resolved label should not be blank", label.isNotBlank())
    }
}
