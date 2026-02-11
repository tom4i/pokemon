package cn.zacash.pokemon.model

import cn.zacash.pokemon.graphql.SearchPokemonQuery

sealed interface HomeUiState {
    data class Loading(val show: Boolean) : HomeUiState
    data class Success(val pokemonSpecies: List<SearchPokemonQuery.Species>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}