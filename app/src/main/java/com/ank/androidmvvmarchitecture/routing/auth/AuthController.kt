package com.ank.androidmvvmarchitecture.routing.auth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity


interface AuthController {

    fun launchAuthActivity(activity: AppCompatActivity)

    fun launchDealsDashboardActivity(activity: AppCompatActivity)

    fun launchOfferFragment(context: Context?, containerId: Int)

    fun launchCountDownFragment(context: Context?, containerId: Int)

    fun popBackStack(context: Context?)
}
