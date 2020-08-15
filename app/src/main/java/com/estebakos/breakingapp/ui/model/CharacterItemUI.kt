package com.estebakos.breakingapp.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterItemUI(
    val id: Int,
    val name: String,
    val occupation: List<String>,
    val imageUrl: String,
    val status: String,
    val nickname: String,
    val portrayed: String,
    var favorite: Boolean
) : Parcelable