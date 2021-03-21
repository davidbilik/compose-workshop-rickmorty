package cz.ackee.ackeecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.ackee.ackeecompose.domain.Character
import cz.ackee.ackeecompose.navigation.Destination
import cz.ackee.ackeecompose.ui.base.LocalNavController
import cz.ackee.ackeecompose.ui.detail.CharacterDetail
import cz.ackee.ackeecompose.ui.list.CharacterList
import cz.ackee.ackeecompose.ui.list.CharacterListViewModel
import cz.ackee.ackeecompose.ui.theme.AckeeComposeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CharacterListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            val navController = rememberNavController()
            CompositionLocalProvider(
                LocalNavController provides navController
            ) {
                AckeeComposeTheme {
                    window.statusBarColor = MaterialTheme.colors.primarySurface.toArgb()
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        NavHost(navController = navController, startDestination = Destination.CharacterList.route) {
                            composable(Destination.CharacterList) { CharacterList(viewModel) }
                            composable(Destination.CharacterDetail) {
                                // NOT recommended to pass parcelable in compose navigation and it should be done
                                // via passing id and fetching from room, but it can be simplified there i guess :)
                                val character =
                                    navController.previousBackStackEntry?.arguments?.getParcelable<Character>(Destination.CharacterDetail.Argument.Character.name)
                                if (character != null) {
                                    CharacterDetail(character)
                                } else {
                                    throw NullPointerException("character is null")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun NavGraphBuilder.composable(destination: Destination, content: @Composable (NavBackStackEntry) -> Unit) {
        composable(destination.route, destination.arguments, content = content)
    }
}
