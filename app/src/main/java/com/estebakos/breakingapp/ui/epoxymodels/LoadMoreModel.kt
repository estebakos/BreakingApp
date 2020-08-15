package com.estebakos.breakingapp.ui.epoxymodels

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.estebakos.breakingapp.R

@EpoxyModelClass(layout = R.layout.item_loader_list)
abstract class LoadMoreModel : EpoxyModel<View>()