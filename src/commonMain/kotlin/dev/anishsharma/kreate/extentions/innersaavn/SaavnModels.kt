package dev.anishsharma.kreate.extentions.innersaavn

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaavnSearchResponse(
    val data: SaavnSearchData? = null
)

@Serializable
data class SaavnSearchData(
    val results: List<SaavnSong> = emptyList()
)

@Serializable
data class SaavnSong(
    val id: String? = null,
    val name: String? = null,
    val title: String? = null
)

@Serializable
data class SaavnSongResponse(
    val data: List<SaavnSong>? = null
)

// Public DTO you can expose from repository
data class SaavnTrack(val id: String, val title: String)
