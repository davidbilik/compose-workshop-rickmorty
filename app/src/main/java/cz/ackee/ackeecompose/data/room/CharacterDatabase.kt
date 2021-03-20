package cz.ackee.ackeecompose.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        CharacterDb::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
}

class DatabaseProvider(
    private val context: Context,
) {

    companion object {

        const val DATABASE_NAME = "rick_and_morty_db"
    }

    fun provideAppDatabase(): RickAndMortyDatabase {
        return Room.databaseBuilder(context, RickAndMortyDatabase::class.java, DATABASE_NAME).build()
    }
}