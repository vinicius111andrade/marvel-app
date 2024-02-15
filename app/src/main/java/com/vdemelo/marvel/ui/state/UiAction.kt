package com.vdemelo.marvel.ui.state

sealed class UiAction {
    class Search(val query: String?) : UiAction()
    class Scroll(
        val currentQuery: String?
//        val visibleItemCount: Int,
//        val lastVisibleItemPosition: Int,
//        val totalItemCount: Int
    ) : UiAction()
}
