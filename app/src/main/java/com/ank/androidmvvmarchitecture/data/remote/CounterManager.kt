package com.ank.androidmvvmarchitecture.data.remote/*

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.ank.androidmvvmarchitecture.data.remote.api.BaseAPIService
import com.ank.androidmvvmarchitecture.data.remote.http.RestClient
import com.ank.androidmvvmarchitecture.utils.Constants
import com.ank.androidmvvmarchitecture.utils.LogUtils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

*/
/**
 * Created by CIS Dev 816 on 5/7/18.
 *//*

class CounterManager private constructor(var context: FragmentActivity?) {

    private val TAG = CounterManager::class.java.simpleName

    companion object {
        private var instance: CounterManager? = null

        var remainTimeToStop: Long? = 0

        fun getInstance(context: FragmentActivity?): CounterManager {
            if (instance == null)
                instance = CounterManager(context)

            return instance as CounterManager
        }
    }

    private var disposables: CompositeDisposable? = null
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()
    val coundown: MutableLiveData<Coundown> = MutableLiveData()
    val coundownToStop: MutableLiveData<Coundown> = MutableLiveData()

    init {
        disposables = CompositeDisposable()
    }

    fun fetchCounterToStart() {
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

                        val coundownList: List<Coundown> = Gson().fromJson(response.get(Constants.API.KEY.DATA).asJsonArray, object : TypeToken<List<Coundown>>() {}.type)
                        if (coundownList.isNotEmpty()) {
                            coundown.value = coundownList[0]
                        } else {
                            if (response.has(Constants.API.KEY.MESSAGE) && !response.get(Constants.API.KEY.MESSAGE).asString.isEmpty()) {
                                error.value = response.get(Constants.API.KEY.MESSAGE).asString
                            } else {
                                error.value = context?.getString(R.string.no_data_found)
                            }
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
            RestClient.createRetrofitService(BaseAPIService::class.java, context).getCounterText()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer))
    }

    fun fetchCounterToStop() {
        //isLoading.value = true

        val observer = object : DisposableObserver<JsonObject>() {

            override fun onComplete() {
                LogUtils.d(TAG, "onComplete")
                //isLoading.value = false
            }

            override fun onError(e: Throwable) {
                LogUtils.d(TAG, "onError")
                //isLoading.value = false
                //error.value = context?.getString(R.string.no_data_found)
            }

            override fun onNext(response: JsonObject) {
                LogUtils.d(TAG, "onNext")
                try {
                    //isLoading.value = false
                    if (response.get(Constants.API.KEY.STATUS).asBoolean && !response.get(Constants.API.KEY.DATA).isJsonNull) {

                        val coundownList: List<Coundown> = Gson().fromJson(response.get(Constants.API.KEY.DATA).asJsonArray, object : TypeToken<List<Coundown>>() {}.type)
                        if (coundownList.isNotEmpty()) {
                            coundownToStop.value = coundownList[0]
                            if (!coundownToStop.value?.RemainingTime.isNullOrEmpty()) {
                                CounterManager.remainTimeToStop = coundownToStop.value?.RemainingTime?.toLong()
                            }
                        } else {
                            if (response.has(Constants.API.KEY.MESSAGE) && !response.get(Constants.API.KEY.MESSAGE).asString.isEmpty()) {
                                //error.value = response.get(Constants.API.KEY.MESSAGE).asString
                            } else {
                                //error.value = context?.getString(R.string.no_data_found)
                            }
                        }
                    } else if (!response.get(Constants.API.KEY.STATUS).asBoolean) {
                        if (response.has(Constants.API.KEY.MESSAGE) && !response.get(Constants.API.KEY.MESSAGE).asString.isEmpty()) {
                            //error.value = response.get(Constants.API.KEY.MESSAGE).asString
                        } else {
                            //error.value = context?.getString(R.string.no_data_found)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    //isLoading.value = false
                    //error.value = context?.getString(R.string.no_data_found)
                }
            }
        }

        disposables?.add(
            RestClient.createRetrofitService(BaseAPIService::class.java, context).getCounterToStart()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer))
    }


}*/
