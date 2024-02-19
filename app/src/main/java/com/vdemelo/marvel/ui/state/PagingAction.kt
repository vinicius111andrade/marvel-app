package com.vdemelo.marvel.ui.state

sealed class PagingAction {
    class Search(val query: String?) : PagingAction()
    class Scroll(val currentQuery: String?) : PagingAction()
}
