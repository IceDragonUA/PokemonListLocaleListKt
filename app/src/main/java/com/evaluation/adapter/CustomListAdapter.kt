package com.evaluation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.evaluation.adapter.diffutils.DiffUtilCallback
import com.evaluation.adapter.factory.TypesFactory
import com.evaluation.adapter.viewholder.BaseViewHolder
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.storage.ConfigPreferences
import kotlin.properties.Delegates

class CustomListAdapter constructor(
    private val typeFactory: TypesFactory,
    private val listener: AdapterItemClickListener<*>? = null,
    private val configPreferences: ConfigPreferences,
) : RecyclerView.Adapter<BaseViewHolder<BaseItemView>>(), DiffUtilCallback, Filterable {

    var items: MutableList<BaseItemView> by Delegates.observable(mutableListOf()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.index == n.index }
    }

    var itemsNotFiltered: MutableList<BaseItemView> = mutableListOf()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseItemView> =
        typeFactory.holder(viewType, LayoutInflater.from(parent.context).inflate(viewType, parent, false), listener) as BaseViewHolder<BaseItemView>

    override fun onBindViewHolder(holder: BaseViewHolder<BaseItemView>, position: Int) {
        holder.bind(items[position], configPreferences.restoreLanguage())
    }

    override fun getItemViewType(position: Int): Int = items[position].type(typeFactory)

    override fun getItemCount(): Int = items.count()

    override fun getFilter(): Filter? = object : Filter() {

        @SuppressLint("DefaultLocale")
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val text = charSequence.toString()
            val filterResults = FilterResults()
            filterResults.values = if (text.isEmpty()) {
                itemsNotFiltered
            } else {
                val filteredList: MutableList<BaseItemView> = mutableListOf()
                for (row in itemsNotFiltered) {
                    if (row.name.toLowerCase().contains(text.toLowerCase())) {
                        filteredList.add(row)
                    }
                }
                filteredList
            }
            return filterResults
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            items = filterResults.values as MutableList<BaseItemView>
        }

    }
}