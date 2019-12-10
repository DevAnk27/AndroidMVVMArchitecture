package androidmvvm.ui.deals_dashboard

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import androidmvvm.R
import androidmvvm.data.AppDataManager
import androidmvvm.data.model.Tab
import androidmvvm.data.remote.FlightDetailManager
import androidmvvm.data.remote.PackageDetailManager
import androidmvvm.ui.base.BaseActivity
import androidmvvm.ui.base.BaseFragment
import androidmvvm.ui.deals_dashboard.booking_process.passenger_detail.PassengerDetailsFragment
import androidmvvm.ui.deals_dashboard.booking_process.payment_details.PaymentDetailsFragment
import androidmvvm.ui.deals_dashboard.filter_result.FilterResultFragment
import androidmvvm.utils.Constants
import androidmvvm.utils.DeviceInfo
import com.google.firebase.iid.FirebaseInstanceId
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_dashboard.*
import javax.inject.Inject

class DealsDashboardActivity : BaseActivity<DashboardActivityVM>(), HasSupportFragmentInjector, DashboardTabsRVAdapter.TabClickListener {

    companion object {
        fun startInstanceWithBackStackCleared(context: Context?) {
            val intent = Intent(context, DealsDashboardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)
        }
    }

    @Inject
    lateinit var dashboardActivityVM: DashboardActivityVM
    @Inject
    lateinit var appDataManager: AppDataManager
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private var k = 0
    lateinit var dashboardTabsRVAdapter: DashboardTabsRVAdapter

    val authBroadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            navigationController.launchAuthActivity(this@DealsDashboardActivity as AppCompatActivity)
        }
    }

    override fun getViewModel(): DashboardActivityVM = dashboardActivityVM

    override fun hideBottomTabs() {
        super.showBottomTabs()
        rv_tabs.visibility = View.GONE
        v_shadow.visibility = View.GONE
    }

    override fun showBottomTabs() {
        super.showBottomTabs()
        rv_tabs.visibility = View.VISIBLE
        v_shadow.visibility = View.VISIBLE
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    override fun onTabClicked(tab: Tab) {
        dashboardTabsRVAdapter.setTabChecked(tab)
        launchTab(tab.name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }
        setContentView(R.layout.activity_dashboard)

        initInstances()
    }

    private fun initInstances() {
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(authBroadCastReceiver, IntentFilter(Constants.BROADCAST_AUTH_INTENT))

        subscribeToLiveData()

        navigationController.launchTodaysDealsFragment(this, R.id.fl_dashboard_container)

        if (!appDataManager.getNotificationSettingInserted()) {
            val refreshedToken = FirebaseInstanceId.getInstance().token
            dashboardActivityVM.insertNotificationSetting(DeviceInfo.getSerialNumber(), refreshedToken,
                    true, true, true, true)
        }
    }

    private fun subscribeToLiveData() {
        dashboardActivityVM.tabs.observe(this, Observer { tabs ->
            setUpTabLayout(tabs as ArrayList<Tab>?)
        })

        dashboardActivityVM.isLoading.observe(this, Observer { isLoading ->
            if (isLoading!!) {
                helper?.showProgressDialog("", getString(R.string.please_wait))
            } else {
                helper?.dismissProgressDialog()
            }
        })

        dashboardActivityVM.error.observe(this, Observer { error ->
            //helper?.showAlertDialog("", error!!, getString(R.string.ok))
            super.onBackPressed()
        })

        dashboardActivityVM.isSuccess.observe(this, Observer { isSignUpSuccess ->
            super.onBackPressed()
        })

        dashboardActivityVM.successMessage.observe(this, Observer { successMessage ->
            helper?.showShortToast(successMessage!!)
        })

        dashboardActivityVM.isNotificationSettingInserted.observe(this, Observer { isNotificationSettingInserted ->
            if (isNotificationSettingInserted != null) {
                appDataManager.saveNotificationSettingInserted(isNotificationSettingInserted)
            }
        })
    }

    private fun setUpTabLayout(tabs: ArrayList<Tab>?) {
        rv_tabs.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        dashboardTabsRVAdapter = DashboardTabsRVAdapter(this, tabs, helper)
        rv_tabs.adapter = dashboardTabsRVAdapter
        dashboardTabsRVAdapter.setTabClickListener(this)
    }

    private fun launchTab(tabName: String) {
        when (tabName) {
            Constants.DealsHomeTabs.TODAYS_DEALS.value -> {
                navigationController.launchTodaysDealsFragment(this, R.id.fl_dashboard_container)
            }

            Constants.DealsHomeTabs.PACKAGES.value -> {
                navigationController.launchPackagesFragment(this, R.id.fl_dashboard_container)
            }

            Constants.DealsHomeTabs.FLIGHTS.value -> {
                navigationController.launchFlightsFragment(this, R.id.fl_dashboard_container)
            }

            Constants.DealsHomeTabs.TOUR.value -> {
                navigationController.launchTourFragment(this, R.id.fl_dashboard_container)
            }

            Constants.DealsHomeTabs.PASSOVER.value -> {
                navigationController.launchPassoverFragment(this, R.id.fl_dashboard_container)
            }

            Constants.DealsHomeTabs.SPORTS.value -> {
                navigationController.launchSportsFragment(this, R.id.fl_dashboard_container)
            }

            Constants.DealsHomeTabs.CONCERTS.value -> {
                navigationController.launchConcertsFragment(this, R.id.fl_dashboard_container)
            }
        }
    }

    override fun onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(authBroadCastReceiver)
        super.onStop()
    }

    override fun onBackPressed() {
        val filterResultFrag = supportFragmentManager.findFragmentByTag(BaseFragment.FragmentTAG.FILTER_RESULT_FRAG.name) as FilterResultFragment?
        val paymentDetailFrag = supportFragmentManager.findFragmentByTag(BaseFragment.FragmentTAG.PAYMENT_DETAILS_FRAG.name) as PaymentDetailsFragment?
        if (supportFragmentManager.backStackEntryCount <= 0) {
            ++k
            if (k == 1) {
                Toast.makeText(this, getString(R.string.back_press_message), Toast.LENGTH_SHORT).show()
                Handler().postDelayed({ k = 0 }, 2000)
            } else if (k == 2) {
                super.onBackPressed()
            } else {
                super.onBackPressed()
            }
        } else if (paymentDetailFrag != null && paymentDetailFrag.isVisible) {
            removeReservePlaces()
        } else if (filterResultFrag != null && filterResultFrag.isVisible) {
            /*navigationController.launchDealsDashboardActivity()
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)*/
            super.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    fun removeReservePlaces() {
        AlertDialog.Builder(this).setTitle(getString(R.string.message))
                .setMessage(getString(R.string.confirm_order_cancelation))
                .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                    dialog.dismiss()

                    val passengerDetailFrag = supportFragmentManager.findFragmentByTag(BaseFragment.FragmentTAG.PASSENGER_DETAILS_FRAG.name) as PassengerDetailsFragment?
                    if (passengerDetailFrag != null) {
                        val deal = passengerDetailFrag.deal
                        val passengerList = passengerDetailFrag.mPassengerList
                        val resUniqueID = passengerDetailFrag.resUniqueID

                        if (deal?.Category_Id.equals(Constants.CATEGORY.FLIGHT)) {
                            val flightDetails = FlightDetailManager.getInstance(this).getSelectedFlight()
                            dashboardActivityVM.callDeleteReservePlaceForFlight(passengerList, flightDetails, deal, resUniqueID)
                        } else {
                            val flightDetails = FlightDetailManager.getInstance(this).getSelectedFlight()
                            val hotel = PackageDetailManager.getInstance(this).packageList.value?.get(0)
                            val selectedRoomList = PackageDetailManager.getInstance(this).selectedRoomList

                            dashboardActivityVM.callDeleteReservePlaceForFlightAndHotel(passengerList, flightDetails,
                                    selectedRoomList, hotel, deal, resUniqueID)
                        }
                    }
                }
                .setNegativeButton(getString(R.string.no)) { dialog, which ->
                    dialog.dismiss()
                }.create().show()
    }
}
