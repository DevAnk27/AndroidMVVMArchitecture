package com.ank.androidmvvmarchitecture.data.local.pref

import android.content.Context
import androidmvvm.data.model.User
import com.ank.androidmvvmarchitecture.data.model.Destination
import com.ank.androidmvvmarchitecture.di.qualifiers.PreferenceInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


class AppPreferenceManager
@Inject
constructor(private val context: Context,
            @PreferenceInfo prefFileName: String) : Preferences(), PreferenceSource {
    init {
        Preferences.init(context, prefFileName)
    }

    var isLoggedIn by booleanPref("isLoggedIn")
    var isNoNotification by booleanPref("isNoNotification")
    var isGeneralNotification by booleanPref("isGeneralNotification")
    var isPackageNotification by booleanPref("isPackageNotification")
    var isFlightsNotification by booleanPref("isFlightsNotification")
    var isNotificationSettingInserted by booleanPref("isNotificationSettingInserted")
    var user by stringPref("user")
    var strDestinations by stringPref("destinations")
    var userLoggedInMode by intPref("userLoggedInMode")
    var notifMode by intPref("notificationMode")

    override fun saveUser(user: User) {
        val gson = Gson()
        val userPreferenceJsonString = gson.toJson(user, User::class.java)
        this.user = userPreferenceJsonString
        isLoggedIn = true
    }

    override fun getUser(): User? {
        val gson = Gson()
        return if (user == null) {
            null
        } else {
            gson.fromJson(user, User::class.java)
        }
    }

    override fun saveDestinations(destinations: ArrayList<Destination>) {
        val gson = Gson()
        val json = gson.toJson(destinations)
        strDestinations = json
    }

    override fun getDestinations(): ArrayList<Destination> {
        val destinationList: ArrayList<Destination>
        if (strDestinations != null) {
            val type = object : TypeToken<ArrayList<Destination>>() {}.type
            destinationList = Gson().fromJson<ArrayList<Destination>>(strDestinations, type)
        } else {
            destinationList = mutableListOf<Destination>() as ArrayList<Destination>
        }
        return destinationList
    }

    override fun isUserLoggedIn(): Boolean {
        return isLoggedIn
    }

    override fun saveNotificationMode(intMode: Int) {
        notifMode = intMode
    }

    override fun getNotificationMode(): Int {
        return notifMode
    }

    override fun saveIsNoNotification(onOrOff: Boolean) {
        isNoNotification = onOrOff
    }

    override fun getIsNoNotification(): Boolean {
        return isNoNotification
    }

    override fun saveIsGeneralNotification(onOrOff: Boolean) {
        isGeneralNotification = onOrOff
    }

    override fun getIsGeneralNotification(): Boolean {
        return isGeneralNotification
    }

    override fun saveIsPackagesNotification(onOrOff: Boolean) {
        isPackageNotification = onOrOff
    }

    override fun getIsPackagesNotification(): Boolean {
        return isPackageNotification
    }

    override fun saveIsFlightsNotification(onOrOff: Boolean) {
        isFlightsNotification = onOrOff
    }

    override fun getIsFlightsNotification(): Boolean {
        return isFlightsNotification
    }

    override fun saveNotificationSettingInserted(onOrOff: Boolean) {
        isNotificationSettingInserted = onOrOff
    }

    override fun getNotificationSettingInserted(): Boolean {
        return isNotificationSettingInserted
    }

    override fun clearSession() {
        isLoggedIn = false
    }
}
