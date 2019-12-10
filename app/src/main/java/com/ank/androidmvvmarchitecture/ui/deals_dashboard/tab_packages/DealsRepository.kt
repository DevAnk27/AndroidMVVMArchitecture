package androidmvvm.ui.deals_dashboard.tab_packages

import androidmvvm.data.model.Deal
import androidmvvm.data.remote.api.BaseAPIService
import androidmvvm.data.remote.extensions.loading
import androidmvvm.data.remote.networking.Outcome
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by CIS Dev 816 on 19/5/18.
 */
class DealsRepository constructor(private val compositeDisposable: CompositeDisposable) {

    @Inject
    lateinit var baseAPIService: BaseAPIService

    //lateinit var scheduler: Scheduler = Scheduler.

    val dealFetchOutcome: PublishSubject<Outcome<List<Deal>>> = PublishSubject.create<Outcome<List<Deal>>>()

    fun getDeals() {
        //===========================
        dealFetchOutcome.loading(true)
    }

}