package androidmvvm.ui.deals_dashboard.tab_todays_deal

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
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_todays_deal.*
import kotlinx.android.synthetic.main.layout_error.*
import javax.inject.Inject

class TodaysDealFragment : BaseFragment<TodaysDealFragmentVM>(), DealsRVAdapter.DealClickListener {

    private val TAG = TodaysDealFragment::class.java.simpleName

    companion object {

        @JvmStatic
        fun newInstance() =
                TodaysDealFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    @Inject
    lateinit var appViewModelFactory: AppViewModelFactory
    lateinit var todaysDealFragmentVM: TodaysDealFragmentVM
    private var mTodaysDeals: MutableList<Deal> = mutableListOf<Deal>()
    lateinit var dealsRVAdapter: DealsRVAdapter
    var disposables: CompositeDisposable? = null
    lateinit var dealsManager: DealsManager
    lateinit var counterManager: CounterManager
    var isLoading = false

    override fun builder(): FragmentToolbar? {
        return FragmentToolbar.Builder()
                .withId(R.id.toolbar)
                .withTitle(R.string.todays_deal)
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
        return FragmentTAG.TODAYS_DEAL_FRAG
    }

    override fun getViewModel(): TodaysDealFragmentVM {
        todaysDealFragmentVM = ViewModelProviders.of(this@TodaysDealFragment, appViewModelFactory)
                .get(TodaysDealFragmentVM::class.java)
        return todaysDealFragmentVM
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
            root = inflater.inflate(R.layout.fragment_todays_deal, container, false) as ViewGroup
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isFragmentLoaded) {
            initInstances()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initInstances() {
        dealsManager = DealsManager.getInstance(activity)
        counterManager = CounterManager.getInstance(activity)

        setUpTodaysDealsAdapter()
        //Temp subscribeToLiveData()
        disposables = CompositeDisposable()

        // Temp
        /*if (dealsManager.isDelasEmpty() && isNetworkConnected()) {
            ll_error.visibility = View.GONE
            dealsManager.fetchDeals()
        } else if (!isNetworkConnected()) {
            ll_error.visibility = View.VISIBLE
            helper?.showShortToast(getString(R.string.no_internet_connection))
        }*/
    }

    override fun onResume() {
        super.onResume()

        showBottomTabs()

        subscribeToLiveData()

        if (/*dealsManager.isDelasEmpty() && */isNetworkConnected()) {
            ll_error.visibility = View.GONE
            todaysDealFragmentVM.fetchDeals()
            dealsManager.fetchDeals()
            counterManager.fetchCounterToStop()
        } else if (!isNetworkConnected()) {
            ll_error.visibility = View.VISIBLE
            helper?.showShortToast(getString(R.string.no_internet_connection))
        }
    }

    private fun subscribeToLiveData() {
        todaysDealFragmentVM.todaysDeals.observe(this, Observer { deals ->
            mTodaysDeals.clear()
            mTodaysDeals.addAll(deals as ArrayList)
            dealsRVAdapter.notifyDataSetChanged()
            if (mTodaysDeals.size <= 0) {
                ll_error.visibility = View.VISIBLE
            } else {
                ll_error.visibility = View.GONE
            }
        })

        todaysDealFragmentVM.isLoading.observe(this, Observer { isLoading ->
            if (isLoading!! && mTodaysDeals.size <= 0) {
                this.isLoading = true
                pb_todays_deal.visibility = View.VISIBLE
                ll_error.visibility = View.GONE
            } else {
                this.isLoading = false
                pb_todays_deal.visibility = View.GONE
            }
        })

        todaysDealFragmentVM.error.observe(this, Observer { error ->
            if (isLoading) {
                ll_error.visibility = View.GONE
            } else {
                ll_error.visibility = View.VISIBLE
            }
        })
    }

    private fun setUpTodaysDealsAdapter() {
        rv_todays_deal.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        dealsRVAdapter = DealsRVAdapter(activity, mTodaysDeals as ArrayList<Deal>)
        dealsRVAdapter.setDealClickListener(this)
        rv_todays_deal.adapter = dealsRVAdapter
    }
}
