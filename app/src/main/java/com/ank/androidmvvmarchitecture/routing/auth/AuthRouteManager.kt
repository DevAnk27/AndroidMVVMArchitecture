package com.ank.androidmvvmarchitecture.routing.auth


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

class AuthRouteManager
@Inject
constructor
(val context: Context) : AuthController {

    override fun launchAuthActivity(activity: AppCompatActivity) {
        context.apply {
            /*AuthActivity.startInstanceWithBackStackCleared(context)
            activity.overridePendingTransition(R.anim.activity_trans_left_in, R.anim.activity_trans_left_out)*/
        }
    }

    override fun launchDealsDashboardActivity(activity: AppCompatActivity) {
        /*DealsDashboardActivity.startInstanceWithBackStackCleared(context)
        activity.overridePendingTransition(R.anim.activity_trans_left_in, R.anim.activity_trans_left_out)*/
    }

    override fun launchOfferFragment(context: Context?, containerId: Int) {
        /*val offerFragment = OfferFragment.newInstance()
        replaceFragment(context, offerFragment, containerId)*/
    }

    override fun launchCountDownFragment(context: Context?, containerId: Int) {
        /*val countDownFragment = CountDownFragment.newInstance()
        replaceFragment(context, countDownFragment, containerId)*/
    }

    override fun popBackStack(context: Context?) {
        /*val activity = context as BaseActivity<*>
        if (activity.isAllowTransition()) {
            activity.supportFragmentManager.popBackStack()
        }*/
    }

    /*private fun replaceFragment(context: Context?, fragment: BaseFragment<ViewModel>, containerId: Int, isAddToBackStack: Boolean = false,
                                withAnimation: Boolean = true) {
        val activity = context as BaseActivity<*>
        if (!activity.isAllowTransition()) {
            return
        }

        val transaction = activity.supportFragmentManager.beginTransaction()
        if (isAddToBackStack) {
            transaction?.addToBackStack(fragment.getFragmentTAG().name)
        }

        if (withAnimation) {
            //transaction?.setCustomAnimations(R.anim.trans_left_in, R.anim.trans_left_out, R.anim.trans_right_in, R.anim.trans_right_out)
            transaction?.setCustomAnimations(R.anim.fragment_trans_left_in, R.anim.fragment_trans_left_out, R.anim.trans_left_in, R.anim.trans_left_out)
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