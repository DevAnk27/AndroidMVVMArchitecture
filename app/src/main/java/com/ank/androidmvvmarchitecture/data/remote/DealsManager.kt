package com.ank.androidmvvmarchitecture.data.remote/*

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.ank.androidmvvmarchitecture.data.remote.api.BaseAPIService
import com.ank.androidmvvmarchitecture.data.remote.http.RestClient
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

*/
/**
 * Created by CIS Dev 816 on 6/6/18.
 *//*

class DealsManager private constructor(var context: FragmentActivity?) {

    private val TAG = DealsManager::class.java.simpleName

    companion object {
        private var instance: DealsManager? = null

        fun getInstance(context: FragmentActivity?): DealsManager {
            if (instance == null)
                instance = DealsManager(context)

            return instance as DealsManager
        }
    }

    private var disposables: CompositeDisposable? = null
    private var deals: MutableList<Deal> = mutableListOf()

    val todaysDeals: MutableLiveData<List<Deal>> = MutableLiveData()
    val packages: MutableLiveData<List<Deal>> = MutableLiveData()
    val flights: MutableLiveData<List<Deal>> = MutableLiveData()
    val tours: MutableLiveData<List<Deal>> = MutableLiveData()
    val passovers: MutableLiveData<List<Deal>> = MutableLiveData()
    val sports: MutableLiveData<List<Deal>> = MutableLiveData()
    val concerts: MutableLiveData<List<Deal>> = MutableLiveData()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()
    val errPackages: MutableLiveData<String> = MutableLiveData()
    val errFlights: MutableLiveData<String> = MutableLiveData()
    val errTours: MutableLiveData<String> = MutableLiveData()
    val errPassovers: MutableLiveData<String> = MutableLiveData()
    val errSports: MutableLiveData<String> = MutableLiveData()
    val errConcerts: MutableLiveData<String> = MutableLiveData()

    init {
        disposables = CompositeDisposable()
    }

    fun isDelasEmpty(): Boolean {
        return deals.size <= 0
    }

    fun fetchDeals() {
        isLoading.value = true

        val observer = object : DisposableObserver<JsonObject>() {

            override fun onComplete() {
                LogUtils.d(TAG, "onComplete")
                isLoading.value = false
            }

            override fun onError(e: Throwable) {
                LogUtils.d(TAG, "onError")
                isLoading.value = false
                error.value = context?.getString(R.string.no_data_found)
            }

            override fun onNext(response: JsonObject) {
                LogUtils.d(TAG, "onNext")
                try {
                    isLoading.value = false
                    if (response.get(Constants.API.KEY.STATUS).asBoolean && !response.get(Constants.API.KEY.DATA).isJsonNull) {
                        val dealsList: List<Deal> = Gson().fromJson(response.get(Constants.API.KEY.DATA).asJsonArray, object : TypeToken<List<Deal>>() {}.type)
                        if (dealsList.size >= 0) {
                            deals.clear()
                            deals.addAll(dealsList)
                            todaysDeals.value = dealsList
                        } else {
                            error.value = context?.getString(R.string.no_data_found)
                        }
                    } else if (!response.get(Constants.API.KEY.STATUS).asBoolean) {
                        if (response.has(Constants.API.KEY.MESSAGE) && !response.get(Constants.API.KEY.MESSAGE).asString.isEmpty()) {
                            error.value = response.get(Constants.API.KEY.MESSAGE).asString
                        } else {
                            error.value = context?.getString(R.string.no_data_found)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    isLoading.value = false
                    error.value = context?.getString(R.string.no_data_found)
                }
            }
        }

        disposables?.add(
            RestClient.createRetrofitService(BaseAPIService::class.java, context).getDeals()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer))
    }

    fun getPackages() {
        if (deals.size >= 0) {
            isLoading.value = true

            var isOrderNumberNull = false
            val tempDeals: MutableList<Deal> = mutableListOf()
            for (deal in deals) {
                if (deal.Category_Id.equals(Constants.CATEGORY.PACKAGE)) {
                    tempDeals.add(deal)

                    if (deal.Ordernumber == null) {
                        isOrderNumberNull = true
                    }
                }
            }

            if (isOrderNumberNull) {
                Collections.sort(tempDeals, object : Comparator<Deal> {
                    override fun compare(abc1: Deal, abc2: Deal): Int {
                        if(abc1.PackagePrice.isNullOrEmpty() || abc2.PackagePrice.isNullOrEmpty()) {
                            return 0
                        }

                        val a1Price = abc1.PackagePrice?.toInt()
                        val a2Price = abc2.PackagePrice?.toInt()
                        if (a1Price!! < a2Price!!) {
                            return -1
                        } else if (a1Price == a2Price) {
                            return 0
                        } else {
                            return 1
                        }
                    }
                })
            } else {
                Collections.sort(tempDeals, object : Comparator<Deal> {
                    override fun compare(abc1: Deal, abc2: Deal): Int {
                        if (abc1.Ordernumber == null || abc2.Ordernumber == null) {
                            return 0
                        }

                        val a1OrderNumber = abc1.Ordernumber?.toInt()
                        val a2OrderNumber = abc2.Ordernumber?.toInt()
                        if (a1OrderNumber!! < a2OrderNumber!!) {
                            return -1
                        } else if (a1OrderNumber == a2OrderNumber) {
                            return 0
                        } else {
                            return 1
                        }
                    }
                })
            }

            isLoading.value = false
            packages.value = tempDeals

            if (tempDeals.size <= 0) {
                //error.value = context?.getString(R.string.no_data_found)
            }
        } else {
            errPackages.value = context?.getString(R.string.no_data_found)
        }
    }

    fun getFlights() {
        if (deals.size >= 0) {
            isLoading.value = true

            var isOrderNumberNull = false
            val tempDeals: MutableList<Deal> = mutableListOf()
            for (deal in deals) {
                if (deal.Category_Id.equals(Constants.CATEGORY.FLIGHT)) {
                    tempDeals.add(deal)

                    if (deal.Ordernumber == null) {
                        isOrderNumberNull = true
                    }
                }
            }

            if (isOrderNumberNull) {
                Collections.sort(tempDeals, object : Comparator<Deal> {
                    override fun compare(abc1: Deal, abc2: Deal): Int {
                        if(abc1.PackagePrice.isNullOrEmpty() || abc2.PackagePrice.isNullOrEmpty()) {
                            return 0
                        }

                        val a1Price = abc1.PackagePrice?.toInt()
                        val a2Price = abc2.PackagePrice?.toInt()
                        if (a1Price!! < a2Price!!) {
                            return -1
                        } else if (a1Price == a2Price) {
                            return 0
                        } else {
                            return 1
                        }
                    }
                })
            } else {
                Collections.sort(tempDeals, object : Comparator<Deal> {
                    override fun compare(abc1: Deal, abc2: Deal): Int {
                        if (abc1.Ordernumber == null || abc2.Ordernumber == null) {
                            return 0
                        }

                        val a1OrderNumber = abc1.Ordernumber?.toInt()
                        val a2OrderNumber = abc2.Ordernumber?.toInt()
                        if (a1OrderNumber!! < a2OrderNumber!!) {
                            return -1
                        } else if (a1OrderNumber == a2OrderNumber) {
                            return 0
                        } else {
                            return 1
                        }
                    }
                })
            }

            isLoading.value = false
            flights.value = tempDeals

            if (tempDeals.size <= 0) {
                //error.value = context?.getString(R.string.no_data_found)
            }
        } else {
            errFlights.value = context?.getString(R.string.no_data_found)
        }
    }

    fun getTours() {
        if (deals.size >= 0) {
            isLoading.value = true

            var isOrderNumberNull = false
            val tempDeals: MutableList<Deal> = mutableListOf()
            for (deal in deals) {
                if (deal.Category_Id.equals(Constants.CATEGORY.TOUR)) {
                    tempDeals.add(deal)

                    if (deal.Ordernumber == null) {
                        isOrderNumberNull = true
                    }
                }
            }

            if (isOrderNumberNull) {
                Collections.sort(tempDeals, object : Comparator<Deal> {
                    override fun compare(abc1: Deal, abc2: Deal): Int {
                        if(abc1.PackagePrice.isNullOrEmpty() || abc2.PackagePrice.isNullOrEmpty()) {
                            return 0
                        }

                        val a1Price = abc1.PackagePrice?.toInt()
                        val a2Price = abc2.PackagePrice?.toInt()
                        if (a1Price!! < a2Price!!) {
                            return -1
                        } else if (a1Price == a2Price) {
                            return 0
                        } else {
                            return 1
                        }
                    }
                })
            } else {
                Collections.sort(tempDeals, object : Comparator<Deal> {
                    override fun compare(abc1: Deal, abc2: Deal): Int {
                        if (abc1.Ordernumber == null || abc2.Ordernumber == null) {
                            return 0
                        }

                        val a1OrderNumber = abc1.Ordernumber?.toInt()
                        val a2OrderNumber = abc2.Ordernumber?.toInt()
                        if (a1OrderNumber!! < a2OrderNumber!!) {
                            return -1
                        } else if (a1OrderNumber == a2OrderNumber) {
                            return 0
                        } else {
                            return 1
                        }
                    }
                })
            }

            isLoading.value = false
            tours.value = tempDeals

            if (tempDeals.size <= 0) {
                //error.value = context?.getString(R.string.no_data_found)`
            }
        } else {
            errTours.value = context?.getString(R.string.no_data_found)
        }
    }

    fun getPassover() {
        if (deals.size >= 0) {
            isLoading.value = true

            var isOrderNumberNull = false
            val tempDeals: MutableList<Deal> = mutableListOf()
            for (deal in deals) {
                if (deal.Category_Id.equals(Constants.CATEGORY.PASSOVER)) {
                    tempDeals.add(deal)

                    if (deal.Ordernumber == null) {
                        isOrderNumberNull = true
                    }
                }
            }

            if (isOrderNumberNull) {
                Collections.sort(tempDeals, object : Comparator<Deal> {
                    override fun compare(abc1: Deal, abc2: Deal): Int {
                        if(abc1.PackagePrice.isNullOrEmpty() || abc2.PackagePrice.isNullOrEmpty()) {
                            return 0
                        }

                        val a1Price = abc1.PackagePrice?.toInt()
                        val a2Price = abc2.PackagePrice?.toInt()
                        if (a1Price!! < a2Price!!) {
                            return -1
                        } else if (a1Price == a2Price) {
                            return 0
                        } else {
                            return 1
                        }
                    }
                })
            } else {
                Collections.sort(tempDeals, object : Comparator<Deal> {
                    override fun compare(abc1: Deal, abc2: Deal): Int {
                        if (abc1.Ordernumber == null || abc2.Ordernumber == null) {
                            return 0
                        }

                        val a1OrderNumber = abc1.Ordernumber?.toInt()
                        val a2OrderNumber = abc2.Ordernumber?.toInt()
                        if (a1OrderNumber!! < a2OrderNumber!!) {
                            return -1
                        } else if (a1OrderNumber == a2OrderNumber) {
                            return 0
                        } else {
                            return 1
                        }
                    }
                })
            }

            isLoading.value = false
            passovers.value = tempDeals

            if (tempDeals.size <= 0) {
                //error.value = context?.getString(R.string.no_data_found)
            }
        } else {
            errPassovers.value = context?.getString(R.string.no_data_found)
        }
    }

    fun getSports() {
        if (deals.size >= 0) {
            isLoading.value = true

            var isOrderNumberNull = false
            val tempDeals: MutableList<Deal> = mutableListOf()
            for (deal in deals) {
                if (deal.Category_Id.equals(Constants.CATEGORY.SPORTS)) {
                    tempDeals.add(deal)

                    if (deal.Ordernumber == null) {
                        isOrderNumberNull = true
                    }
                }
            }

            if (isOrderNumberNull) {
                Collections.sort(tempDeals, object : Comparator<Deal> {
                    override fun compare(abc1: Deal, abc2: Deal): Int {
                        if(abc1.PackagePrice.isNullOrEmpty() || abc2.PackagePrice.isNullOrEmpty()) {
                            return 0
                        }

                        val a1Price = abc1.PackagePrice?.toInt()
                        val a2Price = abc2.PackagePrice?.toInt()
                        if (a1Price!! < a2Price!!) {
                            return -1
                        } else if (a1Price == a2Price) {
                            return 0
                        } else {
                            return 1
                        }
                    }
                })
            } else {
                Collections.sort(tempDeals, object : Comparator<Deal> {
                    override fun compare(abc1: Deal, abc2: Deal): Int {
                        if (abc1.Ordernumber == null || abc2.Ordernumber == null) {
                            return 0
                        }

                        val a1OrderNumber = abc1.Ordernumber?.toInt()
                        val a2OrderNumber = abc2.Ordernumber?.toInt()
                        if (a1OrderNumber!! < a2OrderNumber!!) {
                            return -1
                        } else if (a1OrderNumber == a2OrderNumber) {
                            return 0
                        } else {
                            return 1
                        }
                    }
                })
            }

            isLoading.value = false
            sports.value = tempDeals

            if (tempDeals.size <= 0) {
                //error.value = context?.getString(R.string.no_data_found)
            }
        } else {
            errSports.value = context?.getString(R.string.no_data_found)
        }
    }

    fun getConcert() {
        if (deals.size >= 0) {
            isLoading.value = true

            var isOrderNumberNull = false
            val tempDeals: MutableList<Deal> = mutableListOf()
            for (deal in deals) {
                if (deal.Category_Id.equals(Constants.CATEGORY.CONCERT)) {
                    tempDeals.add(deal)

                    if (deal.Ordernumber == null) {
                        isOrderNumberNull = true
                    }
                }
            }

            if (isOrderNumberNull) {
                Collections.sort(tempDeals, object : Comparator<Deal> {
                    override fun compare(abc1: Deal, abc2: Deal): Int {
                        if (abc1.PackagePrice == null || abc2.PackagePrice == null) {
                            return 0
                        }

                        val a1Price = abc1.PackagePrice?.toInt()
                        val a2Price = abc2.PackagePrice?.toInt()
                        if (a1Price!! < a2Price!!) {
                            return -1
                        } else if (a1Price == a2Price) {
                            return 0
                        } else {
                            return 1
                        }
                    }
                })
            } else {
                Collections.sort(tempDeals, object : Comparator<Deal> {
                    override fun compare(abc1: Deal, abc2: Deal): Int {
                        if (abc1.Ordernumber == null || abc2.Ordernumber == null) {
                            return 0
                        }

                        val a1OrderNumber = abc1.Ordernumber?.toInt()
                        val a2OrderNumber = abc2.Ordernumber?.toInt()
                        if (a1OrderNumber!! < a2OrderNumber!!) {
                            return -1
                        } else if (a1OrderNumber == a2OrderNumber) {
                            return 0
                        } else {
                            return 1
                        }
                    }
                })
            }

            isLoading.value = false
            concerts.value = tempDeals

            if (tempDeals.size <= 0) {
                //error.value = context?.getString(R.string.no_data_found)
            }
        } else {
            errConcerts.value = context?.getString(R.string.no_data_found)
        }
    }
}*/
