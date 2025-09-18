package dev.anishsharma.kreate.extentions.innersaavn

interface SaavnDataSource {
    suspend fun search(query: String, limit: Int): List<SaavnTrack>
    suspend fun getByUrl(url: String): SaavnTrack?
}
