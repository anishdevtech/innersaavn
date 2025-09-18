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
import dev.anishsharma.kreate.extentions.innersaavn.SaavnProvider

class SaavnProviderIntegrationTest {

  private val http = HttpClient(CIO) {
    install(ContentNegotiation) {
      json(Json {
        ignoreUnknownKeys = true; isLenient = true
      })
    }
  }

  // Prefer explicit import of the real package; fall back to fully-qualified name if needed
  private val provider = /* dev.anishsharma.kreate.providers.saavn. */ SaavnProvider(http)

  @Test
  fun search_returns_results_for_common_query() = runBlocking {
    val results = provider.search("arijit singh", 3)
    assertTrue(results.isNotEmpty())
    println("First result = ${results.first()}")
    // ...
  }

  @Test
  fun getByUrl_resolves_when_env_provided() = runBlocking {
    val url = System.getenv("JIOSAAVN_TEST_URL") ?: return@runBlocking
    val track = provider.getByUrl(url)
assertNotNull(track)
println("Resolved track = $track")  }
}