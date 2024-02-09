package com.vdemelo.marvel.ui.screens.character.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vdemelo.marvel.ui.screens.character.item.CharacterItem
import com.vdemelo.marvel.ui.screens.home.HomeViewModel
import com.vdemelo.marvel.ui.components.RetrySection
import org.koin.androidx.compose.getViewModel

//TODO
//Home - Characters
//• Listagem dos personagens.
//
//• Botão para favoritar nas células.
//
//• Barra de busca para filtrar lista de personagens por nome.
//
//• Interface de lista vazia, erro ou sem internet.

@Composable
fun CharactersList(
    navController: NavController,
    viewModel: HomeViewModel = getViewModel()
) {
    val list by remember { viewModel.list }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (list.isNotEmpty()) {
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                val itemCount = list.size

                items(itemCount) { i ->
                    val hasScrolledDown = i >= itemCount - 2
                    if (hasScrolledDown && !endReached && !isLoading) {
                        viewModel.requestCharactersList()
                    }
                    CharacterItem(
                        marvelCharacter = list[i],
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    if (isLoading) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    } else if (loadError.isNotEmpty()) {
                        RetrySection(
                            error = loadError,
                            onRetry = { viewModel.requestCharactersList() }
                        )
                    }
                }
            }
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                } else if (loadError.isNotEmpty()) {
                    RetrySection(
                        error = loadError,
                        onRetry = { viewModel.requestCharactersList() }
                    )
                }
            }
        }
    }
}
