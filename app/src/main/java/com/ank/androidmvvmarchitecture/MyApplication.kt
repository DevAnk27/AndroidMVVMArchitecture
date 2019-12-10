package com.ank.androidmvvmarchitecture

import android.app.Activity
import android.app.Application
import android.app.Fragment
import android.content.Context
import com.ank.androidmvvmarchitecture.utils.LocaleHelper
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by CIS Dev 816 on 4/4/18.
 */

class MyApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()

        LocaleHelper.setLocale(this, "iw")

        context = this
        // initialize Dagger
        DaggerAppComponent.builder().application(this).build().inject(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "iw"))
        //MultiDex.install(this)
    }

    /*override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }*/

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    companion object {
        lateinit var context: Context
    }
}