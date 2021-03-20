package cz.ackee.ackeecompose.data.api

import android.annotation.SuppressLint
import com.squareup.moshi.JsonClass
import cz.ackee.ackeecompose.data.room.CharacterDb
import cz.ackee.ackeecompose.domain.Character
import cz.ackee.ackeecompose.domain.Gender
import cz.ackee.ackeecompose.domain.Status

@JsonClass(generateAdapter = true)
data class CharacterApi(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String
)

@JsonClass(generateAdapter = true)
data class Origin(
    val name: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class Location(
    val name: String,
    val url: String
)

@SuppressLint("DefaultLocale")
fun CharacterApi.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        status = when (status.toLowerCase()) {
            "alive" -> Status.ALIVE
            "dead" -> Status.DEAD
            else -> Status.UNKNOWN
        },
        species = species,
        gender = when (gender.toLowerCase()) {
            "female" -> Gender.FEMALE
            "male" -> Gender.MALE
            "genderless" -> Gender.GENDERLESS
            else -> Gender.UNKNOWN
        },
        origin = origin.name,
        location = location.name,
        image = image
    )
}

@SuppressLint("DefaultLocale")
fun CharacterApi.toDb(page: Int): CharacterDb {
    return CharacterDb(
        id = id,
        name = name,
        status = when (status.toLowerCase()) {
            "alive" -> Status.ALIVE
            "dead" -> Status.DEAD
            else -> Status.UNKNOWN
        },
        species = species,
        gender = when (gender.toLowerCase()) {
            "female" -> Gender.FEMALE
            "male" -> Gender.MALE
            "genderless" -> Gender.GENDERLESS
            else -> Gender.UNKNOWN
        },
        origin = origin.name,
        location = location.name,
        image = image,
        page = page
    )
}