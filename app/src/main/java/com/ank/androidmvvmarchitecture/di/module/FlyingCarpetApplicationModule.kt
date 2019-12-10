package androidmvvm.di.module

import android.app.Application
import android.content.Context
import com.ank.androidmvvmarchitecture.BuildConfig
import com.ank.androidmvvmarchitecture.data.AppDataManager
import com.ank.androidmvvmarchitecture.data.DataManager
import com.ank.androidmvvmarchitecture.data.local.pref.AppPreferenceManager
import com.ank.androidmvvmarchitecture.data.local.pref.PreferenceSource
import com.ank.androidmvvmarchitecture.data.remote.api.BaseAPIService
import com.ank.androidmvvmarchitecture.data.remote.http.RemoteDataManager
import com.ank.androidmvvmarchitecture.data.remote.http.RemoteSource
import com.ank.androidmvvmarchitecture.di.module.ActivityViewModelModule
import com.ank.androidmvvmarchitecture.utils.Constants.PREF_NAME
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Created by CIS Dev 816 on 30/3/18.
 */
@Module(includes = [ActivityViewModelModule::class])
class androidmvvmApplicationModule {

    @Provides
    @Singleton
    internal fun bindContext(application: Application): Context = application

    @Provides
    @Singleton
    internal fun getPreferenceSource(appPreferenceManager: AppPreferenceManager):
            PreferenceSource = appPreferenceManager

    @Provides
    @Singleton
    internal fun getRemoteSource(remoteDataManager: RemoteDataManager): RemoteSource = remoteDataManager

    @Provides
    @Singleton
    internal fun provideAppDataManger(appDataManager: AppDataManager): DataManager = appDataManager

    @Provides
    @Singleton
    internal fun getAuthRouteManager(authRouteManager: AuthRouteManager): AuthController = authRouteManager

    @Provides
    @Singleton
    internal fun getPackageDetailsRouteManager(dealDetailsRoutManager: DealDetailsRoutManager): DealDetailsController = dealDetailsRoutManager


    @Provides
    @Singleton
    internal fun getDashboardRouteManager(dashboardRoutManager: DashboardRouteManager,
                                          dealDetailsRoutManager: DealDetailsRoutManager): DashboardController = dashboardRoutManager

    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024L
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        var logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.interceptors().add(logging)
        }
        client.cache(cache)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideBaseAPIService(retrofit: Retrofit): BaseAPIService {
        return retrofit.create(BaseAPIService::class.java)
    }

    /*@Provides
    @Singleton
    fun provideDealsRepository(baseAPIService: BaseAPIService,
                               scheduler: Scheduler, compositeDisposable: CompositeDisposable): DealsRepository
            = DealsRepository(baseAPIService, scheduler, compositeDisposable)

    @Provides
    @Singleton
    fun scheduler(): Scheduler = Scheduler()

    @Provides
    @Singleton
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()*/

    @Provides
    @PreferenceInfo
    internal fun providePreferenceName(): String {
        return PREF_NAME
    }
}