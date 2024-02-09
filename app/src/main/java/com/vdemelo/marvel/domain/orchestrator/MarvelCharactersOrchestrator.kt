package com.vdemelo.marvel.domain.orchestrator

import com.vdemelo.marvel.domain.usecase.MarvelCharactersRemoteListingUseCase
import com.vdemelo.marvel.domain.usecase.MarvelFavoritesUseCase

class MarvelCharactersOrchestrator(
    remoteListingUseCase: MarvelCharactersRemoteListingUseCase,
    favoritesUseCase: MarvelFavoritesUseCase
) {

    //TODO tenho que combinar os fluxos, a cada request checar se determinado char é fav ou n, melhor fazer uma consulta no DB, salvar em cache e ir comparando
//    == request de paginacao ==
//    0 - pego todos os favoritos e salvo numa val de escopo local
//    1 - faço request pego uma pagina
//    2 - atualizo os status de is fav ou n (ver como fazer de forma performatica)
//    3 - devolvo a lista atualizada com os fav
//
//    == atualizacao de fav ==
//    0 - recebo um isFavorite que pode ser true ou false
//    1 - se true upsert, se false delete by sum
//
//    == listagem de favs ==
//    0 - pedido da lista
//    1 - devolvo a lista
//
//    == cachear char selecionado ==
//    0 - recebo um char
//    1 - salvo numa private var nullable
//
//    == devolver char selecionado ==
//    0 - recebo um pedido
//    1 - devolvo o char cacheado ou null

}
