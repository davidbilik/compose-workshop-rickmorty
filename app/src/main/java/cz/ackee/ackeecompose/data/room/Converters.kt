package cz.ackee.ackeecompose.data.room

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import cz.ackee.ackeecompose.domain.Gender
import cz.ackee.ackeecompose.domain.Status

class Converters {

    @SuppressLint("DefaultLocale")
    @TypeConverter
    fun toGender(value: String?): Gender {
        return when (value?.toLowerCase()) {
            "female" -> Gender.FEMALE
            "male" -> Gender.MALE
            "genderless" -> Gender.GENDERLESS
            else -> Gender.UNKNOWN
        }
    }

    @TypeConverter
    fun fromGender(gender: Gender?): String? {
        return gender?.name
    }

    @SuppressLint("DefaultLocale")
    @TypeConverter
    fun toStatus(value: String?): Status {
        return when (value?.toLowerCase()) {
            "alive" -> Status.ALIVE
            "dead" -> Status.DEAD
            else -> Status.UNKNOWN
        }
    }

    @TypeConverter
    fun fromStatus(status: Status?): String {
        return status?.name ?: ""
    }
}