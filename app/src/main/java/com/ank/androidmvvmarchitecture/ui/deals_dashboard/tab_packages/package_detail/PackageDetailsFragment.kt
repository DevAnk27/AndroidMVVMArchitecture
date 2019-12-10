package androidmvvm.ui.deals_dashboard.tab_packages.package_detail


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidmvvm.R
import androidmvvm.data.model.Deal
import androidmvvm.data.model.Tab
import androidmvvm.di.module.AppViewModelFactory
import androidmvvm.ui.base.BaseFragment
import androidmvvm.utils.Constants
import androidmvvm.utils.toolbar.FragmentToolbar
import androidmvvm.utils.toolbar.callbacks.IBackButtonCallback
import androidmvvm.utils.toolbar.callbacks.INotificationCallback
import kotlinx.android.synthetic.main.fragment_package_details.*
import kotlinx.android.synthetic.main.item_deal.*
import javax.inject.Inject

/**
 * This fragment is not in use
 * */
class PackageDetailsFragment : BaseFragment<PackageDetailFragmentVM>(), View.OnClickListener {

    companion object {

        @JvmStatic
        fun newInstance() =
                PackageDetailsFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    @Inject
    lateinit var appViewModelFactory: AppViewModelFactory
    lateinit var packageDetailFragmentVM: PackageDetailFragmentVM
    lateinit var deal: Deal

    override fun builder(): FragmentToolbar? {
        return FragmentToolbar.Builder()
                .withId(R.id.toolbar)
                .withTitle(R.string.packages)
                .withNotification()
                .withBackButton()
                .withBackButtonCallback(object : IBackButtonCallback {
                    override fun onToolbarBackPressed(view: View) {
                        navigationController.popBackStack(activity)
                    }
                })
                .withNotificationCallback(object : INotificationCallback {
                    override fun onToolbarNotificationClicked(view: View) {
                        showNotificationDialog()
                    }
                })
                .build()
    }

    override fun getFragmentTAG(): FragmentTAG {
        return FragmentTAG.PACKAGE_DETAIL_FRAG
    }

    override fun getViewModel(): PackageDetailFragmentVM {
        packageDetailFragmentVM = ViewModelProviders.of(this@PackageDetailsFragment, appViewModelFactory)
                .get(PackageDetailFragmentVM::class.java)
        return packageDetailFragmentVM
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            //R.id.btn_details_and_order -> navigationController.launchPassengerCountFragment(activity, R.id.fl_dashboard_container)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            deal = arguments?.get(Constants.KEY.DEAL) as Deal
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_package_details, container, false) as ViewGroup
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isFragmentLoaded) {
            initInstances()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        showBottomTabs()
    }

    private fun initInstances() {
        showDealDetails()

        subscribeToLiveData()

        //navigationController.getDealDetailsRoutManager().launchFlightsDetailFragment(activity, R.id.fl_package_details_container)

        /*btn_details_and_order.setOnClickListener(this)*/
    }

    private fun showDealDetails() {

    }

    private fun subscribeToLiveData() {
        packageDetailFragmentVM.tabs.observe(this, Observer { tabs ->
            setUpTabLayout(tabs as ArrayList<Tab>?)
        })
    }

    private fun setUpTabLayout(tabs: ArrayList<Tab>?) {
        tab_layout.addTab(tab_layout.newTab().setText(tabs?.get(0)?.name))
        tab_layout.addTab(tab_layout.newTab().setText(tabs?.get(1)?.name))
        tab_layout.addTab(tab_layout.newTab().setText(tabs?.get(2)?.name))

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                setCurrentTabFragment(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun setCurrentTabFragment(tabPosition: Int) {
        /*when (tabPosition) {
           // 0 -> navigationController.getDealDetailsRoutManager().launchFlightsDetailFragment(activity, R.id.fl_package_details_container)
            1 -> navigationController.getDealDetailsRoutManager().launchPriceDetailFragment(activity, R.id.fl_package_details_container)
            2 -> navigationController.getDealDetailsRoutManager().launchHotelDetailsFragment(activity, R.id.fl_package_details_container)
        }*/
    }

}
