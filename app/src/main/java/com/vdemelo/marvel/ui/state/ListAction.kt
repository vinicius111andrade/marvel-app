package com.vdemelo.marvel.ui.state

sealed class ListAction {
    class Search(val query: String?) : ListAction()
    class Scroll(val currentQuery: String?) : ListAction()
}
