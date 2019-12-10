package com.ank.androidmvvmarchitecture.di.component

import android.app.Application
import com.ank.androidmvvmarchitecture.di.module.ActivityBindingModule
import androidmvvm.di.module.androidmvvmApplicationModule
import com.ank.androidmvvmarchitecture.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by CIS Dev 816 on 27/3/18.
 */

@Singleton
@Component(modules = [(androidmvvmApplicationModule::class),
    (ActivityBindingModule::class),
    (AndroidSupportInjectionModule::class)])
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(myApplication: MyApplication)
}