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
import com.estebakos.breakingapp.ui.adapter.CharacterDetailController
import com.estebakos.breakingapp.ui.adapter.CharactersController
import com.estebakos.breakingapp.ui.model.CharacterItemUI

@EpoxyModelClass(layout = R.layout.item_character_detail_header)
abstract class CharacterDetailHeaderModel : EpoxyModelWithHolder<CharacterDetailHeaderModel.Holder>() {

    @EpoxyAttribute
    lateinit var character: CharacterItemUI
    @EpoxyAttribute
    var characterFavorite: Boolean? = null

    @EpoxyAttribute
    lateinit var listener: CharacterDetailController.CharacterDetailListener

    override fun bind(holder: Holder) {
        with(holder) {
            nickNameTextView.text = character.nickname

            favoriteImageView.setImageDrawable(
                if (characterFavorite != null && characterFavorite!!) ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_favorite_fill
                ) else ContextCompat.getDrawable(context, R.drawable.ic_favorite_border)
            )

            favoriteImageView.setOnClickListener {
                listener.onCharacterFavorite(character)
            }

            Glide.with(context).load(character.imageUrl).into(imageView)
        }
    }

    inner class Holder : EpoxyHolder() {
        lateinit var nickNameTextView: TextView
        lateinit var imageView: ImageView
        lateinit var favoriteImageView: ImageView
        lateinit var context: Context

        override fun bindView(itemView: View) {
            nickNameTextView = itemView.findViewById(R.id.text_character_detail_nickname)
            imageView = itemView.findViewById(R.id.image_character_detail)
            favoriteImageView = itemView.findViewById(R.id.image_character_favorite)
            context = itemView.context
        }
    }
}