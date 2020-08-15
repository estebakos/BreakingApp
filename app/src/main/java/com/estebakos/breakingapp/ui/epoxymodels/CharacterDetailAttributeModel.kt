package com.estebakos.breakingapp.ui.epoxymodels

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.estebakos.breakingapp.R

@EpoxyModelClass(layout = R.layout.item_character_detail_attribute)
abstract class CharacterDetailAttributeModel :
    EpoxyModelWithHolder<CharacterDetailAttributeModel.Holder>() {

    @EpoxyAttribute
    @StringRes
    var label: Int = -1

    @EpoxyAttribute
    lateinit var value: String

    override fun bind(holder: Holder) {
        with(holder) {
            textLabel.text = context.getText(label)
            textValue.text = value
        }
    }

    inner class Holder : EpoxyHolder() {
        lateinit var textLabel: TextView
        lateinit var textValue: TextView
        lateinit var context: Context

        override fun bindView(itemView: View) {
            textLabel = itemView.findViewById(R.id.text_attribute_label)
            textValue = itemView.findViewById(R.id.text_attribute_value)
            context = itemView.context
        }
    }
}