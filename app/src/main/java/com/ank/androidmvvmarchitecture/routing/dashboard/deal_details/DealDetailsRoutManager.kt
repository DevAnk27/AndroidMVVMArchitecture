package com.ank.androidmvvmarchitecture.routing.dashboard.deal_details


import android.content.Context
import android.os.Bundle
import com.ank.androidmvvmarchitecture.routing.dashboard.deal_details.DealDetailsController
import javax.inject.Inject

/**
 * Created by CIS Dev 816 on 12/4/18.
 */
class DealDetailsRoutManager @Inject constructor(val context: Context) : DealDetailsController {

    override fun launchPriceDetailFragment(context: Context?, containerId: Int, bundle: Bundle) {
        /*val priceDetailFragment = PriceDetailFragment.newInstance()
        priceDetailFragment.arguments = bundle
        replaceFragment(context, priceDetailFragment, containerId, withAnimation = false)*/
    }

    override fun launchPackagePriceDetailsFragment(context: Context?, containerId: Int, bundle: Bundle) {
        /*val packagePriceDetailFragment = PackagePriceDetailFragment.newInstance()
        packagePriceDetailFragment.arguments = bundle
        replaceFragment(context, packagePriceDetailFragment, containerId, withAnimation = false)*/
    }

    override fun launchAboutTourDetailsFragment(context: Context?, containerId: Int, bundle: Bundle) {
        /*val aboutTourFragment = AboutTourFragment.newInstance()
        aboutTourFragment.arguments = bundle
        replaceFragment(context, aboutTourFragment, containerId, withAnimation = false)*/
    }

    override fun launchOnTheTourFragment(context: Context?, containerId: Int, bundle: Bundle) {
        /*val onTheTourFragment = OnTheTourFragment.newInstance()
        onTheTourFragment.arguments = bundle
        replaceFragment(context, onTheTourFragment, containerId, withAnimation = false)*/
    }

    override fun launchFlightsDetailFragment(context: Context?, containerId: Int, bundle: Bundle) {
        /*val flightsDetailFragment = FlightsDetailFragment.newInstance()
        flightsDetailFragment.arguments = bundle
        replaceFragment(context, flightsDetailFragment, containerId, withAnimation = false)*/
    }

    override fun launchHotelDetailsFragment(context: Context?, containerId: Int, bundle: Bundle) {
        /*val hotelDetailsFragment = HotelDetailsFragment.newInstance()
        hotelDetailsFragment.arguments = bundle
        replaceFragment(context, hotelDetailsFragment, containerId, withAnimation = false)*/
    }

    /*private fun replaceFragment(context: Context?, fragment: BaseChildFragment<ViewModel>, containerId: Int, isAddToBackStack: Boolean = false,
                                withAnimation: Boolean = true) {
        val activity = context as AppCompatActivity
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (isAddToBackStack) {
            //transaction.addToBackStack(fragment.getFragmentTAG().name)
        }

        if (withAnimation) {
            //transaction?.setCustomAnimations(R.anim.trans_left_in, R.anim.trans_left_out, R.anim.trans_right_in, R.anim.trans_right_out)
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        transaction?.replace(containerId, fragment, fragment.getFragmentTAG().name)
        transaction?.commit()
    }

    fun removeAllFragmentBackStack() {
        val activity = context as AppCompatActivity
        val fragmentManager = activity.supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            FragmentUtils.isDisableFragmentAnimations = true
            activity.supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            FragmentUtils.isDisableFragmentAnimations = false
        }
    }*/
}