package com.ank.androidmvvmarchitecture.routing.dashboard.deal_details

import android.content.Context
import android.os.Bundle

/**
 * Created by CIS Dev 816 on 12/4/18.
 */
interface DealDetailsController {


    fun launchPriceDetailFragment(context: Context?, containerId: Int, bundle: Bundle)

    fun launchPackagePriceDetailsFragment(context: Context?, containerId: Int, bundle: Bundle)

    fun launchAboutTourDetailsFragment(context: Context?, containerId: Int, bundle: Bundle)

    fun launchOnTheTourFragment(context: Context?, containerId: Int, bundle: Bundle)

    fun launchFlightsDetailFragment(context: Context?, containerId: Int, bundle: Bundle)

    fun launchHotelDetailsFragment(context: Context?, containerId: Int, bundle: Bundle)

}