package dev.anishsharma.kreate.extentions.innersaavn

class SaavnApiAdapter(private val api: SaavnApi) : SaavnDataSource {

    override suspend fun search(query: String, limit: Int): List<SaavnTrack> {
        val resp = api.searchSongs(query, page = 1)
        return resp.data?.results.orEmpty().take(limit).mapNotNull { s ->
            val title = s.title ?: s.name
            val id = s.id
            if (!title.isNullOrBlank() && !id.isNullOrBlank()) SaavnTrack(id, title) else null
        }
    }

    override suspend fun getByUrl(url: String): SaavnTrack? {
        val id = extractIdFromUrl(url) ?: return null
        val resp = api.songDetails(id)
        val s = resp.data?.firstOrNull() ?: return null
        val title = s.title ?: s.name ?: return null
        val sid = s.id ?: id
        return SaavnTrack(sid, title)
    }

    private fun extractIdFromUrl(url: String): String? {
        val u = url.trim()
        Regex("""jiosaavn\.com/song/[^/]+/([^/?#]+)""", RegexOption.IGNORE_CASE).find(u)?.groupValues?.getOrNull(1)?.let { return it }
        Regex("""jiosaavn\.com/album/[^/]+/([^/?#]+)""", RegexOption.IGNORE_CASE).find(u)?.groupValues?.getOrNull(1)?.let { return it }
        Regex("""jiosaavn\.com/(?:featured|s(?:\/)?playlist)/[^/]+/([^/?#]+)""", RegexOption.IGNORE_CASE).find(u)?.groupValues?.getOrNull(1)?.let { return it }
        return null
    }
}
