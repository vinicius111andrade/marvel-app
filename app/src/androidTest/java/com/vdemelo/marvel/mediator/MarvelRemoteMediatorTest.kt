package com.vdemelo.marvel.mediator

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vdemelo.marvel.MarvelCharacterFactory
import com.vdemelo.marvel.data.local.db.MarvelCharactersDataBase
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MarvelRemoteMediatorTest {
    private val characterFactory = MarvelCharacterFactory()
    private val charactersList = listOf(
        characterFactory.createCharacter(),
        characterFactory.createCharacter(),
        characterFactory.createCharacter(),
        characterFactory.createCharacter(),
        characterFactory.createCharacter()
    )
//    private val mockApi = mockMarvelApi() //TODO como?
//    private val mockDb = MarvelCharactersDataBase.cre
//TODO
    //https://10zgur.medium.com/pagination-in-jetpack-compose-with-unit-tests-bb2932513d8
    //https://stackoverflow.com/questions/66503911/unit-testing-a-repository-with-paging-3-using-a-a-remote-mediator-and-paging-sou
    //https://developer.android.com/topic/libraries/architecture/paging/test#remotemediator-tests
}
