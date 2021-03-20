package cz.ackee.ackeecompose.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import cz.ackee.ackeecompose.domain.Character
import cz.ackee.ackeecompose.domain.CharacterRepository
import cz.ackee.ackeecompose.domain.Gender
import cz.ackee.ackeecompose.domain.State
import cz.ackee.ackeecompose.domain.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber

class CharacterListViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val charactersPagedList: Flow<PagingData<Character>>
        get() = characterRepository.getPagedCharacterList().cachedIn(viewModelScope)

    fun searchCharacters(
        name: String? = null,
        status: Status? = null,
        species: String? = null,
        type: String? = null,
        gender: Gender? = null
    ): Flow<State<List<Character>>> = flow {
        emit(State.Loading)
        val characters = characterRepository.searchCharacters(name, status, species, type, gender)
        emit(State.Loaded(characters))
    }
}