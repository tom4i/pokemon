package cn.zacash.pokemon.di

import com.apollographql.apollo.ApolloClient

object ApolloClientProvider {
    val client by lazy {
        ApolloClient.Builder().serverUrl("https://graphql.pokeapi.co/v1beta2").build()
    }
}