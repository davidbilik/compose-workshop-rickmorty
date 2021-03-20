package cz.ackee.ackeecompose.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character WHERE page LIKE :requestedPage")
    suspend fun getCharactersOnPage(requestedPage: Int): List<CharacterDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterDb>)
}