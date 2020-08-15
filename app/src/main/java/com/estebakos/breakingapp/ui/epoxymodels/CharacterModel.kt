package com.estebakos.breakingapp.ui.epoxymodels

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.estebakos.breakingapp.R

@EpoxyModelClass(layout = R.layout.character_list_item)
abstract class CharacterModel :
    EpoxyModelWithHolder<CharacterModel.Holder>() {
    @EpoxyAttribute
    var name: String? = null

    @EpoxyAttribute
    var nickname: String? = null

    @EpoxyAttribute
    var image: String? = null

    @EpoxyAttribute
    var favorite: Boolean? = null
    override fun bind(holder: Holder) {
        holder.header.setText(name)
    }

    internal class Holder : EpoxyHolder() {
        override fun bindView(itemView: View) {}
    }
}