package dev.anishsharma.kreate.extentions.innersaavn

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

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
        Regex("""jiosaavn\.com/song/[^/]+/([^/?#]+)""", RegexOption.IGNORE_CASE).find(u)?.groupValues?.getOrNull(1)?.let { return it }
        Regex("""jiosaavn\.com/album/[^/]+/([^/?#]+)""", RegexOption.IGNORE_CASE).find(u)?.groupValues?.getOrNull(1)?.let { return it }
        Regex("""jiosaavn\.com/(?:featured|s(?:\/)?playlist)/[^/]+/([^/?#]+)""", RegexOption.IGNORE_CASE).find(u)?.groupValues?.getOrNull(1)?.let { return it }
        return null
    }
}
