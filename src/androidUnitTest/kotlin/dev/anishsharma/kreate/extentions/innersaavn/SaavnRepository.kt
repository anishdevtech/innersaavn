package dev.anishsharma.kreate.extentions.innersaavn

class SaavnRepository(private val source: SaavnDataSource) {

    suspend fun searchTracks(query: String, limit: Int): List<SaavnTrack> =
        source.search(query, limit)

    suspend fun resolveTrackByUrl(url: String): SaavnTrack? =
        source.getByUrl(url)
}
