package cn.zacash.pokemon.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.zacash.pokemon.graphql.SearchPokemonQuery
import cn.zacash.pokemon.model.HomeUiState
import cn.zacash.pokemon.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {

    companion object {
        private const val TAG = "PokemonViewModel"
    }

    private val pokemonRepository = PokemonRepository()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading(false))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // for data share
    private val _selectedPokemon = MutableStateFlow<SearchPokemonQuery.Pokemon?>(null)
    val selectedPokemon: StateFlow<SearchPokemonQuery.Pokemon?> = _selectedPokemon

    fun selectPokemon(pokemon: SearchPokemonQuery.Pokemon?) {
        Log.i(TAG, "selected pokemon: ${pokemon?.name}")
        _selectedPokemon.value = pokemon
    }

    fun searchPokemon(query: String) {
        Log.i(TAG, "searchPokemon: $query")
        // show loading
        _uiState.value = HomeUiState.Loading(true)
        // clear selection
        _selectedPokemon.value = null
        viewModelScope.launch {
            pokemonRepository.searchPokemon(query)
                .onSuccess { results ->
                    _uiState.value = HomeUiState.Success(results)
                    Log.i(TAG, "searchPokemon: got pokemons")
                }.onFailure { e ->
                    _uiState.value = HomeUiState.Error(e.message ?: "Unknown error")
                    Log.e(TAG, "searchPokemon error: ${e.stackTraceToString()}")
                }
        }
    }

}