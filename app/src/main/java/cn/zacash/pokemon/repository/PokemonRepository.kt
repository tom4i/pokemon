package cn.zacash.pokemon.repository

import cn.zacash.pokemon.di.ApolloClientProvider
import cn.zacash.pokemon.graphql.SearchPokemonQuery

class PokemonRepository {

    suspend fun searchPokemon(name: String): Result<List<SearchPokemonQuery.Species>> {
        return try {
            val response =
                ApolloClientProvider.client.query(SearchPokemonQuery("%${name.lowercase()}%")).execute()
            when {
                response.exception != null ->
                    // Network error
                    Result.failure(response.exception ?: Exception("Network error"))

                response.errors != null ->
                    // GraphQL error
                    Result.failure(Exception("GraphQL error: ${response.errors?.joinToString()}"))

                response.data != null ->
                    if (response.data?.species?.isNotEmpty() == true) {
                        Result.success(response.data?.species ?: emptyList())
                    } else {
                        Result.failure(Exception("Pokemon species data is null"))
                    }

                else ->
                    Result.failure(Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}