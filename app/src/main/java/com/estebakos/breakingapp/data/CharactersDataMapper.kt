package com.estebakos.breakingapp.data

import com.estebakos.breakingapp.base.BaseMapper
import com.estebakos.breakingapp.data.local.entity.CharacterEntity
import com.estebakos.breakingapp.data.remote.model.CharacterItemResponse
import com.estebakos.breakingapp.ui.model.CharacterItemUI

object CharactersDataMapper {

    object CharacterListRemoteToUI :
        BaseMapper<List<CharacterItemResponse>, List<CharacterItemUI>> {
        override fun map(type: List<CharacterItemResponse>): List<CharacterItemUI> {
            return type.map {
                CharacterItemUI(
                    id = it.id,
                    name = it.name,
                    occupation = it.occupation,
                    imageUrl = it.imageUrl,
                    status = it.status,
                    nickname = it.nickname,
                    portrayed = it.portrayed,
                    favorite = false
                )
            }
        }
    }

    object CharacterListUIToCache : BaseMapper<List<CharacterItemUI>, List<CharacterEntity>> {
        override fun map(type: List<CharacterItemUI>): List<CharacterEntity> {
            return type.map {
                CharacterEntity(
                    characterId = it.id,
                    name = it.name,
                    occupation = it.occupation,
                    imageUrl = it.imageUrl,
                    status = it.status,
                    nickname = it.nickname,
                    portrayed = it.portrayed,
                    favorite = it.favorite
                )
            }
        }
    }

    object CharacterListCacheToUI : BaseMapper<List<CharacterEntity>, List<CharacterItemUI>> {
        override fun map(type: List<CharacterEntity>): List<CharacterItemUI> {
            return type.map {
                CharacterItemUI(
                    id = it.characterId,
                    name = it.name,
                    occupation = it.occupation,
                    imageUrl = it.imageUrl,
                    status = it.status,
                    nickname = it.nickname,
                    portrayed = it.portrayed,
                    favorite = it.favorite
                )
            }
        }
    }

    object CharacterCacheToUI : BaseMapper<CharacterEntity, CharacterItemUI> {
        override fun map(type: CharacterEntity): CharacterItemUI {
            return CharacterItemUI(
                id = type.characterId,
                name = type.name,
                occupation = type.occupation,
                imageUrl = type.imageUrl,
                status = type.status,
                nickname = type.nickname,
                portrayed = type.portrayed,
                favorite = type.favorite
            )
        }
    }
}