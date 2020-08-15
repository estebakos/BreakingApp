package com.estebakos.breakingapp.ui.epoxymodels

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.estebakos.breakingapp.R

@EpoxyModelClass(layout = R.layout.loader_list_item)
abstract class LoadMoreView : EpoxyModel<View>()