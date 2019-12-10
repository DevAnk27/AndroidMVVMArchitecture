package androidmvvm.ui.base

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidmvvm.routing.NavigationController
import androidmvvm.utils.Helper
import androidmvvm.utils.toolbar.FragmentToolbar
import androidmvvm.utils.toolbar.ToolbarManager
import com.ank.androidmvvmarchitecture.ui.base.BaseActivity
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Created by CIS Dev 816 on 12/4/18.
 */
abstract class BaseGrandChildFragment<out V : ViewModel> : DaggerFragment() {

    private var mActivity: BaseActivity<*>? = null
    private var mViewModel: V? = null
    protected var root: ViewGroup? = null
    var isFragmentLoaded = false

    @Inject
    lateinit var navigationController: NavigationController

    var helper: Helper? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentToolbar = builder()
        if (fragmentToolbar != null) {
            ToolbarManager(fragmentToolbar, view).prepareToolbar()
        }
        isFragmentLoaded = true
    }

    protected abstract fun builder(): FragmentToolbar?

    override fun onAttach(context: Context?) {
        performDependencyInjection()
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            val activity = context as BaseActivity<*>?
            this.mActivity = activity
            activity!!.onFragmentAttached()
            helper = mActivity!!.helper
        }
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
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

    /**
     * @return the identifying enum for the current fragment.
     */
    abstract fun getFragmentTAG(): FragmentTAG

    /**
     * An enumerator of TAGs for Fragments.
     */
    enum class FragmentTAG {
        A_FRAG, B_FRAG
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

    fun isAllowTransition(): Boolean {
        return if (mActivity != null) {
            mActivity!!.isAllowTransition()
        } else {
            false
        }
    }

    fun showNotificationDialog() {
        if (mActivity != null) {
            mActivity!!.showNotificationDialog()
        }
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V
}