package cz.ackee.ackeecompose.data.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterResponse(
    val info: ResponseInfo,
    val results: List<CharacterApi>
)

@JsonClass(generateAdapter = true)
data class ResponseInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)