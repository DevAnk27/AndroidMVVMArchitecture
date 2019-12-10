package androidmvvm.ui.deals_dashboard.tab_sports_packages

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import androidmvvm.androidmvvmApplication.Companion.context
import androidmvvm.R
import androidmvvm.data.model.Deal
import javax.inject.Inject

/**
 * Created by CIS Dev 816 on 18/5/18.
 */
class SportsPackagesFragmentVM @Inject constructor() : ViewModel() {

    val deals: MutableLiveData<List<Deal>>

    init {
        deals = MutableLiveData()
        loadDeals()
    }

    private fun loadDeals() {
        val dealTitle = arrayOf("French League Sport", "FIFA Beach Soccer", "IFAF World Championship",
                "French League Sport", "FIFA Beach Soccer", "IFAF World Championship",
                "French League Sport", "FIFA Beach Soccer", "IFAF World Championship",
                "French League Sport", "FIFA Beach Soccer", "IFAF World Championship",
                "French League Sport", "FIFA Beach Soccer", "IFAF World Championship")

        val images = arrayOf(R.mipmap.sport, R.mipmap.sport, R.mipmap.sport,
                R.mipmap.sport, R.mipmap.sport, R.mipmap.sport,
                R.mipmap.sport, R.mipmap.sport, R.mipmap.sport,
                R.mipmap.sport, R.mipmap.sport, R.mipmap.sport,
                R.mipmap.sport, R.mipmap.sport, R.mipmap.sport)

        val type = arrayOf(context.resources.getString(R.string.tab_sports), context.resources.getString(R.string.tab_sports), context.resources.getString(R.string.tab_sports),
                context.resources.getString(R.string.tab_sports), context.resources.getString(R.string.tab_sports), context.resources.getString(R.string.tab_sports),
                context.resources.getString(R.string.tab_sports), context.resources.getString(R.string.tab_sports), context.resources.getString(R.string.tab_sports),
                context.resources.getString(R.string.tab_sports), context.resources.getString(R.string.tab_sports), context.resources.getString(R.string.tab_sports),
                context.resources.getString(R.string.tab_sports), context.resources.getString(R.string.tab_sports), context.resources.getString(R.string.tab_sports))

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
}