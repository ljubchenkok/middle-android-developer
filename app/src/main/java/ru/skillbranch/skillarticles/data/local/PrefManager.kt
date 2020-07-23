package ru.skillbranch.skillarticles.data.local

import android.content.SharedPreferences
import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import ru.skillbranch.skillarticles.App
import ru.skillbranch.skillarticles.data.delegates.PrefDelegate
import ru.skillbranch.skillarticles.data.models.AppSettings

object PrefManager {

    internal val preferences : SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(App.applicationContext())
    }

    private var isDarkMode by PrefDelegate(false)
    private var isBigText by PrefDelegate(false)
    private var isAuth by PrefDelegate(false)
    private var appSettingsLiveData = MutableLiveData(AppSettings(isDarkMode ?: false, isBigText ?: false))
    private var isAuthLiveData = MutableLiveData(false)

    fun clearAll(){
        preferences.edit().clear().apply()
    }

    fun isAuth(): LiveData<Boolean> = isAuthLiveData
    fun getAppSettings(): LiveData<AppSettings> = appSettingsLiveData

    @UiThread
    fun setAuth(auth: Boolean) {
        isAuth = auth
        isAuthLiveData.value = auth
    }

    @UiThread
    fun updateAppSettings(appSettings: AppSettings) {
        isDarkMode = appSettings.isDarkMode
        isBigText = appSettings.isBigText
        appSettingsLiveData.value = appSettings
    }
}