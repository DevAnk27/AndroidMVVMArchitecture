package com.ank.androidmvvmarchitecture.ui.auth.offer/*




import android.os.Bundle
import android.os.Handler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidmvvm.ui.base.BaseFragment
import java.util.*

import javax.inject.Inject


class OfferFragment : BaseFragment<OfferFragmentVM>(), View.OnClickListener {

    private val TAG = OfferFragment::class.java.simpleName

    companion object {

        @JvmStatic
        fun newInstance() =
                OfferFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    @Inject
    lateinit var appViewModelFactory: AppViewModelFactory
    lateinit var offerFragmentVM: OfferFragmentVM
    lateinit var counterManager: CounterManager

    lateinit var handler: Handler
    var runnable: Runnable? = null
    var isShowTimer = false

    override fun builder(): FragmentToolbar? {
        return null
    }

    override fun getFragmentTAG(): FragmentTAG {
        return FragmentTAG.OFFER_FRAG
    }

    override fun getViewModel(): OfferFragmentVM {
        offerFragmentVM = ViewModelProviders.of(this@OfferFragment, appViewModelFactory)
                .get(OfferFragmentVM::class.java)
        return offerFragmentVM
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_close -> {
                if (runnable != null) {
                    handler.removeCallbacks(runnable)
                    launchNextScreen()
                } else {
                    launchNextScreen()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_offer, container, false) as ViewGroup
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isFragmentLoaded) {
            initInstances()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initInstances() {
        counterManager = CounterManager.getInstance(activity)

        subscribeToLiveData()

        handler = Handler()
        fl_close.setOnClickListener(this)

        */
/*runnable = Runnable {

            launchNextScreen()
        }
        handler.postDelayed(runnable, 3000)*//*

    }

    private fun subscribeToLiveData() {
        try {
            counterManager.coundown.observe(this, Observer { coundown ->
                if (coundown != null) {
                    val strRemainTime = coundown.RemainingTime
                    if (strRemainTime != null) {
                        val intRemainTime = strRemainTime.toInt()
                        isShowTimer = intRemainTime > 0

                        createRunnable()
                    } else {
                        createRunnable()
                    }
                }
            })

            counterManager.isLoading.observe(this, Observer { isLoading ->
                if (isLoading!!) {
                    pb.visibility = View.VISIBLE
                    rl_main.visibility = View.GONE
                } else {
                    pb.visibility = View.GONE
                    rl_main.visibility = View.VISIBLE
                }
            })

            counterManager.error.observe(this, Observer { error ->
                if (!counterManager.isLoading.value!!) {
                    createRunnable()
                }
            })
        } catch (ex: Exception) {
            LogUtils.e(TAG, ex.message.toString())
        }
    }

    private fun createRunnable() {
        runnable = Runnable {

            launchNextScreen()
        }
        handler.postDelayed(runnable, 3000)
    }

    private fun launchNextScreen() {
        */
/*if (isShowCountDownTimer()) {
            navigationController.launchCountDownFragment(activity, R.id.fl_auth_container)
        } else {
            navigationController.launchDealsDashboardActivity()
            activity?.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
        }*//*


        if(activity != null) {
            if (isShowTimer) {
                navigationController.launchCountDownFragment(activity, R.id.fl_auth_container)
                //navigationController.launchDealsDashboardActivity(activity as AppCompatActivity)
            } else {
                navigationController.launchDealsDashboardActivity(activity as AppCompatActivity)
                //activity?.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
            }
        }
    }

    private fun isShowCountDownTimer(): Boolean {
        var isShow = false
        val currCal = Calendar.getInstance()
        if (currCal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            val hourOfDay = currCal.get(Calendar.HOUR_OF_DAY)
            if (hourOfDay < 9 || hourOfDay >= 21) {
                isShow = true
            }
        } else {
            isShow = true
        }

        return isShow
    }

}
*/
