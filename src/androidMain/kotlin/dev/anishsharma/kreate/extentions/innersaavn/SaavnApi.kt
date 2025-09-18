package dev.anishsharma.kreate.extentions.innersaavn

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * Base URL must include the "api/" suffix, e.g., "https://saavn.dev/api/".
 */
class SaavnApi(
    baseUrl: String,
    private val http: HttpClient
) {
    private val root = if (baseUrl.endsWith("/")) baseUrl else "$baseUrl/"

    suspend fun searchSongs(query: String, page: Int = 1): SaavnSearchSongsResponse =
        http.get("${root}search/songs") {
            parameter("query", query)
            parameter("page", page)
        }.body()

    suspend fun songDetails(id: String): SaavnSongDetailsResponse =
        http.get("${root}songs") {
            parameter("id", id)
        }.body()

    // Optional additional endpoints if needed
    suspend fun searchAlbums(query: String, page: Int = 1): SaavnSearchAlbumsResponse =
        http.get("${root}search/albums") {
            parameter("query", query)
            parameter("page", page)
        }.body()

    suspend fun searchPlaylists(query: String, page: Int = 1): SaavnSearchPlaylistsResponse =
        http.get("${root}search/playlists") {
            parameter("query", query)
            parameter("page", page)
        }.body()

    suspend fun albumDetails(id: String): SaavnAlbumDetailsResponse =
        http.get("${root}albums") {
            parameter("id", id)
        }.body()

    suspend fun playlistDetails(id: String): SaavnPlaylistDetailsResponse =
        http.get("${root}playlists") {
            parameter("id", id)
        }.body()
}

/** Response types used above; customize to your schema or reuse SaavnModels if shapes match. */
typealias SaavnSearchSongsResponse = SaavnSearchResponse
typealias SaavnSongDetailsResponse = SaavnSongResponse
typealias SaavnSearchAlbumsResponse = SaavnSearchResponse
typealias SaavnSearchPlaylistsResponse = SaavnSearchResponse
typealias SaavnAlbumDetailsResponse = SaavnSongResponse
typealias SaavnPlaylistDetailsResponse = SaavnSongResponse
