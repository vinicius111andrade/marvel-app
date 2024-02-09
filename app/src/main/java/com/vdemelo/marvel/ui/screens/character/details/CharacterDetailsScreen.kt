package com.vdemelo.marvel.ui.screens.character.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vdemelo.marvel.R
import com.vdemelo.marvel.domain.entity.model.MarvelCharacter
import com.vdemelo.marvel.ui.components.FavoriteButton
import com.vdemelo.marvel.ui.components.ImageLoader

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
    val marvelCharacter: MarvelCharacter = MarvelCharacter(
        null, null, null, null, listOf(), null, false
    )

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ImageLoader(
                modifier = Modifier
                    .sizeIn(
                        minHeight = 280.dp,
                        maxHeight = 480.dp,
                        minWidth = 280.dp,
                        maxWidth = 480.dp,
                    ),
                url = marvelCharacter.thumbnail?.getUrl(),
                contentDescription = stringResource(
                    id = R.string.content_description_character_image
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val iconsSize = 60.dp
                FavoriteButton(
                    modifier = Modifier.size(iconsSize),
                    isFavorite = marvelCharacter.isFavorite,
                    selectAction = { /*TODO*/ },
                    unselectAction = { /*TODO*/ }
                )
                Image(
                    modifier = Modifier.size(iconsSize),
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = stringResource(id = R.string.common_share)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(id = R.string.character_bio_label),
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                text = marvelCharacter.description
                    ?: stringResource(id = R.string.character_bio_unknown)
            )

        }
    }
}

@Preview
@Composable
fun PreviewCharacterDetailsScreen() {
    CharacterDetailsScreen(navController = rememberNavController())
}
