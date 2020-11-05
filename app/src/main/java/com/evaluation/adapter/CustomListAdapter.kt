package com.evaluation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.evaluation.adapter.diffutils.DiffItemCallback
import com.evaluation.adapter.factory.TypesFactory
import com.evaluation.adapter.viewholder.BaseViewHolder
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.utils.emptyString
import kotlin.properties.Delegates

class CustomListAdapter constructor(private val typeFactory: TypesFactory, private val listener: AdapterItemClickListener<*>? = null) :
    PagedListAdapter<BaseItemView, BaseViewHolder<BaseItemView>>(DiffItemCallback()) {

    var language: String by Delegates.observable(emptyString()) { _, _, _ ->
        notifyDataSetChanged()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseItemView> =
        typeFactory.holder(viewType, LayoutInflater.from(parent.context).inflate(viewType, parent, false), listener) as BaseViewHolder<BaseItemView>

    override fun onBindViewHolder(holder: BaseViewHolder<BaseItemView>, position: Int) {
        holder.bind(getItem(position)!!, language)
    }

    override fun getItemViewType(position: Int): Int = getItem(position)!!.type(typeFactory)

}