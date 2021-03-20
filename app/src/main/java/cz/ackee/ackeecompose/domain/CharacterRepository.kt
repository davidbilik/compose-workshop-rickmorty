package cz.ackee.ackeecompose.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import cz.ackee.ackeecompose.data.api.CharacterService
import cz.ackee.ackeecompose.data.api.toDomain
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getPagedCharacterList() : Flow<PagingData<Character>>

    suspend fun searchCharacters(
        name: String?,
        status: Status?,
        species: String?,
        type: String?,
        gender: Gender?
    ): List<Character>
}

class CharacterRepositoryImpl(
    private val characterPagingSource: PagingSource<Int, Character>,
    private val service: CharacterService
) : CharacterRepository {

    override fun getPagedCharacterList() = Pager(
        config = PagingConfig(pageSize = 50),
        pagingSourceFactory = { characterPagingSource }
    ).flow

    override suspend fun searchCharacters(
        name: String?,
        status: Status?,
        species: String?,
        type: String?,
        gender: Gender?
    ): List<Character> {
        return try {
            service.searchCharacters(name, status?.name, species, type, gender?.name).results.map { it.toDomain() }
        } catch (t: Throwable) {
            emptyList()
        }
    }
}