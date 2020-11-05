package com.evaluation.adapter

/**
 * @author Vladyslav Havrylenko
 * @since 09.10.2020
 */
interface AdapterItemClickListener<T> {

    fun onClicked(item: T)

}