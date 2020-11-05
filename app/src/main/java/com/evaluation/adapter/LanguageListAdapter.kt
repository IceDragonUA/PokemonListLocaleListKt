package com.evaluation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evaluation.adapter.diffutils.DiffUtilCallback
import com.evaluation.adapter.factory.TypesFactory
import com.evaluation.adapter.viewholder.BaseViewHolder
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.utils.emptyString
import kotlin.properties.Delegates

class LanguageListAdapter constructor(private val typeFactory: TypesFactory, private val listener: AdapterItemClickListener<*>? = null) :
    RecyclerView.Adapter<BaseViewHolder<BaseItemView>>(), DiffUtilCallback {

    var items: MutableList<BaseItemView> by Delegates.observable(mutableListOf()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.index == n.index }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseItemView> =
        typeFactory.holder(viewType, LayoutInflater.from(parent.context).inflate(viewType, parent, false), listener) as BaseViewHolder<BaseItemView>

    override fun onBindViewHolder(holder: BaseViewHolder<BaseItemView>, position: Int) {
        holder.bind(items[position], emptyString())
    }

    override fun getItemViewType(position: Int): Int = items[position].type(typeFactory)

    override fun getItemCount(): Int = items.count()

}