package com.aptivist.tripsupiicsaapp.data.local.backup

import com.google.gson.GsonBuilder

val gson = GsonBuilder().create()

fun BackupPayload.toJson(): String {
    return gson.toJson(this)
}

fun String.toBackupPayload(): BackupPayload {
    return gson.fromJson(this, BackupPayload::class.java)
}

fun <T> String.fromJsonNoReified(clazz: Class<T>): T {
    return gson.fromJson(this, clazz)
}

/*fun <T> String.fromJsonNoReified(): T {
    return gson.fromJson(this, T::class.java)
}*/

inline fun <reified T> String.fromJson(): T {
    return gson.fromJson(this, T::class.java)
}

