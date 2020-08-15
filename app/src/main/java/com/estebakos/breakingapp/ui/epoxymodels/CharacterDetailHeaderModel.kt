package com.estebakos.breakingapp.ui.epoxymodels

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.estebakos.breakingapp.R
import com.estebakos.breakingapp.ui.adapter.CharactersController
import com.estebakos.breakingapp.ui.model.CharacterItemUI

@EpoxyModelClass(layout = R.layout.item_character_list)
abstract class CharacterModel : EpoxyModelWithHolder<CharacterModel.Holder>() {
    @EpoxyAttribute
    lateinit var character: CharacterItemUI

    @EpoxyAttribute
    lateinit var characterListener: CharactersController.CharacterListener

    override fun bind(holder: Holder) {
        with(holder) {
            nameTextView.text = character.name
            nickNameTextView.text = character.nickname

            favoriteImageView.setImageDrawable(
                if (character.favorite) ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_favorite_fill
                ) else ContextCompat.getDrawable(context, R.drawable.ic_favorite_border)
            )

            favoriteImageView.setOnClickListener {
                characterListener.onCharacterFavorite(character)
            }

            container.setOnClickListener {
                characterListener.onCharacterSelected(character)
            }

            Glide.with(context).load(character.imageUrl).into(imageView)
        }
    }

    inner class Holder : EpoxyHolder() {
        lateinit var nameTextView: TextView
        lateinit var nickNameTextView: TextView
        lateinit var imageView: ImageView
        lateinit var favoriteImageView: ImageView
        lateinit var context: Context
        lateinit var container: ConstraintLayout

        override fun bindView(itemView: View) {
            nameTextView = itemView.findViewById(R.id.text_character_name)
            nickNameTextView = itemView.findViewById(R.id.text_character_nick_name)
            imageView = itemView.findViewById(R.id.image_character_item)
            favoriteImageView = itemView.findViewById(R.id.image_favorite)
            container = itemView.findViewById(R.id.container_character)
            context = itemView.context
        }
    }
}