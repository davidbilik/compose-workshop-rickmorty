package cz.ackee.ackeecompose.ui.list

import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import cz.ackee.ackeecompose.domain.Character

// to navigate use that
// val navController = findNavController()
// navController.navigate(Destination.CharacterDetail.constructRoute(navController, char))

@Composable
fun CharacterList(characters: LazyPagingItems<Character>) {

}