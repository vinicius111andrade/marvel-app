package com.vdemelo.marvel.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.vdemelo.marvel.R

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    selectAction: () -> Unit,
    unselectAction: () -> Unit
) {
    val favImageRes =
        if (isFavorite) R.drawable.ic_favorite_selected
        else R.drawable.ic_favorite_unselected
    Image(
        modifier = modifier
            .clickable {
                if (isFavorite) {
                    unselectAction.invoke()
                } else {
                    selectAction.invoke()
                }
            },
        painter = painterResource(id = favImageRes),
        contentDescription = stringResource(id = R.string.button_favorite)
    )
}
