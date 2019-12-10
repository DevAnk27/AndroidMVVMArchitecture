package androidmvvm.ui.splash

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewTreeObserver
import androidmvvm.R
import androidmvvm.data.AppDataManager
import androidmvvm.data.remote.CounterManager
import androidmvvm.data.remote.DealsManager
import androidmvvm.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject


class SplashActivity : BaseActivity<SplashActivityVM>() {

    private val TAG = SplashActivity::class.java!!.getSimpleName()

    @Inject
    lateinit var splashActivityVM: SplashActivityVM
    @Inject
    lateinit var appDataManager: AppDataManager

    override fun getViewModel(): SplashActivityVM = splashActivityVM
    lateinit var dealsManager: DealsManager
    lateinit var counterManager: CounterManager

    companion object {
        fun startInstanceWithBackStackCleared(context: Context?) {
            val intent = Intent(context, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }

        // inflate your main layout here (use RelativeLayout or whatever your root ViewGroup type is
        val mainLayout = this.layoutInflater.inflate(R.layout.activity_splash, null) as ConstraintLayout

        // set a global layout listener which will be called when the layout pass is completed and the view is drawn
        mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        //Remove the listener before proceeding
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                        } else {
                            mainLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this)
                        }

                        // measure your views here

                        setUpAnimation()
                    }
                }
        )


        //setContentView(R.layout.activity_splash)
        setContentView(mainLayout)

        dealsManager = DealsManager.getInstance(this)
        counterManager = CounterManager.getInstance(this)

        if (dealsManager.isDelasEmpty() && isNetworkConnected()) {
            dealsManager.fetchDeals()
            counterManager.fetchCounterToStart()
            counterManager.fetchCounterToStop()
        } else if (!isNetworkConnected()) {
            helper?.showShortToast(getString(R.string.no_internet_connection))
        }
    }

    private fun getRelativeLeft(myView: View): Int {
        return if (myView.parent === myView.rootView)
            myView.left
        else
            myView.left + getRelativeLeft(myView.parent as View)
    }


    private fun setUpAnimation() {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        val location = IntArray(2)
        imageView.getLocationInWindow(location)
        val x = location[0]
        val y = location[1]

        val guidelinePos = x.toFloat()
        val fp = guidelinePos.plus(30)

        val animX: ObjectAnimator = ObjectAnimator.ofFloat(iv_plane_anim, View.TRANSLATION_X, fp!!)

        animX.setDuration(2000)
        animX.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                launchNextScreen()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
        animX.start()
    }

    private fun launchNextScreen() {
        Handler().postDelayed({
            navigationController.launchAuthActivity(this)
        }, 500)
    }
}
