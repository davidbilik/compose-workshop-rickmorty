package cz.ackee.ackeecompose.data.api

import cz.ackee.ackeecompose.domain.Gender
import cz.ackee.ackeecompose.domain.Status
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): CharacterResponse

    @GET("character")
    suspend fun searchCharacters(
        @Query("name") name: String?,
        @Query("status") status: String?,
        @Query("species") species: String?,
        @Query("type") type: String?,
        @Query("gender") gender: String?
    ): CharacterResponse
}