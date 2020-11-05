package com.evaluation.utils

import android.os.Handler

/**
 * @author Vladyslav Havrylenko
 * @since 29.10.2020
 */
class Run {
    companion object {
        fun after(delay: Long, process: () -> Unit) {
            Handler().postDelayed({
                process()
            }, delay)
        }
    }
}