package androidmvvm.ui.deals_dashboard.tab_packages.package_detail

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import androidmvvm.data.model.Tab
import javax.inject.Inject

/**
 * Created by CIS Dev 816 on 12/4/18.
 */
class PackageDetailFragmentVM @Inject constructor() : ViewModel() {

    val tabs: MutableLiveData<List<Tab>>

    @Inject
    lateinit var context: Application

    init {
        tabs = MutableLiveData()
        loadTabs()
    }

    private fun loadTabs() {

    }
}