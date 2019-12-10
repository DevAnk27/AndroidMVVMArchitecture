package com.ank.androidmvvmarchitecture.ui.base

import android.content.Context
import android.os.Bundle

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidmvvm.routing.NavigationController
import androidmvvm.ui.base.BaseFragment
import com.ank.androidmvvmarchitecture.utils.Helper
import com.ank.androidmvvmarchitecture.utils.LocaleHelper
import com.ank.androidmvvmarchitecture.utils.NetworkUtils
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<out V : ViewModel> : AppCompatActivity(), BaseFragment.Callback {


    private lateinit var mViewModel: V
    var allowTransaction = true
    var helper: Helper? = null
    @Inject
    lateinit var navigationController: NavigationController

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)

        helper = Helper(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    override fun onTransitionStarted() {
        allowTransaction = false
    }

    override fun onTransitionEnded() {
        allowTransaction = true
    }

    override fun onTransitionRepeated() {
        allowTransaction = false
    }

    override fun onResume() {
        super.onResume()

        allowTransaction = true
    }

    override fun onStop() {
        super.onStop()

        allowTransaction = false
    }

    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
        mViewModel = getViewModel()
    }

    fun isAllowTransition(): Boolean {
        return allowTransaction
    }

    fun isNetworkConnected(): Boolean = NetworkUtils.isNetworkConnected(applicationContext)

    fun hideKeyboard() {
        val view: View? = this.currentFocus
        val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    fun showShortToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showNotificationDialog() {
        navigationController.launchNotificationDialogFragment(this)
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V

    open fun hideBottomTabs() {

    }

    open fun showBottomTabs() {

    }

}