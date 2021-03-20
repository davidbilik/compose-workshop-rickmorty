package cz.ackee.ackeecompose.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cz.ackee.ackeecompose.data.api.CharacterService
import cz.ackee.ackeecompose.data.api.toDb
import cz.ackee.ackeecompose.data.api.toDomain
import cz.ackee.ackeecompose.data.room.CharacterDao
import cz.ackee.ackeecompose.data.room.toDomain
import cz.ackee.ackeecompose.data.sharedprefs.CharacterSharedPrefs
import cz.ackee.ackeecompose.domain.Character

class CharacterPagedDataSource(
    private val characterService: CharacterService,
    private val characterDao: CharacterDao,
    private val characterPrefs: CharacterSharedPrefs
) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val pageNumber = params.key ?: 1
        return try {
            var characters = characterDao.getCharactersOnPage(pageNumber).map { it.toDomain() }
            if (characters.isEmpty()) {
                val response = characterService.getCharacters(pageNumber)
                characterPrefs.setMaxPages(response.info.pages)
                characterDao.insertAll(response.results.map { it.toDb(pageNumber) })
                characters = response.results.map { it.toDomain() }
            }

            LoadResult.Page(
                data = characters,
                prevKey = null,
                nextKey = if (characterPrefs.getMaxPages() > pageNumber) pageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}