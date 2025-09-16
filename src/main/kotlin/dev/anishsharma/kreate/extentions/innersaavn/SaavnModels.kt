package dev.anishsharma.kreate.extentions.innersaavn

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaavnAudio(
    val quality: String, // "320kbps", "160kbps", "96kbps", "12kbps"
    val url: String
)

@Serializable
data class SaavnSongItem(
    val id: String,
    val title: String,
    val album: String? = null,
    val artist: String? = null,
    val duration: Int? = null,
    @SerialName("image") val imageUrl: String? = null,
    @SerialName("downloadUrl") val downloadUrl: List<SaavnAudio> = emptyList()
)

@Serializable
data class SaavnSearchSongsResponse(
    val results: List<SaavnSongItem> = emptyList(),
    val total: Int = 0
)

@Serializable
data class SaavnAlbumItem(
    val id: String,
    val title: String,
    @SerialName("image") val imageUrl: String? = null,
    val year: String? = null,
    val primaryArtists: String? = null
)

@Serializable
data class SaavnSearchAlbumsResponse(
    val results: List<SaavnAlbumItem> = emptyList(),
    val total: Int = 0
)

@Serializable
data class SaavnPlaylistItem(
    val id: String,
    val title: String,
    @SerialName("image") val imageUrl: String? = null
)

@Serializable
data class SaavnSearchPlaylistsResponse(
    val results: List<SaavnPlaylistItem> = emptyList(),
    val total: Int = 0
)

@Serializable
data class SaavnSongDetailsResponse(val songs: List<SaavnSongItem> = emptyList())

@Serializable
data class SaavnAlbumDetailsResponse(
    val id: String,
    val title: String,
    @SerialName("image") val imageUrl: String? = null,
    val songs: List<SaavnSongItem> = emptyList()
)

@Serializable
data class SaavnPlaylistDetailsResponse(
    val id: String,
    val title: String,
    @SerialName("image") val imageUrl: String? = null,
    val songs: List<SaavnSongItem> = emptyList()
)
