package com.vdemelo.marvel.ui.screens.favorites

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
fun FavoriteToggle(
    characterUi: MarvelCharacterUi,
    initialIsFavorite: Boolean,
    favoritesViewModel: FavoritesViewModel,
    onFavoriteChange: (Boolean) -> Unit
) {
    var isFavorite by remember { mutableStateOf(initialIsFavorite) }

    FavoriteHeart(
        isFavorite = isFavorite,
        onFavoriteChange = { newIsFavorite ->
            favoritesViewModel.updateFavorite(
                characterUi = characterUi,
                isFavorite = isFavorite
            )
            isFavorite = newIsFavorite
        }
    )
}

@Composable
fun FavoriteHeart(
    isFavorite: Boolean,
    onFavoriteChange: (Boolean) -> Unit
) {
    val favImageRes =
        if (isFavorite) R.drawable.ic_favorite_selected
        else R.drawable.ic_favorite_unselected
    Image(
        modifier = Modifier.clickable { onFavoriteChange(!isFavorite) },
        painter = painterResource(id = favImageRes),
        contentDescription = stringResource(id = R.string.button_favorite)
    )
}
