package dev.anishsharma.kreate.extentions.innersaavn

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Minimal model used by the tests
data class SaavnTrack(val id: String, val title: String)

// Minimal integration provider backed by saavn.dev
class SaavnProvider(private val http: HttpClient) {

    suspend fun search(query: String, limit: Int): List<SaavnTrack> {
        val resp = http.get("https://saavn.dev/api/search/songs") {
            parameter("query", query)
            parameter("limit", limit)
        }.body<SaavnSearchResponse>()
        val items = resp.data?.results.orEmpty()
        return items.mapNotNull { s ->
            val title = s.title ?: s.name
            val id = s.id
            if (!title.isNullOrBlank() && !id.isNullOrBlank()) SaavnTrack(id = id, title = title) else null
        }
    }

    // Best-effort URL resolver: try to extract an ID token from common share links; fallback to null
    suspend fun getByUrl(url: String): SaavnTrack? {
        val id = extractIdFromUrl(url) ?: return null
        val resp = http.get("https://saavn.dev/api/songs/$id").body<SaavnSongResponse?>()
        val s = resp?.data?.firstOrNull() ?: return null
        val title = s.title ?: s.name ?: return null
        val sid = s.id ?: id
        return SaavnTrack(id = sid, title = title)
    }

    private fun extractIdFromUrl(url: String): String? {
        val u = url.trim()
        val m1 = Regex("""jiosaavn\.com/song/[^/]+/([^/?#]+)""", RegexOption.IGNORE_CASE).find(u)?.groupValues?.getOrNull(1)
        if (!m1.isNullOrBlank()) return m1
        val m2 = Regex("""jiosaavn\.com/album/[^/]+/([^/?#]+)""", RegexOption.IGNORE_CASE).find(u)?.groupValues?.getOrNull(1)
        if (!m2.isNullOrBlank()) return m2
        val m3 = Regex("""jiosaavn\.com/(?:featured|s(?:\/)?playlist)/[^/]+/([^/?#]+)""", RegexOption.IGNORE_CASE).find(u)?.groupValues?.getOrNull(1)
        if (!m3.isNullOrBlank()) return m3
        return null
    }
}

@Serializable
private data class SaavnSearchResponse(
    val data: SaavnSearchData? = null
)

@Serializable
private data class SaavnSearchData(
    val results: List<SaavnSong> = emptyList()
)

@Serializable
private data class SaavnSong(
    val id: String? = null,
    val name: String? = null,
    val title: String? = null
)

@Serializable
private data class SaavnSongResponse(
    val data: List<SaavnSong>? = null
)
