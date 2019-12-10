package androidmvvm.ui.deals_dashboard.tab_todays_deal

import androidmvvm.data.model.Deal
import androidmvvm.data.remote.api.BaseAPIService
import androidmvvm.data.remote.extensions.addTo
import androidmvvm.data.remote.extensions.loading
import androidmvvm.data.remote.extensions.success
import androidmvvm.data.remote.networking.Outcome
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by CIS Dev 816 on 5/6/18.
 */
class TodaysDealsRepository constructor(private val compositeDisposable: CompositeDisposable) {

    @Inject
    lateinit var baseAPIService: BaseAPIService

    //lateinit var scheduler: Scheduler = Scheduler.

    val dealFetchOutcome: PublishSubject<Outcome<List<Deal>>> = PublishSubject.create<Outcome<List<Deal>>>()

    fun getDeals() {
        dealFetchOutcome.loading(true)
        baseAPIService.getDeals()
                //.performOnBackOutOnMain(scheduler)
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ retailers ->
                    //dealFetchOutcome.success(retailers)
                    val result = listOf(Deal) as List<Deal>
                    dealFetchOutcome.success(result)
                }, { error ->
                    //dealFetchOutcome.failed()
                })
                .addTo(compositeDisposable)

    }

}