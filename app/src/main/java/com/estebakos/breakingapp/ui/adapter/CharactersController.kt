package com.estebakos.breakingapp.ui.adapter

import com.airbnb.epoxy.AutoModel
import com.airbnb.epoxy.EpoxyController
import com.estebakos.breakingapp.ui.epoxymodels.LoadMoreModel_
import com.estebakos.breakingapp.ui.epoxymodels.character
import com.estebakos.breakingapp.ui.model.CharacterItemUI

class CharactersController(private val characterListener: CharacterListener) : EpoxyController() {

    @AutoModel
    lateinit var loadMoreView: LoadMoreModel_
    var hasMoreToLoad = false

    private val characters: MutableList<CharacterItemUI> = mutableListOf()
    private val favorites: MutableList<CharacterItemUI> = mutableListOf()

    fun setData(data: MutableList<CharacterItemUI>) {
        this.characters.clear()
        this.characters.addAll(data)
        requestModelBuild()
    }

    fun setFavorites(favorites: MutableList<CharacterItemUI>) {
        this.favorites.clear()
        this.favorites.addAll(favorites)
        requestModelBuild()
    }

    fun addData(data: MutableList<CharacterItemUI>) {
        this.characters.addAll(data)
        requestModelBuild()
    }

    override fun buildModels() {
        favorites.forEach {
            character {
                id(it.id)
                character(it)
                characterListener(characterListener)
            }
        }

        characters.forEach {
            character {
                id(it.id)
                character(it)
                characterListener(characterListener)
            }
        }

        loadMoreView.addIf(hasMoreToLoad, this)
    }

    interface CharacterListener {
        fun onCharacterSelected(character: CharacterItemUI)
        fun onCharacterFavorite(character: CharacterItemUI)
    }
}