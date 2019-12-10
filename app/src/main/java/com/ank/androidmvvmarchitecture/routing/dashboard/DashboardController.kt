package com.ank.androidmvvmarchitecture.routing.dashboard

import android.app.Fragment
import android.content.Context
import android.os.Bundle

import com.ank.androidmvvmarchitecture.routing.dashboard.deal_details.DealDetailsRoutManager


interface DashboardController {

    fun launchNotificationDialogFragment(context: Context)

    fun launchTourFragment(context: Context?, containerId: Int)

    fun launchPassoverFragment(context: Context?, containerId: Int)

    fun launchFlightsFragment(context: Context?, containerId: Int)

    fun launchPackagesFragment(context: Context?, containerId: Int)

    fun launchTodaysDealsFragment(context: Context?, containerId: Int)

    fun launchSportsFragment(context: Context?, containerId: Int)

    fun launchConcertsFragment(context: Context?, containerId: Int)

    fun launchDealDetailsFragment(context: Context?, containerId: Int, bundle: Bundle)

    fun getDealDetailsRoutManager(): DealDetailsRoutManager

    fun launchPassengerCountFragment(context: Context?, containerId: Int, bundle: Bundle)

    fun launchSelectRoomFragment(context: Context?, containerId: Int, bundle: Bundle)

    fun launchSelectFlightFragment(context: Context?, containerId: Int, bundle: Bundle)

    fun launchFlightForthReturnDetailFragment(context: Context?, containerId: Int, bundle: Bundle)

    fun launchPassengerDetailsFragment(context: Context?, containerId: Int, bundle: Bundle)

    fun launchPaymentDetailsFragment(context: Context?, containerId: Int, bundle: Bundle)

    fun launchOrderConfirmationFragment(context: Context?, containerId: Int, bundle: Bundle)

    fun launchOrderConfirmedDialogFragment(context: Context?, bundle: Bundle)

    fun launchSalesNotificationFragment(context: Context?, containerId: Int)

    fun launchDestinationNotificationFragment(context: Context?, containerId: Int)

    fun launchNotificationSwitchesFragment(context: Context?, containerId: Int)

    fun launchFilterFragment(context: Context?, containerId: Int)

    fun launchFilterResultFragment(context: Context?, containerId: Int, bundle: Bundle)

    fun launchCalendarDialogFragment(context: Fragment?)

    fun launchHotelGalleryFragment(context: Context?, containerId: Int)

    fun launchGalleryDetailDialogFragment(context: Context?, bundle: Bundle)

    fun launchValidationAlertDialogFragment(context: Context?, bundle: Bundle)

    fun launchDestinationDialogFragment(context: Context?, bundle: Bundle)

}