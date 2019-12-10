package androidmvvm.ui.deals_dashboard.tab_concerts

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import androidmvvm.androidmvvmApplication
import androidmvvm.R
import androidmvvm.data.model.Deal
import javax.inject.Inject

/**
 * Created by CIS Dev 816 on 18/5/18.
 */
class ConcertsFragmentVM @Inject constructor() : ViewModel() {

    val deals: MutableLiveData<List<Deal>>

    init {
        deals = MutableLiveData()
        loadDeals()
    }

    private fun loadDeals() {
        val dealTitle = arrayOf("The Wall Live", "The Mars Volta", "Glastonbury",
                "The Wall Live", "The Mars Volta", "Glastonbury",
                "The Wall Live", "The Mars Volta", "Glastonbury",
                "The Wall Live", "The Mars Volta", "Glastonbury",
                "The Wall Live", "The Mars Volta", "Glastonbury")

        val images = arrayOf(R.mipmap.concert, R.mipmap.concert, R.mipmap.concert,
                R.mipmap.concert, R.mipmap.concert, R.mipmap.concert,
                R.mipmap.concert, R.mipmap.concert, R.mipmap.concert,
                R.mipmap.concert, R.mipmap.concert, R.mipmap.concert,
                R.mipmap.concert, R.mipmap.concert, R.mipmap.concert)

        val type = arrayOf(androidmvvmApplication.context.resources.getString(R.string.tab_concerts), androidmvvmApplication.context.resources.getString(R.string.tab_concerts), androidmvvmApplication.context.resources.getString(R.string.tab_concerts),
                androidmvvmApplication.context.resources.getString(R.string.tab_concerts), androidmvvmApplication.context.resources.getString(R.string.tab_concerts), androidmvvmApplication.context.resources.getString(R.string.tab_concerts),
                androidmvvmApplication.context.resources.getString(R.string.tab_concerts), androidmvvmApplication.context.resources.getString(R.string.tab_concerts), androidmvvmApplication.context.resources.getString(R.string.tab_concerts),
                androidmvvmApplication.context.resources.getString(R.string.tab_concerts), androidmvvmApplication.context.resources.getString(R.string.tab_concerts), androidmvvmApplication.context.resources.getString(R.string.tab_concerts),
                androidmvvmApplication.context.resources.getString(R.string.tab_concerts), androidmvvmApplication.context.resources.getString(R.string.tab_concerts), androidmvvmApplication.context.resources.getString(R.string.tab_concerts))

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