package cn.zacash.pokemon.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.zacash.pokemon.ext.ColorMapper
import cn.zacash.pokemon.model.HomeUiState
import cn.zacash.pokemon.ui.theme.PokemonTheme
import cn.zacash.pokemon.ui.widget.SimpleSearchBar
import cn.zacash.pokemon.viewmodel.PokemonViewModel

@Composable
fun HomeScreen(onNavToDetail: () -> Unit, viewModel: PokemonViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val textFieldState = rememberTextFieldState()

    Scaffold(
        topBar = {
            SimpleSearchBar(textFieldState, onSearch = {
                val query = textFieldState.text.toString()
                viewModel.searchPokemon(query)
            })
        }
    ) { padding ->
        when (uiState) {
            is HomeUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    if ((uiState as HomeUiState.Loading).show) {
                        CircularProgressIndicator()
                    }
                }
            }

            is HomeUiState.Success -> {
                val species = (uiState as HomeUiState.Success).pokemonSpecies

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    items(species.size, key = { species[it].id }) {
                        val spec = species[it]
                        val speciesName = spec.name
                        val captureRate = spec.capture_rate
                        val colorName = spec.pokemoncolor?.name
                        val pokemonColor = ColorMapper.parseColor(colorName)
                        Spacer(Modifier.height(16.dp))
                        Column {
                            Text(
                                "Species Name: $speciesName",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                "Capture Rate: $captureRate",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                "Pokemons:",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            spec.pokemons.forEach { pokemon ->
                                Box(modifier = Modifier.clickable {
                                    viewModel.selectPokemon(pokemon)
                                    onNavToDetail()
                                }) {
                                    ListItem(
                                        headlineContent = { Text("#${pokemon.id} ${pokemon.name}") },
                                        colors = ListItemDefaults.colors(
                                            containerColor = pokemonColor
                                        )
                                    )
                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                }

            }

            is HomeUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (uiState as HomeUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    PokemonTheme {

    }
}