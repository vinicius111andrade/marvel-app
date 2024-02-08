package com.vdemelo.marvel.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.vdemelo.marvel.R

@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    url: String?,
    contentDescription: String,
    placeholderRes: Int = R.drawable.ic_image_placeholder
) {
    AsyncImage(
        modifier = modifier,
        model = url,
        contentDescription = contentDescription,
        error = painterResource(id = placeholderRes)
    )
}
