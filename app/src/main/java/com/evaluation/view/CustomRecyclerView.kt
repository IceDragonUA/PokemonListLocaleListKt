package com.evaluation.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evaluation.adapter.AdapterItemClickListener
import com.evaluation.adapter.CustomListAdapter
import com.evaluation.adapter.factory.TypesFactoryImpl
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.storage.ConfigPreferences
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Vladyslav Havrylenko
 * @since 03.10.2020
 */
@AndroidEntryPoint
class CustomRecyclerView : RecyclerView, AdapterItemClickListener<BaseItemView> {

    lateinit var listener: AdapterItemClickListener<BaseItemView>

    @Inject lateinit var configPreferences: ConfigPreferences

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        layoutManager = LinearLayoutManager(context)
        adapter = CustomListAdapter(TypesFactoryImpl(), this, configPreferences)
        itemAnimator = null
    }

    override fun getAdapter(): CustomListAdapter? =
        super.getAdapter() as? CustomListAdapter

    override fun onClicked(item: BaseItemView) {
        listener.onClicked(item)
    }
}