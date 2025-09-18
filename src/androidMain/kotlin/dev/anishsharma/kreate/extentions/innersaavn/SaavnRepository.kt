package dev.anishsharma.kreate.extentions.innersaavn

class SaavnRepository(private val provider: SaavnProvider) {

  // Public API returns public DTO
  suspend fun searchTracks(query: String, limit: Int): List<SaavnTrack> =
  provider.search(query, limit)

  suspend fun resolveTrackByUrl(url: String): SaavnTrack? =
  provider.getByUrl(url)
}