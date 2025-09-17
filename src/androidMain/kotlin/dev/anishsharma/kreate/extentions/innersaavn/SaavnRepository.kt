package dev.anishsharma.kreate.extentions.innersaavn

data class SaavnSong(
    val id: String,
    val title: String,
    val album: String?,
    val artist: String?,
    val duration: Int,
    val artwork: String?,
    val streamUrl: String?,
    val bitrate: String?
)

class SaavnRepository(
    private val api: SaavnApi,
    private val qualityPref: () -> List<String> = { listOf("320kbps", "160kbps", "96kbps", "12kbps") }
) {
    suspend fun searchSongs(query: String, page: Int = 1): List<SaavnSong> =
        api.searchSongs(query, page).results.map { it.toSong(qualityPref()) }

    suspend fun songDetails(id: String): SaavnSong =
        api.songDetails(id).songs.firstOrNull()?.toSong(qualityPref()) ?: error("Song not found")

    private fun SaavnSongItem.toSong(order: List<String>): SaavnSong {
        val best = downloadUrl.minByOrNull { audio ->
            val idx = order.indexOf(audio.quality)
            if (idx == -1) Int.MAX_VALUE else idx
        }
        return SaavnSong(
            id = id,
            title = title,
            album = album,
            artist = artist,
            duration = duration ?: 0,
            artwork = imageUrl,
            streamUrl = best?.url,
            bitrate = best?.quality
        )
    }
}
