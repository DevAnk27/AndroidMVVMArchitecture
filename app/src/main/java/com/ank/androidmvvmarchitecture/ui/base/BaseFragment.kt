package androidmvvm.ui.base

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidmvvm.routing.NavigationController
import androidmvvm.utils.Helper
import androidmvvm.utils.toolbar.FragmentToolbar
import androidmvvm.utils.toolbar.ToolbarManager
import com.ank.androidmvvmarchitecture.ui.base.BaseActivity
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<out V : ViewModel> : Fragment() {

    private var mActivity: BaseActivity<*>? = null
    private var mViewModel: V? = null
    protected var root: ViewGroup? = null
    var isFragmentLoaded = false
    var toolbarManager: ToolbarManager? = null

    @Inject
    lateinit var navigationController: NavigationController

    var helper: Helper? = null

    val keyboardLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        try {
            // navigation bar height
            var navigationBarHeight = 0
            var resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                navigationBarHeight = resources.getDimensionPixelSize(resourceId)
            }

            // status bar height
            var statusBarHeight = 0
            resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                statusBarHeight = resources.getDimensionPixelSize(resourceId)
            }

            // display window size for the app layout
            val rect = Rect()
            activity?.getWindow()?.getDecorView()?.getWindowVisibleDisplayFrame(rect)

            // screen height - (user app height + status + nav) ..... if non-zero, then there is a soft keyboard
            val keyboardHeight = root?.getRootView()?.getHeight()!! - (statusBarHeight + navigationBarHeight + rect.height())

            if (keyboardHeight <= 0) {
                onKeyboardClose()
            } else {
                //onShowKeyboard(keyboardHeight)
                onKeyboardOpen()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    open fun onKeyboardOpen() {

    }

    open fun onKeyboardClose() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val fragmentToolbar = builder()
            if (fragmentToolbar != null) {
                toolbarManager = ToolbarManager(fragmentToolbar, view)
                toolbarManager?.prepareToolbar()
            }
            isFragmentLoaded = true
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    protected abstract fun builder(): FragmentToolbar?

    override fun onAttach(context: Context?) {
        try {
            performDependencyInjection()
            super.onAttach(context)
            if (context is BaseActivity<*>) {
                val activity = context as BaseActivity<*>?
                this.mActivity = activity
                activity!!.onFragmentAttached()
                if (mActivity != null && mActivity!!.helper != null) {
                    helper = mActivity!!.helper
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onStop() {
        toolbarManager?.stopCountDownTimer()
        super.onStop()
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()

        isFragmentLoaded = false
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        var animation: Animation? = super.onCreateAnimation(transit, enter, nextAnim)

        if (animation == null && nextAnim != 0) {
            animation = AnimationUtils.loadAnimation(activity, nextAnim)
        }

        if (animation != null) {
            view!!.setLayerType(View.LAYER_TYPE_HARDWARE, null)

            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    mActivity?.onTransitionStarted()
                }

                override fun onAnimationEnd(animation: Animation) {
                    if (view != null) {
                        view!!.setLayerType(View.LAYER_TYPE_NONE, null)
                    }
                    mActivity?.onTransitionEnded()
                }

                override fun onAnimationRepeat(animation: Animation) {
                    mActivity?.onTransitionRepeated()
                }
                // ...other AnimationListener methods go here...
            })
        }
        return animation
    }

    fun setCartItemCount(cartItemCount: Int) {
        toolbarManager?.setCartItemCount(cartItemCount)
    }

    /**
     * @return the identifying enum for the current fragment.
     */
    abstract fun getFragmentTAG(): FragmentTAG

    /**
     * An enumerator of TAGs for Fragments.
     */
    enum class FragmentTAG {
        SIGN_IN_FRAG, SIGN_UP_FRAG, FORGOT_PASSWORD_FRAG,
        OFFER_FRAG, COUNT_DOWN_FRAG, APP_SELECTION_FRAG,
        TOUR_FRAG, PASSOVER_FRAG, PACKAGES_FRAG, FLIGHTS_FRAG, TODAYS_DEAL_FRAG, SPORTS_PACKAGES_FRAG, CONCERTS_FRAG,
        DEAL_DETAILS_FRAG, PACKAGE_DETAIL_FRAG, FLIGHT_FORTH_RETURN_DETAIL_FRAG,
        PASSENGER_COUNT_FRAG, PASSENGER_DETAILS_FRAG, PAYMENT_DETAILS_FRAG, ORDER_CONFIRMATION_FRAG,
        SALES_NOTIFICATION_FRAG, DESTINATION_NOTIFICATION_FRAG, NOTIFICATION_SWITCHES_FRAG,
        FILTER_FRAG, FILTER_RESULT_FRAG,
        HOTEL_GALLERY_FRAG,
        SELECT_ROOM_FRAG, SELECT_FLIGHT_FRAG
    }

    fun getBaseActivity(): BaseActivity<*>? {
        return mActivity
    }


    fun isNetworkConnected(): Boolean {
        return mActivity != null && mActivity!!.isNetworkConnected()
    }

    fun hideKeyboard() {
        if (mActivity != null) {
            mActivity!!.hideKeyboard()
        }
    }

    fun showShortToast(message: String) {
        if (mActivity != null) {
            mActivity!!.showShortToast(message)
        }
    }

    fun showNotificationDialog() {
        if (mActivity != null) {
            mActivity!!.showNotificationDialog()
        }
    }

    fun hideBottomTabs() {
        if (mActivity != null) {
            mActivity!!.hideBottomTabs()
        }
    }

    fun showBottomTabs() {
        if (mActivity != null) {
            mActivity!!.showBottomTabs()
        }
    }

    fun isAllowTransition(): Boolean {
        return if (mActivity != null) {
            mActivity!!.isAllowTransition()
        } else {
            false
        }
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)

        fun onTransitionStarted()

        fun onTransitionEnded()

        fun onTransitionRepeated()
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V


}