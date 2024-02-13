package com.vdemelo.marvel.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.vdemelo.marvel.R
import com.vdemelo.marvel.ui.model.MarvelCharacterUi

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    characterUi: MarvelCharacterUi,
    onFavoriteChange: (MarvelCharacterUi, Boolean) -> Unit
) {
    //TODO ver se vai dar problema de trocar estado do botao sem finalizar troca no BD
    var isFavorite by remember { mutableStateOf(characterUi.isFavorite) }

    val favImageRes =
        if (isFavorite) R.drawable.ic_favorite_selected
        else R.drawable.ic_favorite_unselected
    Image(
        modifier = modifier.clickable {
            onFavoriteChange(characterUi, !isFavorite)
            isFavorite = !isFavorite
        },
        painter = painterResource(id = favImageRes),
        contentDescription = stringResource(id = R.string.button_favorite)
    )
}
