package com.ank.androidmvvmarchitecture.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit



/**
 * Created by CIS Dev 816 on 9/2/18.
 */
class Helper(activity: Activity) {

    private var activity: Activity? = null
    private var progressDialog: ProgressDialog? = null
    private var dialog: Dialog? = null
    private val anim: RotateAnimation? = null
    private val prog: ImageView? = null
    internal var tvLoading: TextView? = null

    fun Helper(activity: Activity) {
        this.activity = activity
        //createFullScreenProgress(activity);
    }

    init {
        this.activity = activity
    }

    companion object {
        private val TAG = Helper::class.java.getSimpleName()

        private var screenWidth = 0
        private var screenHeight = 0

        fun isHebrewLanguage(): Boolean {
            return Locale.getDefault().toString().equals("iw")
        }

        fun updateToHebrew(view: View) {
            view.scaleX = -1F
        }

        fun getDaysLeft(endDate: String?): String? {
            val myFormat = SimpleDateFormat("yyyy-MM-dd")
            val inputString1: String? = endDate
            var days: String? = null
            val currDate: Date = Calendar.getInstance().time

            try {
                val date2: Date = myFormat.parse(inputString1)
                val diff: Long = date2.time - currDate.time
                days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return days
        }

        fun getScreenHeight(c: Context?): Int {
            if (screenHeight == 0) {
                val wm = c?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display = wm.defaultDisplay
                val size = Point()
                display.getSize(size)
                screenHeight = size.y
            }

            return screenHeight
        }

        fun getScreenWidth(c: Context?): Int {
            if (screenWidth == 0) {
                val wm = c?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display = wm.defaultDisplay
                val size = Point()
                display.getSize(size)
                screenWidth = size.x
            }

            return screenWidth
        }

        fun getDisplayDate(dateInEnglish: String?): Pair<String, String> {
            var dd = ""
            var mmYYYY = ""
            try {
                val fromFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                val date = fromFormat.parse(dateInEnglish)

                val toFormat = SimpleDateFormat("dd-MMM-yyyy")
                val result = toFormat.format(date)

                val arr = result.split("-")
                dd = arr[0]
                mmYYYY = arr[1] + "\n" + arr[2]
            } catch (ex: Exception) {
                LogUtils.e(TAG, ex.message.toString())
                return getDisplayDateWiththoutSec(dateInEnglish)
            }
            return Pair(dd, mmYYYY)
        }

        fun getEngDate(dateInEnglish: String?): String {
            var result = ""
            try {
                val fromFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val date = fromFormat.parse(dateInEnglish)

                val toFormat = SimpleDateFormat("dd/MM/yy")
                result = toFormat.format(date)
            } catch (e: ParseException){
                e.printStackTrace()
            }
            return result
        }


        fun getDisplayDateWiththoutSec(dateInEnglish: String?): Pair<String, String> {
            var dd = ""
            var mmYYYY = ""
            try {
                val fromFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val date = fromFormat.parse(dateInEnglish)

                val toFormat = SimpleDateFormat("dd-MMM-yyyy")
                val result = toFormat.format(date)

                val arr = result.split("-")
                dd = arr[0]
                mmYYYY = arr[1] + "\n" + arr[2]
            } catch (ex: Exception) {
                LogUtils.e(TAG, ex.message.toString())
            }
            return Pair(dd, mmYYYY)
        }

        fun getDateWithTime(dateInEnglish: String?): Pair<String, String> {
            var ddMMMyyyy = ""
            var hhMM = ""
            try {
                val fromFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val date = fromFormat.parse(dateInEnglish)

                val toFormat = SimpleDateFormat("dd-MMM-yyyy")
                ddMMMyyyy = toFormat.format(date)

                val arr = dateInEnglish?.split("T")
                val hhmmss = arr?.get(1)
                hhMM = hhmmss?.substring(0, 5)!!
            } catch (ex: Exception) {
                LogUtils.e(TAG, ex.message.toString())
            }
            return Pair(ddMMMyyyy, hhMM)
        }

        fun getDayDateWithTime(dateInEnglish: String?): Pair<String, String> {
            var EEEddMMMyyyy = ""
            var hhMM = ""
            try {
                val fromFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val date = fromFormat.parse(dateInEnglish)

                val toFormat = SimpleDateFormat("EEE dd-MMM-yyyy")
                EEEddMMMyyyy = toFormat.format(date)

                val arr = dateInEnglish?.split("T")
                val hhmmss = arr?.get(1)
                hhMM = hhmmss?.substring(0, 5)!!
            } catch (ex: Exception) {
                LogUtils.e(TAG, ex.message.toString())
            }
            return Pair(EEEddMMMyyyy, hhMM)
        }

        fun getDateForReservePlace(inComingDate: String?): String {
            var ddMMMyyyy = ""
            var hhMM = ""
            try {
                val fromFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val date = fromFormat.parse(inComingDate)

                val toFormat = SimpleDateFormat("dd/MM/yyyy")
                ddMMMyyyy = toFormat.format(date)
            } catch (ex: Exception) {
                LogUtils.e(TAG, ex.message.toString())
            }
            return ddMMMyyyy
        }

        fun getDuration(duInMinutes: String): String {
            var result = ""
            try {
                val minute = duInMinutes.toInt()
                val hh = minute.div(60).toInt()
                val hhInMinute = hh.times(60)
                val mm = minute.minus(hhInMinute)

                if (hh > 0) {
                    result = hh.toString() + " hrs "
                }

                if (mm > 0) {
                    result = result + mm.toString() + " min"
                }
            } catch (ex: Exception) {
                LogUtils.e(TAG, ex.message.toString())
            }
            return result
        }

        fun getDate(dateInEnglish: String?): Date {
            var dt: Date = Date()
            try {
                val fromFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                val date = fromFormat.parse(dateInEnglish)

                dt = date
            } catch (ex: Exception) {
                LogUtils.e(TAG, ex.message.toString())
                return getDateWiththoutSec(dateInEnglish)
            }
            return dt
        }

        fun getDateWiththoutSec(dateInEnglish: String?): Date {
            var dt: Date = Date()

            try {
                val fromFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val date = fromFormat.parse(dateInEnglish)

                dt = date
            } catch (ex: Exception) {
                LogUtils.e(TAG, ex.message.toString())
            }
            return dt
        }

        /**
         * Returns the unique serial number of device
         */
        fun getSerialNumber(): String {
            return Build.SERIAL
        }

        /*fun setFlightLogo(context: Context?, ivFlightLogo: ImageView, flAirline: String?) {
            try {
                when (flAirline) {
                    "LY" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.ly))
                    }

                    "IZ" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.iz))
                    }

                    "QS" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.qs))
                    }

                    "UX" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.ux))
                    }

                    "J2" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.j_two))
                    }

                    "AEA" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.aea))
                    }

                    "LX" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.lx))
                    }

                    "RO" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.ro))
                    }

                    "LH" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.lh))
                    }

                    "NO" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.no))
                    }

                    "A3" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.a_three))
                    }

                    "CAI" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.cai))
                    }

                    "VY" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.vy))
                    }

                    "TWI" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.twi))
                    }

                    "LY0" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.ly_zero))
                    }

                    "8Q" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.eight_q))
                    }

                    *//*"XXX" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.xxx))
                    }*//*

                    "C0" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.co))
                    }

                    "U8" -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.u_eight))
                    }

                    else -> {
                        ivFlightLogo.setImageDrawable(context?.resources?.getDrawable(R.drawable.img_placeholder))
                    }
                }
            } catch (ex: Exception) {

            }
        }*/

        fun dpToPx(dp: Float): Float {
            return (dp * Resources.getSystem().getDisplayMetrics().density) as Float
        }

        fun pxToDp(px: Float): Float {
            return (px / Resources.getSystem().getDisplayMetrics().density) as Float
        }

        fun getDateOfBirth(yearAgo: Int): String {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -yearAgo)
            val date = calendar.time
            val outputFmt = SimpleDateFormat("dd/MM/yyyy")
            val ddMMYYYY = outputFmt.format(date)
            return ddMMYYYY
        }
    }

    /*fun createFullScreenProgress(ctx: Context) {
        dialog = Dialog(ctx)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //progressDialog.setCancelable(false);
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(0))
        dialog?.setContentView(R.layout.dialog_progress);
        //prog=(ImageView)dialog.findViewById(R.id.pb);
        //tvLoading= (TextView) dialog.findViewById(R.id.progressBarTextView);
    }*/

    fun isAndroid5(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    fun isAndroid6(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    fun showFullScreenProgress(cancellable: Boolean) {
        if (activity != null) {
            activity!!.runOnUiThread {
                dialog!!.setCancelable(cancellable)
                prog!!.startAnimation(anim)
                dialog?.show()
            }
        }
    }

    fun dismissFullScreenProgress() {
        try {
            if (activity != null && dialog != null && dialog!!.isShowing) {
                prog!!.animation = null
                dialog?.dismiss()
            }
        } catch (ex: Exception) {
            LogUtils.e(TAG, ex.message.toString())
        }
    }

    fun showProgressDialog(title: String, message: String) {
        if (activity != null) {
            activity!!.runOnUiThread { progressDialog = ProgressDialog.show(activity, title, message) }
        }
    }

    fun isProgressActive(): Boolean {
        return if (progressDialog != null) {
            progressDialog!!.isShowing
        } else {
            false
        }
    }

    fun dismissProgressDialog() {
        if (activity != null && progressDialog != null) {
            progressDialog!!.dismiss()
        }
    }

    fun showAlertDialog(title: String?, message: String, btnText: String?) {
        if (title != null && !title.isNullOrEmpty()) {
            AlertDialog.Builder(activity).setTitle(title).setMessage(message).setPositiveButton(btnText
            ) { dialog, id -> dialog.dismiss() }.create().show()
        } else {
            AlertDialog.Builder(activity).setMessage(message).setPositiveButton(btnText
            ) { dialog, id -> dialog.dismiss() }.create().show()
        }
    }

    fun showAlertDialogOnUiThread(title: String, message: String) {
        if (activity != null) {
            activity!!.runOnUiThread {
                AlertDialog.Builder(activity).setTitle(title).setMessage(message).setPositiveButton("OK"
                ) { dialog, id -> dialog.dismiss() }.create().show()
            }
        }
    }

    fun showErrorFinishAppDialogOnGuiThread(errorMessage: String) {
        if (activity != null) {
            activity!!.runOnUiThread {
                AlertDialog.Builder(activity).setMessage(errorMessage).setTitle("message").setPositiveButton("OK") { dialog, id ->
                    dialog.dismiss()
                    System.runFinalizersOnExit(true)
                    System.exit(0)
                }.create().show()
            }
        }
    }

    fun showErrorFinishAppDialog(errorMessage: String) {
        if (activity != null) {
            AlertDialog.Builder(activity).setMessage(errorMessage).setTitle("message").setPositiveButton("OK") { dialog, id ->
                dialog.dismiss()
                System.runFinalizersOnExit(true)
                System.exit(0)
            }.create().show()
        }
    }

    fun showShortToast(msg: String) {
        if (activity != null) {
            activity!!.runOnUiThread { Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show() }
        }
    }

    fun showLongToast(msg: String) {
        if (activity != null) {
            activity!!.runOnUiThread { Toast.makeText(activity, msg, Toast.LENGTH_LONG).show() }
        }
    }

}