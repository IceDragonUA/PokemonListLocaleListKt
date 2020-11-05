package com.evaluation.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.evaluation.adapter.AdapterItemClickListener

abstract class BaseViewHolder<T>(view: View, val listener: AdapterItemClickListener<T>?) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: T, language: String?)

}


