package androidmvvm.ui.deals_dashboard.tab_packages

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import androidmvvm.R
import androidmvvm.data.model.Deal
import androidmvvm.data.remote.extensions.toLiveData
import androidmvvm.data.remote.networking.Outcome
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by CIS Dev 816 on 7/4/18.
 */
class PackagesFragmentVM @Inject constructor(/*var dealsRepository: DealsRepository,private val compositeDisposable: CompositeDisposable,*/
        var context: Application) : ViewModel() {

    val deals: MutableLiveData<List<Deal>>

    lateinit var compositeDisposable: CompositeDisposable
    lateinit var dealsRepository: DealsRepository

    val dealOutcome: LiveData<Outcome<List<Deal>>> by lazy {
        //Convert publish subject to livedata
        dealsRepository.dealFetchOutcome.toLiveData(compositeDisposable)
    }

    init {
        deals = MutableLiveData()

        compositeDisposable = CompositeDisposable()
        dealsRepository = DealsRepository(compositeDisposable)

        loadDeals()
    }

    private fun loadDeals() {
        dealsRepository.getDeals()

        val dealTitle = arrayOf("Las-Vegas", "Chicago", "New York",
                "Las-Vegas", "Chicago", "New York",
                "Las-Vegas", "Chicago", "New York",
                "Las-Vegas", "Chicago", "New York",
                "Las-Vegas", "Chicago", "New York")

        val images = arrayOf(R.mipmap.packages, R.mipmap.packages, R.mipmap.packages,
                R.mipmap.packages, R.mipmap.packages, R.mipmap.packages,
                R.mipmap.packages, R.mipmap.packages, R.mipmap.packages,
                R.mipmap.packages, R.mipmap.packages, R.mipmap.packages,
                R.mipmap.packages, R.mipmap.packages, R.mipmap.packages)

        val type = arrayOf(context.resources.getString(R.string.tab_packages), context.resources.getString(R.string.tab_packages), context.resources.getString(R.string.tab_packages),
                context.resources.getString(R.string.tab_packages), context.resources.getString(R.string.tab_packages), context.resources.getString(R.string.tab_packages),
                context.resources.getString(R.string.tab_packages), context.resources.getString(R.string.tab_packages), context.resources.getString(R.string.tab_packages),
                context.resources.getString(R.string.tab_packages), context.resources.getString(R.string.tab_packages), context.resources.getString(R.string.tab_packages),
                context.resources.getString(R.string.tab_packages), context.resources.getString(R.string.tab_packages), context.resources.getString(R.string.tab_packages))

        val durations = arrayOf("3 לילות", "3 לילות", "3 לילות",
                "3 לילות", "3 לילות", "3 לילות",
                "3 לילות", "3 לילות", "3 לילות",
                "3 לילות", "3 לילות", "3 לילות",
                "3 לילות", "3 לילות", "3 לילות")

        val priceList = arrayOf("$512", "$720", "$234",
                "$678", "$233", "$599",
                "$566", "$899", "$799",
                "$899", "$355", "$999",
                "$399", "$799", "$789")

        val dealList = mutableListOf<Deal>()

        for (i in 0..14) {
            val deal = Deal(i.toString(), dealTitle[i], images[i], durations[i], priceList[i])
            deal.dealType = type[i]

            dealList.add(deal)
        }

        deals.value = dealList
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}