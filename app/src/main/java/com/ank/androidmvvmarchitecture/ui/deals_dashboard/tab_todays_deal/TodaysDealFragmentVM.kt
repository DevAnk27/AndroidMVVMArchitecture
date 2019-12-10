package androidmvvm.ui.deals_dashboard.tab_todays_deal

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import androidmvvm.R
import androidmvvm.data.model.Deal
import androidmvvm.data.remote.api.BaseAPIService
import androidmvvm.data.remote.http.RestClient
import androidmvvm.utils.Constants
import androidmvvm.utils.LogUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by CIS Dev 816 on 10/4/18.
 */
class TodaysDealFragmentVM @Inject constructor(var context: Application) : ViewModel() {

    private val TAG = "TDFragmentVM"

    private var compositeDisposable = CompositeDisposable()
    private var deals: MutableList<Deal> = mutableListOf<Deal>()
    val todaysDeals: MutableLiveData<List<Deal>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val error: MutableLiveData<String> = MutableLiveData<String>()

    override fun onCleared() {
        compositeDisposable.dispose()
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
                error.value = context.getString(R.string.no_data_found)
            }

            override fun onNext(response: JsonObject) {
                LogUtils.d(TAG, "onNext")
                try {
                    isLoading.value = false
                    if (response.get(Constants.API.KEY.STATUS).asBoolean && !response.get(Constants.API.KEY.DATA).isJsonNull) {
                        val dealsList: List<Deal> = Gson().fromJson(response.get(Constants.API.KEY.DATA).asJsonArray, object : TypeToken<List<Deal>>() {}.type)
                        if (dealsList.size >= 0) {
                            deals.clear()

                            var isOrderNumberNull = false
                            for (deal in dealsList) {
                                if (deal.Ordernumber == null) {
                                    isOrderNumberNull = true
                                    break
                                }
                            }

                            if (isOrderNumberNull) {
                                Collections.sort(dealsList, object : Comparator<Deal> {
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
                                Collections.sort(dealsList, object : Comparator<Deal> {
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

                            // Updated on 10-10-18
                            /*Collections.sort(dealsList, object : Comparator<Deal> {
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
                            })*/

                            deals.addAll(dealsList)
                            todaysDeals.value = dealsList
                        } else {
                            error.value = context.getString(R.string.no_data_found)
                        }
                    } else if (!response.get(Constants.API.KEY.STATUS).asBoolean) {
                        if (response.has(Constants.API.KEY.MESSAGE) && !response.get(Constants.API.KEY.MESSAGE).asString.isEmpty()) {
                            error.value = response.get(Constants.API.KEY.MESSAGE).asString
                        } else {
                            error.value = context.getString(R.string.no_data_found)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    isLoading.value = false
                    error.value = context.getString(R.string.no_data_found)
                }
            }
        }

        compositeDisposable.add(RestClient.createRetrofitService(BaseAPIService::class.java, context).getOnlyDeals()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer))
    }
}