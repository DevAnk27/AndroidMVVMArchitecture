package androidmvvm.ui.deals_dashboard.tab_flights

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import androidmvvm.R
import androidmvvm.data.model.Deal
import javax.inject.Inject

/**
 * Created by CIS Dev 816 on 10/4/18.
 */
class FlightsFragmentVM @Inject constructor(var context: Application) : ViewModel() {

    val deals: MutableLiveData<List<Deal>>

    init {
        deals = MutableLiveData()
        loadDeals()
    }

    private fun loadDeals() {
        val dealTitle = arrayOf("Las-Vegas", "Chicago", "New York",
                "Las-Vegas", "Chicago", "New York",
                "Las-Vegas", "Chicago", "New York",
                "Las-Vegas", "Chicago", "New York",
                "Las-Vegas", "Chicago", "New York")

        val images = arrayOf(R.mipmap.flights, R.mipmap.flights, R.mipmap.flights,
                R.mipmap.flights, R.mipmap.flights, R.mipmap.flights,
                R.mipmap.flights, R.mipmap.flights, R.mipmap.flights,
                R.mipmap.flights, R.mipmap.flights, R.mipmap.flights,
                R.mipmap.flights, R.mipmap.flights, R.mipmap.flights)

        val type = arrayOf(context.resources.getString(R.string.tab_flights), context.resources.getString(R.string.tab_flights), context.resources.getString(R.string.tab_flights),
                context.resources.getString(R.string.tab_flights), context.resources.getString(R.string.tab_flights), context.resources.getString(R.string.tab_flights),
                context.resources.getString(R.string.tab_flights), context.resources.getString(R.string.tab_flights), context.resources.getString(R.string.tab_flights),
                context.resources.getString(R.string.tab_flights), context.resources.getString(R.string.tab_flights), context.resources.getString(R.string.tab_flights),
                context.resources.getString(R.string.tab_flights), context.resources.getString(R.string.tab_flights), context.resources.getString(R.string.tab_flights))

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