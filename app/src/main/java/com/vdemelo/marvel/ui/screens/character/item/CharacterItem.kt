package com.vdemelo.marvel.ui.screens.character.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vdemelo.marvel.R
import com.vdemelo.marvel.domain.entity.model.MarvelCharacter
import com.vdemelo.marvel.ui.components.ImageLoader

//TODO here I can call the viewModel that will save the state of the char, if its a fav, if it has been selected

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
            .clickable {
                //TODO navigate to character screen
            }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            ImageLoader(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .size(120.dp),
                url = marvelCharacter.thumbnail?.getUrl(),
                contentDescription = marvelCharacter.name
                    ?: stringResource(id = R.string.details_screen_image_content_description)
            )
//            Spacer(modifier = Modifier.height(8.dp))
//            detailsData(species).forEach {
//                LabelAndTextData(label = it.label, text = it.text)
//            }
        }
    }
}

//@Composable
//fun detailsData(species: Species): List<TextField> {
//    return listOf(
//        TextField(
//            label = stringResource(id = R.string.list_screen_name_label),
//            text = species.name
//        ),
//        TextField(
//            label = stringResource(id = R.string.list_screen_language_label),
//            text = species.language
//        ),
//        TextField(
//            label = stringResource(id = R.string.list_screen_classification_label),
//            text = species.classification
//        )
//    )
//}
