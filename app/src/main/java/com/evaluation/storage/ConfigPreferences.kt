package com.evaluation.storage

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigPreferences @Inject constructor(context: Context) {

    companion object {
        private const val NAME = "CONFIG_PREFS"
        private const val BOOT = "boot"
        private const val LANG = "lang"
    }

    private val sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    fun saveBoot(boot: Boolean) {
        sp.edit().apply {
            putBoolean(BOOT, boot)
            apply()
        }
    }

    fun restoreBoot(): Boolean {
        return sp.getBoolean(BOOT, false)
    }

    fun saveLanguage(language: String?) {
        sp.edit().apply {
            putString(LANG, language)
            apply()
        }
    }

    fun restoreLanguage(): String? {
        return sp.getString(LANG, null)
    }
}