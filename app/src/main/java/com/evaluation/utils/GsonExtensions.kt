package com.evaluation.utils

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

inline fun <reified T> Gson.fromJson(json: String): T = this.fromJson(json, object : TypeToken<T>() {}.type)

inline fun <reified T> Gson.toTypedJson(clazz: T, parentType: Type): String = this.toJson(clazz, parentType)