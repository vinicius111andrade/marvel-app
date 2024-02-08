package com.vdemelo.marvel.ui.screens.character.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

//TODO
//Detalhes do personagem
//• Botão de favorito.
//
//• Botão para compartilhar a imagem do personagem.
//
//• Foto em tamanho maior
//
//• Descrição (se houver).

@Composable
fun CharacterDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
    }

}

@Preview
@Composable
fun PreviewCharacterDetailsScreen() {
    CharacterDetailsScreen(navController = rememberNavController())
}
