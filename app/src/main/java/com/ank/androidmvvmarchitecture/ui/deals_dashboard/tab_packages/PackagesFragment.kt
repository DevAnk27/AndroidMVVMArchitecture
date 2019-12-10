package androidmvvm.ui.deals_dashboard.tab_packages


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidmvvm.R
import androidmvvm.data.model.Deal
import androidmvvm.data.remote.CounterManager
import androidmvvm.data.remote.DealsManager
import androidmvvm.di.module.AppViewModelFactory
import androidmvvm.ui.base.BaseFragment
import androidmvvm.ui.deals_dashboard.adapter.DealsRVAdapter
import androidmvvm.utils.Constants
import androidmvvm.utils.toolbar.FragmentToolbar
import androidmvvm.utils.toolbar.callbacks.IFilterCallback
import androidmvvm.utils.toolbar.callbacks.INotificationCallback
import kotlinx.android.synthetic.main.fragment_packages.*
import kotlinx.android.synthetic.main.layout_error.*
import java.util.*
import javax.inject.Inject


class PackagesFragment : BaseFragment<PackagesFragmentVM>(), DealsRVAdapter.DealClickListener {

    companion object {
        @JvmStatic
        fun newInstance() =
                PackagesFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    @Inject
    lateinit var appViewModelFactory: AppViewModelFactory
    lateinit var packagesFragmentVM: PackagesFragmentVM
    lateinit var dealsRVAdapter: DealsRVAdapter
    lateinit var dealsManager: DealsManager
    private var mPackages: MutableList<Deal> = mutableListOf<Deal>()

    override fun builder(): FragmentToolbar? {
        return FragmentToolbar.Builder()
                .withId(R.id.toolbar)
                .withTitle(R.string.packages)
                .withNotification()
                .withFilter()
                .withRemainingTime(CounterManager.remainTimeToStop)
                .withContext(activity as Context)
                .withNotificationCallback(object : INotificationCallback {
                    override fun onToolbarNotificationClicked(view: View) {
                        showNotificationDialog()
                    }
                })
                .withFilterCallback(object : IFilterCallback {
                    override fun onToolbarFilterClicked(view: View) {
                        navigationController.launchFilterFragment(activity, R.id.fl_dashboard_container)
                    }
                })
                .build()
    }

    override fun getFragmentTAG(): FragmentTAG {
        return FragmentTAG.PACKAGES_FRAG
    }

    override fun getViewModel(): PackagesFragmentVM {
        packagesFragmentVM = ViewModelProviders.of(this@PackagesFragment, appViewModelFactory)
                .get(PackagesFragmentVM::class.java)
        return packagesFragmentVM
    }

    override fun onDealClicked(deal: Deal) {
        if (deal.IsActive != null && deal.IsActive.equals("true")) {
            val bundle = Bundle()
            bundle.putParcelable(Constants.KEY.DEAL, deal)
            navigationController.launchDealDetailsFragment(activity, R.id.fl_dashboard_container, bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_packages, container, false) as ViewGroup
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
        dealsManager = DealsManager.getInstance(activity)

        setUpPackageAdapter()

        subscribeToLiveData()

        if (dealsManager.isDelasEmpty() && isNetworkConnected()) {
            //dealsManager.fetchDeals()
            ll_error.visibility = View.VISIBLE
        } else if (isNetworkConnected()) {
            dealsManager.getPackages()
        } else if (!isNetworkConnected()) {
            helper?.showShortToast(getString(R.string.no_internet_connection))
            ll_error.visibility = View.VISIBLE
        }
    }

    private fun subscribeToLiveData() {
        packagesFragmentVM.deals.observe(this, Observer { deals ->
            //setUpPackageAdapter(deals as ArrayList<Deal>)
        })

        dealsManager.packages.observe(this, Observer { packages ->
            mPackages.clear()
            mPackages.addAll(packages as ArrayList)
            dealsRVAdapter.notifyDataSetChanged()
            if (mPackages.size <= 0) {
                ll_error.visibility = View.VISIBLE
            } else {
                ll_error.visibility = View.GONE
            }
        })

        dealsManager.isLoading.observe(this, Observer { isLoading ->
            if (isLoading!!) {
                pb_deals.visibility = View.VISIBLE
                ll_error.visibility = View.GONE
            } else {
                pb_deals.visibility = View.GONE
            }
        })

        dealsManager.errPackages.observe(this, Observer { error ->
            ll_error.visibility = View.VISIBLE
        })
    }

    private fun setUpPackageAdapter() {
        rv_packages.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false))
        dealsRVAdapter = DealsRVAdapter(activity, mPackages as ArrayList<Deal>)
        dealsRVAdapter.setDealClickListener(this)
        rv_packages.adapter = dealsRVAdapter
    }
}
