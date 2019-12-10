package androidmvvm.ui.deals_dashboard.tab_concerts


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
import kotlinx.android.synthetic.main.fragment_concerts.*
import kotlinx.android.synthetic.main.layout_error.*
import java.util.*
import javax.inject.Inject


class ConcertsFragment : BaseFragment<ConcertsFragmentVM>(), DealsRVAdapter.DealClickListener {

    companion object {

        @JvmStatic
        fun newInstance() =
                ConcertsFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    @Inject
    lateinit var appViewModelFactory: AppViewModelFactory
    lateinit var concertsFragmentVM: ConcertsFragmentVM
    lateinit var dealsRVAdapter: DealsRVAdapter
    lateinit var dealsManager: DealsManager
    private var mConcerts: MutableList<Deal> = mutableListOf<Deal>()

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
        return FragmentTAG.CONCERTS_FRAG
    }

    override fun getViewModel(): ConcertsFragmentVM {
        concertsFragmentVM = ViewModelProviders.of(this@ConcertsFragment, appViewModelFactory)
                .get(ConcertsFragmentVM::class.java)
        return concertsFragmentVM
    }

    override fun onDealClicked(deal: Deal) {
        if (deal.IsActive != null && deal.IsActive.equals("true")) {
            var bundle = Bundle()
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
            root = inflater.inflate(R.layout.fragment_concerts, container, false) as ViewGroup
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

        setUpConcertsRVAAdapter()

        subscribeToLiveData()

        if (dealsManager.isDelasEmpty() && isNetworkConnected()) {
            //dealsManager.fetchDeals()
            ll_error.visibility = View.VISIBLE
        } else if (isNetworkConnected()) {
            dealsManager.getConcert()
        } else if (!isNetworkConnected()) {
            helper?.showShortToast(getString(R.string.no_internet_connection))
            ll_error.visibility = View.VISIBLE
        }
    }

    private fun subscribeToLiveData() {
        concertsFragmentVM.deals.observe(this, Observer { deals ->
            //setUpConcertsRVAAdapter(deals as ArrayList<Deal>)
        })

        dealsManager.concerts.observe(this, Observer { concerts ->
            mConcerts.clear()
            mConcerts.addAll(concerts as ArrayList)
            dealsRVAdapter.notifyDataSetChanged()
            if (mConcerts.size <= 0) {
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

        dealsManager.errConcerts.observe(this, Observer { error ->
            ll_error.visibility = View.VISIBLE
        })
    }

    private fun setUpConcertsRVAAdapter() {
        rv_concerts.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        dealsRVAdapter = DealsRVAdapter(activity, mConcerts as ArrayList<Deal>)
        dealsRVAdapter.setDealClickListener(this)
        rv_concerts.adapter = dealsRVAdapter
    }

}
