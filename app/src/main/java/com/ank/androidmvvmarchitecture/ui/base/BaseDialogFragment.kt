package androidmvvm.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidmvvm.routing.NavigationController
import androidmvvm.utils.Helper
import com.ank.androidmvvmarchitecture.ui.base.BaseActivity
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by CIS Dev 816 on 10/4/18.
 */
abstract class BaseDialogFragment : DialogFragment() {

    private var mActivity: BaseActivity<*>? = null
    protected var root: ViewGroup? = null
    var isFragmentLoaded = false

    @Inject
    lateinit var navigationController: NavigationController

    var helper: Helper? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isFragmentLoaded = true
    }

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

    /**
     * @return the identifying enum for the current fragment.
     */
    abstract fun getFragmentTAG(): FragmentTAG

    /**
     * An enumerator of TAGs for Fragments.
     */
    enum class FragmentTAG {
        NOTIFICATION_DIALOG_FRAG, ORDER_CONFIRMED_DIALOG_FRAG, CALENDAR_DIALOG_FRAG,
        GALLERY_DETAIL_DIALOG_FRAG, VALIDATION_ALERT_DIALOG_FRAG,
        DESTINATION_DIALOG_FRAG, E_DRAWER_MENU_FRAG
    }

    fun getBaseActivity(): BaseActivity<*>? {
        return mActivity
    }


    fun isNetworkConnected(): Boolean {
        return mActivity != null && mActivity!!.isNetworkConnected()
    }

    fun showShortToast(message: String) {
        if (mActivity != null) {
            mActivity!!.showShortToast(message)
        }
    }

    fun isAllowTransition(): Boolean {
        if (mActivity != null) {
            return mActivity!!.isAllowTransition()
        } else {
            return false
        }
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }
}