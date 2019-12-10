package androidmvvm.ui.auth.countdown


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidmvvm.R
import androidmvvm.data.remote.CounterManager
import androidmvvm.di.module.AppViewModelFactory
import androidmvvm.ui.base.BaseFragment
import androidmvvm.utils.LogUtils
import androidmvvm.utils.toolbar.FragmentToolbar
import kotlinx.android.synthetic.main.fragment_count_down.*
import java.util.*
import javax.inject.Inject


class CountDownFragment : BaseFragment<CountDownFragmentVM>(), View.OnClickListener {

    private val TAG = CountDownFragment::class.java.simpleName

    companion object {

        @JvmStatic
        fun newInstance() =
                CountDownFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    @Inject
    lateinit var appViewModelFactory: AppViewModelFactory
    lateinit var countDownFragmentVM: CountDownFragmentVM
    lateinit var countDownTimer: CountDownTimer
    private var counterManager: CounterManager? = null

    override fun builder(): FragmentToolbar? {
        return null
    }

    override fun getFragmentTAG(): FragmentTAG {
        return FragmentTAG.COUNT_DOWN_FRAG
    }

    override fun getViewModel(): CountDownFragmentVM {
        countDownFragmentVM = ViewModelProviders.of(this@CountDownFragment, appViewModelFactory)
                .get(CountDownFragmentVM::class.java)
        return countDownFragmentVM
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_close -> {
                navigationController.launchDealsDashboardActivity(activity as AppCompatActivity)
                //activity?.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
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
            root = inflater.inflate(R.layout.fragment_count_down, container, false) as ViewGroup
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isFragmentLoaded) {
            initInstances()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        try {
            countDownTimer.cancel()
        } catch (ex: Exception) {
            LogUtils.e(TAG, ex.message.toString())
        }
    }

    private fun initInstances() {
        counterManager = CounterManager.getInstance(activity)

//        if (isNetworkConnected()) {
//            counterManager?.fetchCounterToStart()
//        }

        fl_close.setOnClickListener(this)

        subscribeToLiveData()
    }

    fun showCountDownTimer() {
        val curCal = Calendar.getInstance()
        val currTimeInMill = Calendar.getInstance().timeInMillis

        var wednesdayInMIll: Long = 0

        if (curCal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            if (curCal.get(Calendar.HOUR_OF_DAY) >= 21) {
                wednesdayInMIll = nextDayOfWeek(Calendar.WEDNESDAY, false).timeInMillis
            } else {
                wednesdayInMIll = nextDayOfWeek(Calendar.WEDNESDAY, true).timeInMillis
            }
        } else {
            wednesdayInMIll = nextDayOfWeek(Calendar.WEDNESDAY, false).timeInMillis
        }

        Log.d("TAG", "Current: " + currTimeInMill.toString())
        Log.d("TAG", "Wednesday: " + wednesdayInMIll.toString())

        val millInDiff = wednesdayInMIll - currTimeInMill

        countDownTimer = object : CountDownTimer(millInDiff, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var displayDays: Long = 0
                var displayHours: Long = 0
                var displayMinutes: Long = 0
                var displaySeconds: Long = 0
                // decompose difference into days, hours, minutes and seconds
                displayDays = (millisUntilFinished / 1000 / 86400)
                displayHours = (millisUntilFinished / 1000 - displayDays * 86400) / 3600
                displayMinutes = (millisUntilFinished / 1000 - (displayDays * 86400 + displayHours * 3600)) / 60
                displaySeconds = (millisUntilFinished / 1000 % 60)

                tv_days.text = displayDays.toString()
                tv_hours.text = displayHours.toString()
                tv_minutes.text = displayMinutes.toString()
                tv_seconds.text = displaySeconds.toString()
            }

            override fun onFinish() {
                fl_close.performClick()
            }
        }.start()
    }

    fun showCountDownTimer(remainTimeInSec: Long) {
        try {
//            if(counterManager != null) {
//                countDownTimer.cancel()
//            }

            val millInDiff = remainTimeInSec * 1000
            //val millInDiff = remainTimeInSec

            countDownTimer = object : CountDownTimer(millInDiff, 1000) {

                override fun onTick(millisUntilFinished: Long) {
                    var displayDays: Long = 0
                    var displayHours: Long = 0
                    var displayMinutes: Long = 0
                    var displaySeconds: Long = 0
                    // decompose difference into days, hours, minutes and seconds
                    displayDays = (millisUntilFinished / 1000 / 86400)
                    displayHours = (millisUntilFinished / 1000 - displayDays * 86400) / 3600
                    displayMinutes = (millisUntilFinished / 1000 - (displayDays * 86400 + displayHours * 3600)) / 60
                    displaySeconds = (millisUntilFinished / 1000 % 60)

                    tv_days.text = displayDays.toString()
                    tv_hours.text = displayHours.toString()
                    tv_minutes.text = displayMinutes.toString()
                    tv_seconds.text = displaySeconds.toString()
                }

                override fun onFinish() {
                    fl_close.performClick()
                }
            }.start()
        } catch (ex: java.lang.Exception) {
            LogUtils.e(TAG, ex.message.toString())
        }
    }


    fun nextDayOfWeek(dow: Int, isTodayWednesday: Boolean): Calendar {
        val date = Calendar.getInstance()
        if (isTodayWednesday) {
            date.set(Calendar.HOUR_OF_DAY, 9)
            date.set(Calendar.MINUTE, 0)
            date.set(Calendar.SECOND, 0)
        } else {
            var diff = dow - date.get(Calendar.DAY_OF_WEEK)
            if (diff <= 0) {
                diff += 7
            }
            date.add(Calendar.DAY_OF_MONTH, diff)
            date.set(Calendar.HOUR_OF_DAY, 9)
            date.set(Calendar.MINUTE, 0)
            date.set(Calendar.SECOND, 0)
        }

        return date
    }

    private fun subscribeToLiveData() {
        try {
            counterManager?.coundown?.observe(this, Observer { coundown ->
                if (coundown != null) {
                    tv_msg_1.text = coundown.Upper_Text
                    tv_msg_2.text = coundown.Middle_Text

                    if (!coundown.RemainingTime.isNullOrEmpty()) {
                        showCountDownTimer(coundown.RemainingTime!!.toLong())
                    }
                }
            })

            counterManager?.isLoading?.observe(this, Observer { isLoading ->
                if (isLoading!!) {
                    pb.visibility = View.VISIBLE
                    cl_main.visibility = View.GONE
                } else {
                    pb.visibility = View.GONE
                    cl_main.visibility = View.VISIBLE
                }
            })
        } catch (ex: Exception) {
            LogUtils.e(TAG, ex.message.toString())
        }
    }
}
