package cz.ackee.ackeecompose.domain

sealed class State<out T> {

    object Loading : State<Nothing>()

    data class Loaded<out T>(val data: T) : State<T>()
}