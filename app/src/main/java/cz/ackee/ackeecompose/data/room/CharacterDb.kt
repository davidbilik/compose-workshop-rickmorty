package cz.ackee.ackeecompose.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.ackee.ackeecompose.domain.Character
import cz.ackee.ackeecompose.domain.Gender
import cz.ackee.ackeecompose.domain.Status

@Entity(tableName = "character")
data class CharacterDb(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val gender: Gender,
    val origin: String,
    val location: String,
    val image: String,
    override val page: Int
) : PaginatedEntity

fun CharacterDb.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        origin = origin,
        location = location,
        image = image
    )
}
