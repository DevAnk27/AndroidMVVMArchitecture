package com.ank.androidmvvmarchitecture.di.module


import com.ank.androidmvvmarchitecture.di.module.DealDetailsChildFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by CIS Dev 816 on 2/4/18.
 */
@Module(includes = [DealDetailsChildFragmentViewModel::class])
abstract class FragmentViewModelModule {

    /*@Binds
    @IntoMap
    @ViewModelKey(OfferFragmentVM::class)
    abstract fun bindOfferFragmentViewModel(offerFragmentVM: OfferFragmentVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CountDownFragmentVM::class)
    abstract fun bindCountDownFragmentViewModel(countDownFragmentVM: CountDownFragmentVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TourFragmentVM::class)
    abstract fun bindArrangedTripsFragmentViewModel(tourFragmentVM: TourFragmentVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PassoverFragmentVM::class)
    abstract fun bindPassoverFragmentViewModel(passoverFragmentVM: PassoverFragmentVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FlightsFragmentVM::class)
    abstract fun bindFlightsFragmentViewModel(flightsFragmentVM: FlightsFragmentVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PackagesFragmentVM::class)
    abstract fun bindPackagesFragmentViewModel(packagesFragmentVM: PackagesFragmentVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TodaysDealFragmentVM::class)
    abstract fun bindTodaysDealFragmentViewModel(todaysDealFragmentVM: TodaysDealFragmentVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SportsPackagesFragmentVM::class)
    abstract fun bindSportsPackagesFragmentViewModel(sportsPackagesFragmentVM: SportsPackagesFragmentVM): ViewModel*/


}