package com.evaluation.adapter.viewholder.item

import com.evaluation.adapter.factory.TypesFactory

interface BaseItemView {

    var index: String

    var next: Boolean

    fun type(typesFactory: TypesFactory): Int

}