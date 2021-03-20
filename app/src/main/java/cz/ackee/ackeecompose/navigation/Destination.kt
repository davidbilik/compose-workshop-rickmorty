package cz.ackee.ackeecompose.navigation

import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument
import cz.ackee.ackeecompose.domain.Character

sealed class Destination(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {

    object CharacterList : Destination("character_list")

    object CharacterDetail : Destination(
        route = "character",
        arguments = listOf(
            navArgument(Argument.Character.name) {
                NavType.ParcelableType(Character::class.java)
            }
        )
    ) {

        fun constructRoute(navController: NavController, character: Character): String {
            navController.currentBackStackEntry?.arguments?.putParcelable(Argument.Character.name, character)
            return "character"
        }

        enum class Argument {
            Character
        }
    }
}