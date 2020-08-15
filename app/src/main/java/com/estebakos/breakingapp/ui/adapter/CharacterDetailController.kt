package com.estebakos.breakingapp.ui.adapter

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.TypedEpoxyController
import com.estebakos.breakingapp.R
import com.estebakos.breakingapp.base.extensions.toSeparatedString
import com.estebakos.breakingapp.ui.epoxymodels.characterDetailAttribute
import com.estebakos.breakingapp.ui.epoxymodels.characterDetailHeader
import com.estebakos.breakingapp.ui.model.CharacterItemUI

class CharacterDetailController(private val characterListener: CharacterDetailListener) :
    EpoxyController() {

    private var character: CharacterItemUI? = null
    private var isFavorite: Boolean? = null

    fun setData(character: CharacterItemUI, favorite: Boolean) {
        this.character = character
        this.isFavorite = favorite
        requestModelBuild()

    }

    override fun buildModels() {
        character?.let {
            characterDetailHeader {
                id(HEADER_KEY)
                character(character!!)
                listener(characterListener)
                characterFavorite(isFavorite)
            }

            characterDetailAttribute {
                id(character?.id)
                label(R.string.occupation_label)
                value(character?.occupation.toSeparatedString())
            }

            characterDetailAttribute {
                id(character?.id)
                label(R.string.status_label)
                value(character?.status ?: "")
            }

            characterDetailAttribute {
                id(character?.id)
                label(R.string.portrayed_label)
                value(character?.portrayed ?: "")
            }
        }
    }

    interface CharacterDetailListener {
        fun onCharacterFavorite(character: CharacterItemUI)
    }


    companion object {
        const val HEADER_KEY = "Header"
    }
}