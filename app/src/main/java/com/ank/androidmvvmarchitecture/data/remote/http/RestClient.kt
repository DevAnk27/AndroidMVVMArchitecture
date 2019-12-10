package com.ank.androidmvvmarchitecture.data.remote.http

import android.content.Context
import com.ank.androidmvvmarchitecture.BuildConfig
import com.ank.androidmvvmarchitecture.R
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by CIS Dev 816 on 9/2/18.
 */
class RestClient {

    companion object {

        private var BASE_API_URL = ""

        private var httpClient = OkHttpClient.Builder()
        private val logging = HttpLoggingInterceptor()


        fun <T> createRetrofitService(clazz: Class<T>, context: Context?): T {
            httpClient = OkHttpClient.Builder()
            configClient(context)
            addLoggingIfNeeded()
            //addAuthHeader()

            val gson = GsonBuilder()
                    .setPrettyPrinting()
                    .setLenient()
                    .create()

            val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build()

            return retrofit.create(clazz)
        }

        fun <T> createERetrofitService(clazz: Class<T>, context: Context?, authToken: String? = null): T {
            httpClient = OkHttpClient.Builder()
            configClient(context)
            addLoggingIfNeeded()
            addEAuthHeader(authToken)

            val gson = GsonBuilder()
                    .setPrettyPrinting()
                    .setLenient()
                    .create()

            val retrofit = Retrofit.Builder().baseUrl(BuildConfig.E_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build()

            return retrofit.create(clazz)
        }

        fun configClient(context: Context?) {
            httpClient.connectTimeout(10, TimeUnit.MINUTES)
            httpClient.readTimeout(10, TimeUnit.MINUTES)

            /*val cacheFile = File(context?.cacheDir, "okhttp.cache")
            cacheFile.mkdir()

            val cache = Cache(cacheFile, (10 * 1000 * 1000).toLong()) // 10mb cache
            httpClient.cache(cache)*/
        }

        fun addLoggingIfNeeded() {
            if (httpClient.interceptors().size == 0 && BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
                httpClient.interceptors().add(logging)
            }
        }

        fun addEAuthHeader(authToken: String?) {
            httpClient.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain?): Response? {
                    val original = chain?.request()

                    var requestBuilder: Request.Builder? = null
                    /*if (authToken == null *//*&& httpClient.interceptors().size == 2*//*) {
                        requestBuilder = original?.newBuilder()?.header("Content-Type", "application/json")
                                ?.header("NST", context.getString(R.string.jwt_token))
                                ?.header("DeviceId", Helper.getSerialNumber())
                    } else  {
                        requestBuilder = original?.newBuilder()?.header("Content-Type", "application/json")
                                ?.header("NST", context.getString(R.string.jwt_token))
                                ?.header("DeviceId", Helper.getSerialNumber())
                                ?.header("Token", authToken)
                    }*/

                    val request = requestBuilder?.build()
                    return chain?.proceed(request)
                }
            })
        }
    }


}