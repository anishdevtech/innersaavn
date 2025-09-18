package dev.anishsharma.kreate.providers.saavn

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Integration tests that hit saavn.dev to validate the SaavnProvider wiring.
 * Set JIOSAAVN_TEST_URL (env) to a real JioSaavn song URL to enable the URL test.
 */
class SaavnProviderIntegrationTest {

  private val http = HttpClient(CIO) {
    install(ContentNegotiation) {
      json(Json {
        ignoreUnknownKeys = true; isLenient = true
      })
    }
    // Uncomment to debug HTTP traffic
  install(io.ktor.client.plugins.logging.Logging) { level = io.ktor.client.plugins.logging.LogLevel.ALL }
  }

  private val provider = SaavnProvider(http)

  @Test
  fun search_returns_results_for_common_query() = runBlocking {
    val results = provider.search(query = "arijit singh", limit = 5)
    assertTrue("Expected some results for 'arijit singh'", results.isNotEmpty())
    // At least one result should look sane
    val first = results.first()
    assertTrue("Title should not be blank", first.title.isNotBlank())
  }

  @Test
  fun getByUrl_resolves_known_song_when_env_provided() = runBlocking {
    val url = System.getenv("JIOSAAVN_TEST_URL") ?: return@runBlocking
    val track = provider.getByUrl(url)
    assertNotNull("Expected track for provided JioSaavn URL", track)
    assertTrue("Resolved title should not be blank", track!!.title.isNotBlank())
  }
}