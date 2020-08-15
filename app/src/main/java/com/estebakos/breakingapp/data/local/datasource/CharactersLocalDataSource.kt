package com.estebakos.breakingapp.data.local.datasource

import com.estebakos.breakingapp.base.Output
import com.estebakos.breakingapp.data.CharactersDataMapper
import com.estebakos.breakingapp.data.local.dao.CharacterDao
import com.estebakos.breakingapp.ui.model.CharacterItemUI
import java.io.IOException
import javax.inject.Inject

class CharactersLocalDataSource @Inject constructor(
    private val dao: CharacterDao
) {

    suspend fun getCharacterList(limit: Int, offset: Int): Output<List<CharacterItemUI>> =
        try {
            val itemDomain = CharactersDataMapper.CharacterListCacheToUI.map(dao.getCharacters(limit, offset))
            Output.Success(itemDomain)
        } catch (e: Throwable) {
            Output.Error(IOException("Exception ${e.message}"))
        }

    suspend fun getFavoriteList(): Output<List<CharacterItemUI>> =
        try {
            val itemDomain = CharactersDataMapper.CharacterListCacheToUI.map(dao.getFavorites())
            Output.Success(itemDomain)
        } catch (e: Throwable) {
            Output.Error(IOException("Exception ${e.message}"))
        }

    suspend fun insertItems(items: List<CharacterItemUI>) {
        try {
            val savedList: MutableList<CharacterItemUI> = mutableListOf()
            items.forEach { character ->
                if (dao.getById(character.id) == null) {
                    savedList.add(character)
                }
            }

            dao.insertAll(CharactersDataMapper.CharacterListUIToCache.map(savedList))
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    suspend fun favorite(id: Int, favorite: Boolean): Output<Boolean> =
        try {
            dao.favorite(id, favorite)
            Output.Success(true)
        } catch (e: Throwable) {
            e.printStackTrace()
            Output.Error(IOException("Exception ${e.message}"))
        }

    suspend fun getCharacterById(id: Int): Output<CharacterItemUI> =
        try {
            val character = CharactersDataMapper.CharacterCacheToUI.map(dao.getById(id))
            Output.Success(character)
        } catch (e: Throwable) {
            e.printStackTrace()
            Output.Error(IOException("Exception ${e.message}"))
        }
}