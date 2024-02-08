package com.vdemelo.marvel.ui.screens.character.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vdemelo.marvel.R
import com.vdemelo.marvel.domain.entity.model.MarvelCharacter
import com.vdemelo.marvel.ui.components.ImageLoader

@Composable
fun CharacterItem(
    marvelCharacter: MarvelCharacter,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = modifier
                    .weight(0.7F)
                    .clickable {
                        //TODO click de abrir o card
                    },
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageLoader(
                    modifier = Modifier.size(60.dp),
                    url = marvelCharacter.thumbnail?.getUrl(),
                    contentDescription = marvelCharacter.name
                        ?: stringResource(id = R.string.details_screen_image_content_description),
                    placeholderRes = R.drawable.ic_person
                )
                Text(text = marvelCharacter.name ?: stringResource(id = R.string.unknown_name))
            }
            val favImageRes = //TODO tem como fazer isso aqui atualizar dinamicamente aqui no composable mesmo
                if(marvelCharacter.isFavorite) R.drawable.ic_favorite_selected
                else R.drawable.ic_favorite_unselected
            Image(
                modifier = modifier
                    .weight(0.3F)
                    .size(30.dp)
                    .clickable {
                        //TODO click de favoritar/desfavoritar
                    },
                painter = painterResource(id = favImageRes),
                contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
fun PreviewCharItem() {
    CharacterItem(
        marvelCharacter = MarvelCharacter(
            id = null,
            name = "Spider Man",
            description = null,
            modified = null,
            urls = listOf(),
            thumbnail = null,
            isFavorite = false
        ),
        navController = rememberNavController()
    )
}

@Preview
@Composable
fun PreviewCharItemUnknown() {
    CharacterItem(
        marvelCharacter = MarvelCharacter(
            id = null,
            name = null,
            description = null,
            modified = null,
            urls = listOf(),
            thumbnail = null,
            isFavorite = false
        ),
        navController = rememberNavController()
    )
}
