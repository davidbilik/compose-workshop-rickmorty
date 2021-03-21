package cz.ackee.ackeecompose.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.navigate
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.coil.CoilImage
import cz.ackee.ackeecompose.domain.Character
import cz.ackee.ackeecompose.domain.State
import cz.ackee.ackeecompose.navigation.Destination
import cz.ackee.ackeecompose.ui.base.findNavController
import cz.ackee.ackeecompose.ui.theme.toolbarGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

sealed class ListContent {

    class Paged(
        val pagedListStream: Flow<PagingData<Character>>
    ) : ListContent()

    class Search(
        val searchStateStream: Flow<State<List<Character>>>
    ) : ListContent()
}

class ScreenState {

    var searchActive by mutableStateOf(false)
    var searchQuery by mutableStateOf("")
    var listContent by mutableStateOf<ListContent?>(null)
}

@Composable
fun CharacterList(
    viewModel: CharacterListViewModel
) {
    val screenState = remember {
        ScreenState()
    }
    val scrollState = rememberLazyListState()
    if (screenState.searchQuery.isEmpty()) {
        screenState.listContent = ListContent.Paged(viewModel.charactersPagedList)
    } else {
        screenState.listContent = ListContent.Search(viewModel.searchCharacters(name = screenState.searchQuery))
    }
    CharacterList(
        scrollState = scrollState,
        state = screenState,
        onQueryChange = {
            screenState.searchQuery = it
        },
        onSearchActiveChange = {
            screenState.searchActive = it
            if (!it) {
                screenState.searchQuery = ""
            }
        }
    )
}

@Composable
fun CharacterList(
    scrollState: LazyListState,
    state: ScreenState,
    onQueryChange: (String) -> Unit,
    onSearchActiveChange: (Boolean) -> Unit,
) {
    // Remember a CoroutineScope to be able to launch
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    Scaffold(
        topBar = {
            AppBarWithSearch(
                searchActive = state.searchActive,
                onSearchClick = {
                    onSearchActiveChange(true)
                    coroutineScope.launch {
                        delay(300)
                        focusRequester.requestFocus()
                    }
                },
                onCloseSearchClick = {
                    onSearchActiveChange(false)
                },
                onQueryChange = onQueryChange,
                searchQuery = state.searchQuery,
                textFieldFocusRequester = focusRequester
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (state.listContent) {
                is ListContent.Paged -> {
                    val lazyPagingItems = (state.listContent as ListContent.Paged).pagedListStream.collectAsLazyPagingItems()
                    PagedCharactersList(scrollState = scrollState, characters = lazyPagingItems)
                }
                is ListContent.Search -> {
                    val loadingState by ((state.listContent as ListContent.Search).searchStateStream).collectAsState(initial = State.Loading)
                    when (loadingState) {
                        is State.Loaded -> {
                            FixedCharactersList(scrollState = scrollState, characters = (loadingState as State.Loaded<List<Character>>).data)
                        }
                        is State.Loading -> {
                            ScreenProgress()
                        }
                    }
                }
                else -> {
                    ScreenProgress()
                }
            }

            ScrollToTopFab(scrollState, coroutineScope)
        }
    }
}

@Composable
fun ScreenProgress() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun PagedCharactersList(
    scrollState: LazyListState,
    characters: LazyPagingItems<Character>
) {
    LazyColumn(
        state = scrollState
    ) {
        items(characters) { character ->
            character?.let { current ->
                CharacterListItem(current)
            }
        }
    }
}

@Composable
fun FixedCharactersList(
    scrollState: LazyListState,
    characters: List<Character>
) {
    LazyColumn(
        state = scrollState
    ) {
        items(characters) { character ->
            CharacterListItem(character)
        }
    }
}

@Composable
private fun AppBarWithSearch(
    searchQuery: String,
    searchActive: Boolean,
    onSearchClick: () -> Unit,
    onCloseSearchClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    textFieldFocusRequester: FocusRequester
) {
    if (!searchActive) {
        TopAppBar(
            title = {
                Text(text = "Wubba Lubba")
            },
            actions = {
                IconButton(onClick = { onSearchClick() }) {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
                }
            },
            backgroundColor = toolbarGray
        )
    } else {
        TopAppBar(
            backgroundColor = toolbarGray
        ) {
            TextField(
                modifier = Modifier
                    .focusRequester(textFieldFocusRequester)
                    .weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                placeholder = {
                    Text("Search name")
                },
                value = searchQuery,
                onValueChange = {
                    onQueryChange(it)
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                },
                shape = RoundedCornerShape(0.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            IconButton(onClick = { onCloseSearchClick() }) {
                Icon(Icons.Default.Close, contentDescription = null, tint = Color.White)
            }
        }
    }
}

@Composable
private fun BoxScope.ScrollToTopFab(scrollState: LazyListState, coroutineScope: CoroutineScope) {
    val yOffset = if (scrollState.firstVisibleItemIndex != 0) {
        0.dp
    } else {
        lerp(150.dp, 0.dp, (scrollState.firstVisibleItemScrollOffset / 150f).coerceAtMost(1f))
    }

    FloatingActionButton(
        onClick = {
            coroutineScope.launch {
                scrollState.animateScrollToItem(index = 0)
            }
        }, modifier = Modifier.Companion
            .align(Alignment.BottomEnd)
            .offset(0.dp, yOffset)
            .padding(12.dp)
    ) {
        Icon(
            Icons.Default.KeyboardArrowUp,
            modifier = Modifier.size(48.dp),
            contentDescription = "up button"
        )
    }
}

@Composable
private fun CharacterListItem(character: Character) {
    val navController = findNavController()
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            )
            .clickable {
                navController.navigate(Destination.CharacterDetail.constructRoute(navController, character))
            }
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                )
        ) {
            CoilImage(
                data = character.image,
                contentDescription = "image of ${character.name}",
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.padding(top = 4.dp)) {
                Text(
                    character.name,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(character.species, fontSize = 12.sp)
            }
        }
    }
}
