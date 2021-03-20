package cz.ackee.ackeecompose.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val gender: Gender,
    val origin: String,
    val location: String,
    val image: String
) : Parcelable

enum class Status {
    ALIVE, DEAD, UNKNOWN
}

enum class Gender {
    FEMALE, MALE, GENDERLESS, UNKNOWN
}