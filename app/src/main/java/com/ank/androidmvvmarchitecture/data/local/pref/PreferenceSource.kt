package com.ank.androidmvvmarchitecture.data.local.pref

import androidmvvm.data.model.User
import com.ank.androidmvvmarchitecture.data.model.Destination


interface PreferenceSource {

    enum class LoggedInMode(val mode: Int) {
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3)
    }

    fun saveUser(user: User)
    fun getUser(): User?
    fun saveDestinations(destinations: ArrayList<Destination>)
    fun getDestinations(): ArrayList<Destination>
    fun isUserLoggedIn(): Boolean
    fun saveIsNoNotification(onOrOff: Boolean)
    fun getIsNoNotification(): Boolean
    fun saveIsGeneralNotification(onOrOff: Boolean)
    fun getIsGeneralNotification(): Boolean
    fun saveIsPackagesNotification(onOrOff: Boolean)
    fun getIsPackagesNotification(): Boolean
    fun saveIsFlightsNotification(onOrOff: Boolean)
    fun getIsFlightsNotification(): Boolean
    fun saveNotificationSettingInserted(onOrOff: Boolean)
    fun getNotificationSettingInserted(): Boolean
    fun saveNotificationMode(intMode: Int)
    fun getNotificationMode(): Int
    fun clearSession()
}