package dev.anishsharma.kreate.extentions.innersaavn

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlinx.coroutines.test.runTest

class SaavnApiTest {

  private fun mockClient(handler: (HttpRequestData) -> String): HttpClient {
    val engine = MockEngine {
      req ->
      val json = handler(req)
      respond(
        content = json,
        status = HttpStatusCode.OK,
        headers = Headers.build {
          append(HttpHeaders.ContentType, "application/json")
        }
      )
    }
    return HttpClient(engine) {
      install(ContentNegotiation) {
        json(Json {
          ignoreUnknownKeys = true; isLenient = true
        })
      }
    }
  }

  @Test
  fun searchSongs_parsesResults() = runTest {
    val client = mockClient {
      req ->
      require(req.url.encodedPath.endsWith("/search/songs"))
      """{
        "results": [
          {
            "id": "1",
            "title": "Test",
            "album": "A",
            "artist": "X",
            "duration": 200,
            "image": "http://img",
            "downloadUrl": [ { "quality": "160kbps", "url": "http://u" } ]
          }
        ],
        "total": 1
      }"""
    }
    val api = SaavnApi(baseUrl = "https://example/", client = client)
    val repo = SaavnRepository(api)
    val list = repo.searchSongs("q")
    assertEquals(1, list.size)
    val s = list.first()
    assertEquals("1", s.id)
    assertEquals("Test", s.title)
    assertEquals("http://u", s.streamUrl)
    assertEquals("160kbps", s.bitrate)
  }

  @Test
  fun songDetails_handlesEmpty() = runTest {
    val client = mockClient {
      """{ "songs": [] }"""
    }
    val api = SaavnApi(baseUrl = "https://example/", client = client)
    try {
      api.songDetails("id")
      assert(false) {
        "Expected error when song not found"
      }
    } catch (e: IllegalStateException) {
      assertNotNull(e)
    }
  }
}