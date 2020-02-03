package ru.skillbranch.skillarticles.data.local

import android.content.Context
import androidx.preference.PreferenceManager

class PrefManager(context: Context) {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    fun clearAll() = preferences.all.clear()

}