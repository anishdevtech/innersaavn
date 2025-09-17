package dev.anishsharma.kreate.extentions.innersaavn

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Disambiguate Ktor client logging types
import io.ktor.client.plugins.logging.Logging as ClientLogging
import io.ktor.client.plugins.logging.LogLevel as ClientLogLevel
import io.ktor.client.plugins.logging.Logger as ClientLogger

class SaavnApi(
  baseUrl: String,
  client: HttpClient? = null
) {
  private val json = Json { ignoreUnknownKeys = true; isLenient = true } [web:1274]

  private val http = client ?: HttpClient {
    install(ContentNegotiation) { json(json) } [web:1274]
    install(ClientLogging) {
      // Use a custom logger to avoid platform binding issues and DEFAULT symbol differences
      logger = object : ClientLogger {
        override fun log(message: String) {
          println(message)
        }
      } [web:1276]
      level = ClientLogLevel.INFO
    } [web:1274]
    defaultRequest { contentType(ContentType.Application.Json) } [web:1274]
  }

  private val root = if (baseUrl.endsWith("/")) baseUrl else "$baseUrl/" [web:1274]

  suspend fun searchSongs(query: String, page: Int = 1): SaavnSearchSongsResponse =
    http.get("${root}search/songs") {
      parameter("query", query); parameter("page", page)
    }.body() [web:1274]

  suspend fun searchAlbums(query: String, page: Int = 1): SaavnSearchAlbumsResponse =
    http.get("${root}search/albums") {
      parameter("query", query); parameter("page", page)
    }.body() [web:1274]

  suspend fun searchPlaylists(query: String, page: Int = 1): SaavnSearchPlaylistsResponse =
    http.get("${root}search/playlists") {
      parameter("query", query); parameter("page", page)
    }.body() [web:1274]

  suspend fun songDetails(id: String): SaavnSongDetailsResponse =
    http.get("${root}songs") {
      parameter("id", id)
    }.body() [web:1274]

  suspend fun albumDetails(id: String): SaavnAlbumDetailsResponse =
    http.get("${root}albums") {
      parameter("id", id)
    }.body() [web:1274]

  suspend fun playlistDetails(id: String): SaavnPlaylistDetailsResponse =
    http.get("${root}playlists") {
      parameter("id", id)
    }.body() [web:1274]
}
