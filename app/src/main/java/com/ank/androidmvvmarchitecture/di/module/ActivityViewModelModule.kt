package com.ank.androidmvvmarchitecture.di.module

import dagger.Module

/**
 * Created by CIS Dev 816 on 30/3/18.
 */
@Module(includes = [FragmentViewModelModule::class])
abstract class ActivityViewModelModule {

    /*@Binds
    @IntoMap
    @ViewModelKey(SplashActivityVM::class)
    abstract fun bindSplashActivityViewModel(splashActivityVM: SplashActivityVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthActivityVM::class)
    abstract fun bindAuthActivityViewModel(authActivityVM: AuthActivityVM): ViewModel*/

    /*@Binds
    @IntoMap
    @ViewModelKey(DashboardActivityVM::class)
    abstract fun bindDashboardActivityViewModel(dashboardActivityVM: DashboardActivityVM): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory*/
}