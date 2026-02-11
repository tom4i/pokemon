package cn.zacash.pokemon.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cn.zacash.pokemon.ui.theme.PokemonTheme
import cn.zacash.pokemon.viewmodel.PokemonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(onBack: () -> Unit, viewModel: PokemonViewModel) {
    val selectedPokemon by viewModel.selectedPokemon.collectAsState()

    selectedPokemon?.let { pokemon ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(pokemon.name) },
                    navigationIcon = {
                        TextButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Go back"
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(padding)) {
                Text(text = "Abilities: ", style = MaterialTheme.typography.bodyMedium)
                LazyColumn {
                    val abilities = pokemon.pokemonabilities
                    items(abilities.size, key = { abilities[it].id }) {
                        val ability = abilities[it]
                        ability.ability?.name?.let { ability ->
                            ListItem(
                                headlineContent = { Text(ability) }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    } ?: run {
        Log.i("DetailScreen", "No pokemon selected ")
    }

    DisposableEffect(Unit) {
        onDispose {
            // reset selection
            viewModel.selectPokemon(null)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    PokemonTheme {

    }
}