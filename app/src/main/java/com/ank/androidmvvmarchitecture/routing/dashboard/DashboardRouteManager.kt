package com.ank.androidmvvmarchitecture.routing.dashboard/*



import android.content.Context
import com.ank.androidmvvmarchitecture.routing.dashboard.DashboardController
import com.ank.androidmvvmarchitecture.routing.dashboard.deal_details.DealDetailsController
import com.ank.androidmvvmarchitecture.routing.dashboard.deal_details.DealDetailsRoutManager
import javax.inject.Inject

class DashboardRouteManager
@Inject
constructor
(val context: Context, private val dealDetailsRoutManager: DealDetailsRoutManager) : DashboardController,
        DealDetailsController by dealDetailsRoutManager {

    override fun launchTourFragment(context: Context?, containerId: Int) {
        val arrangedTripsFragment = TourFragment.newInstance()
        replaceFragment(context, arrangedTripsFragment, containerId, withAnimation = false, isRemoveAllBackStack = true)
    }

    override fun launchPassoverFragment(context: Context?, containerId: Int) {
        val passoverFragment = PassoverFragment.newInstance()
        replaceFragment(context, passoverFragment, containerId, withAnimation = false, isRemoveAllBackStack = true)
    }

    override fun launchFlightsFragment(context: Context?, containerId: Int) {
        val flightsFragment = FlightsFragment.newInstance()
        replaceFragment(context, flightsFragment, containerId, withAnimation = false, isRemoveAllBackStack = true)
    }

    override fun launchPackagesFragment(context: Context?, containerId: Int) {
        val packagesFragment = PackagesFragment.newInstance()
        replaceFragment(context, packagesFragment, containerId, withAnimation = false, isRemoveAllBackStack = true)
    }

    override fun launchTodaysDealsFragment(context: Context?, containerId: Int) {
        val todaysDealFragment = TodaysDealFragment.newInstance()
        replaceFragment(context, todaysDealFragment, containerId, withAnimation = false, isRemoveAllBackStack = true)
    }

    override fun launchSportsFragment(context: Context?, containerId: Int) {
        val sportsPackagesFragment = SportsPackagesFragment.newInstance()
        replaceFragment(context, sportsPackagesFragment, containerId, withAnimation = false, isRemoveAllBackStack = true)
    }

    override fun launchConcertsFragment(context: Context?, containerId: Int) {
        val concertsFragment = ConcertsFragment.newInstance()
        replaceFragment(context, concertsFragment, containerId, withAnimation = false, isRemoveAllBackStack = true)
    }

    override fun launchNotificationDialogFragment(context: Context) {
        val activity = context as BaseActivity<*>
        if (activity.isAllowTransition()) {
            val notificationDialogFragment = NotificationDialogFragment.newInstance()
            notificationDialogFragment.show(activity.supportFragmentManager, BaseDialogFragment.FragmentTAG.NOTIFICATION_DIALOG_FRAG.name)
        }
    }

    override fun launchDealDetailsFragment(context: Context?, containerId: Int, bundle: Bundle) {
        val dealDetailsFragment = DealDetailsFragment.newInstance()
        dealDetailsFragment.arguments = bundle
        replaceFragment(context, dealDetailsFragment, containerId, true, withAnimation = true)
    }

    override fun launchPassengerCountFragment(context: Context?, containerId: Int, bundle: Bundle) {
        val passengerCountFragment = PassengerCountFragment.newInstance()
        passengerCountFragment.arguments = bundle
        replaceFragment(context, passengerCountFragment, containerId, true)
    }

    override fun launchSelectRoomFragment(context: Context?, containerId: Int, bundle: Bundle) {
        val selectRoomFragment = SelectRoomFragment.newInstance()
        selectRoomFragment.arguments = bundle
        replaceFragment(context, selectRoomFragment, containerId, true)
    }

    override fun launchSelectFlightFragment(context: Context?, containerId: Int, bundle: Bundle) {
        val selectFlightFragment = SelectFlightFragment.newInstance()
        selectFlightFragment.arguments = bundle
        replaceFragment(context, selectFlightFragment, containerId, true)
    }

    override fun launchFlightForthReturnDetailFragment(context: Context?, containerId: Int, bundle: Bundle) {
        val flightForthReturnDetailsFragment = FlightForthReturnDetailsFragment.newInstance()
        flightForthReturnDetailsFragment.arguments = bundle
        replaceFragment(context, flightForthReturnDetailsFragment, containerId, true)
    }

    override fun launchPassengerDetailsFragment(context: Context?, containerId: Int, bundle: Bundle) {
        val passengerDetailsFragment = PassengerDetailsFragment.newInstance()
        passengerDetailsFragment.arguments = bundle
        replaceFragment(context, passengerDetailsFragment, containerId, true)
    }

    override fun launchPaymentDetailsFragment(context: Context?, containerId: Int, bundle: Bundle) {
        val paymentDetailsFragment = PaymentDetailsFragment.newInstance()
        paymentDetailsFragment.arguments = bundle
        replaceFragment(context, paymentDetailsFragment, containerId, true)
    }

    override fun launchOrderConfirmationFragment(context: Context?, containerId: Int, bundle: Bundle) {
        val orderConfirmationFragment = OrderConfirmationFragment.newInstance()
        orderConfirmationFragment.arguments = bundle
        replaceFragment(context, orderConfirmationFragment, containerId, true)
    }

    override fun launchOrderConfirmedDialogFragment(context: Context?, bundle: Bundle) {
        val activity = context as BaseActivity<*>
        if (activity.isAllowTransition()) {
            val orderConfirmationFragment = OrderConfirmedDialogFragment.newInstance()
            orderConfirmationFragment.arguments = bundle
            orderConfirmationFragment.show(activity.supportFragmentManager, BaseDialogFragment.FragmentTAG.ORDER_CONFIRMED_DIALOG_FRAG.name)
        }
    }

    override fun launchSalesNotificationFragment(context: Context?, containerId: Int) {
        val salesNotificationFragment = SalesNotificationFragment.newInstance()
        replaceFragment(context, salesNotificationFragment, containerId, true)
    }

    override fun launchDestinationNotificationFragment(context: Context?, containerId: Int) {
        val destinationNotificationFragment = DestinationNotificationFragment.newInstance()
        replaceFragment(context, destinationNotificationFragment, containerId, true)
    }

    override fun launchNotificationSwitchesFragment(context: Context?, containerId: Int) {
        val notificationSwitchesFragment = NotificationSwitchesFragment.newInstance()
        replaceFragment(context, notificationSwitchesFragment, containerId, true)
    }

    override fun launchFilterFragment(context: Context?, containerId: Int) {
        val filterFragment = FilterFragment.newInstance()
        replaceFragment(context, filterFragment, containerId, true)
    }

    override fun launchFilterResultFragment(context: Context?, containerId: Int, bundle: Bundle) {
        val filterResultFragment = FilterResultFragment.newInstance()
        filterResultFragment.arguments = bundle
        replaceFragment(context, filterResultFragment, containerId, true)
    }

    override fun launchCalendarDialogFragment(context: Fragment?) {
        val fragment = context as BaseFragment<*>
        if (fragment.isAllowTransition()) {
            val calendarDialogFragment = CalendarDialogFragment.newInstance()
            //calendarDialogFragment.setStyle(R.style.myCustomDialog, R.style.AppTheme)
            calendarDialogFragment.show(fragment.childFragmentManager, BaseDialogFragment.FragmentTAG.CALENDAR_DIALOG_FRAG.name)
        }
    }

    override fun launchHotelGalleryFragment(context: Context?, containerId: Int) {
        val hotelGalleryFragment = HotelGalleryFragment.newInstance()
        replaceFragment(context, hotelGalleryFragment, containerId, true)
    }

    override fun launchGalleryDetailDialogFragment(context: Context?, bundle: Bundle) {
        val activity = context as BaseActivity<*>
        if (activity.isAllowTransition()) {
            val galleryDetailDialogFragment = GalleryDetailDialogFragment.newInstance()
            galleryDetailDialogFragment.arguments = bundle
            galleryDetailDialogFragment.show(activity.supportFragmentManager, BaseDialogFragment.FragmentTAG.GALLERY_DETAIL_DIALOG_FRAG.name)
        }
    }

    override fun launchValidationAlertDialogFragment(context: Context?, bundle: Bundle) {
        val activity = context as BaseActivity<*>
        if (activity.isAllowTransition()) {
            val validationAlertDialogFragment = ValidationAlertDialogFragment.newInstance()
            validationAlertDialogFragment.arguments = bundle
            validationAlertDialogFragment.show(activity.supportFragmentManager, BaseDialogFragment.FragmentTAG.VALIDATION_ALERT_DIALOG_FRAG.name)
        }
    }

    override fun launchDestinationDialogFragment(context: Context?, bundle: Bundle) {
        val activity = context as BaseActivity<*>
        if (activity.isAllowTransition()) {
            val destinationDialogFragment = DestinationDialogFragment.newInstance()
            destinationDialogFragment.arguments = bundle
            destinationDialogFragment.show(activity.supportFragmentManager, BaseDialogFragment.FragmentTAG.DESTINATION_DIALOG_FRAG.name)
        }
    }

    override fun getDealDetailsRoutManager(): DealDetailsRoutManager = dealDetailsRoutManager


    private fun replaceFragment(context: Context?, fragment: BaseFragment<ViewModel>, containerId: Int, isAddToBackStack: Boolean = false,
                                withAnimation: Boolean = true, isRemoveAllBackStack: Boolean = false) {
        val activity = context as BaseActivity<*>

        if (!activity.isAllowTransition()) {
            return
        }

        if (isRemoveAllBackStack) {
            removeAllFragmentBackStack(context)
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

    fun removeAllFragmentBackStack(context: Context?) {
        val activity = context as AppCompatActivity
        val fragmentManager = activity.supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            FragmentUtils.isDisableFragmentAnimations = true
            activity.supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            FragmentUtils.isDisableFragmentAnimations = false
        }
    }

    //override fun getHomeRouteManager(): HomeRoutManager = homeRoutManager
}
*/
