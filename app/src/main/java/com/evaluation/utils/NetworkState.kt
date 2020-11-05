package com.evaluation.utils

enum class NetworkState(private val state: Boolean) {
    LOADING(true),
    LOADED(false);
    fun value() = state
}