package com.ank.androidmvvmarchitecture.utils.toolbar

import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ank.androidmvvmarchitecture.utils.Constants
import com.ank.androidmvvmarchitecture.utils.LogUtils
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class ToolbarManager constructor(
    private var builder: FragmentToolbar,
    private var container: View
) {

    private val TAG = ToolbarManager::class.java.simpleName
    private var countDownTimer: CountDownTimer? = null

    /***
     * Choose FragmentToolbar.NO_TOOLBAR as ID, if no toolbar required.
     * */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun prepareToolbar() {
        try {
            if (countDownTimer != null) {
                countDownTimer?.cancel()
            }

            if (builder.resId != FragmentToolbar.NO_TOOLBAR) {
                val fragmentToolbar = container.findViewById(builder.resId) as Toolbar

                if (builder.title != -1) {
                    /*fragmentToolbar.setTitle(builder.title)*/
                    fragmentToolbar.tv_title.setText(builder.title)
                }

                if (builder.cartItemCount > 0) {
                    fragmentToolbar.tv_cart_item_count_r.setText(builder.cartItemCount.toString())
                    fragmentToolbar.tv_cart_item_count_r.visibility = View.VISIBLE
                } else {
                    fragmentToolbar.tv_cart_item_count_r.setText("0")
                    fragmentToolbar.tv_cart_item_count_r.visibility = View.GONE
                }

                if (builder.remainingTime > 0) {
                    fragmentToolbar.ll_countdown.visibility = View.VISIBLE
                    showCountDownTimer(builder.remainingTime)
                } else {
                    fragmentToolbar.ll_countdown.visibility = View.GONE
                }

                if (builder.menuId != -1) {
                    fragmentToolbar.inflateMenu(builder.menuId)
                }

                if (builder.isHamburger) {
                    fragmentToolbar.fl_hamburger.visibility = View.VISIBLE
                    fragmentToolbar.fl_hamburger.setOnClickListener({
                        /*drawerLayout.openDrawer(GravityCompat.START)*/
                        builder.hamburgerListener?.onToolbarHamburgerClicked(it)
                    })
                } else {
                    fragmentToolbar.fl_hamburger.visibility = View.GONE
                }

                if (builder.isBackButton) {
                    assert(builder.isHamburger, { "Both Hamburger and Back cannot be set true" })
                    fragmentToolbar.fl_back_arrow.visibility = View.VISIBLE
                    fragmentToolbar.fl_back_arrow.setOnClickListener({
                        builder.backButtonListener?.onToolbarBackPressed(it)
                    })
                } else {
                    fragmentToolbar.fl_back_arrow.visibility = View.GONE
                }

                if (builder.isNotification) {
                    fragmentToolbar.fl_notification.visibility = View.VISIBLE
                    fragmentToolbar.fl_notification.setOnClickListener({
                        builder.notificationListener?.onToolbarNotificationClicked(it)
                    })
                } else {
                    fragmentToolbar.fl_notification.visibility = View.GONE
                }

                if (builder.isNotificationR) {
                    fragmentToolbar.fl_notification_r.visibility = View.VISIBLE
                    fragmentToolbar.iv_notification_r.setOnClickListener({
                        builder.notificationListenerR?.onToolbarNotificationClicked(it)
                    })
                } else {
                    fragmentToolbar.fl_notification_r.visibility = View.GONE
                }

                if (builder.isFilter) {
                    fragmentToolbar.fl_filter.visibility = View.VISIBLE
                    if (builder.isElectricLogo) {
                        fragmentToolbar.tv_filter.visibility = View.GONE
                    } else {
                        fragmentToolbar.tv_filter.visibility = View.VISIBLE
                    }
                    fragmentToolbar.fl_filter.setOnClickListener({
                        builder.filterListener?.onToolbarFilterClicked(it)
                    })
                } else {
                    fragmentToolbar.fl_filter.visibility = View.GONE
                }

                if (builder.isCartR) {
                    fragmentToolbar.fl_cart_r.visibility = View.VISIBLE
                    fragmentToolbar.fl_cart_r.setOnClickListener({
                        builder.iCartRListener?.onCartClicked(it)
                    })
                } else {
                    fragmentToolbar.fl_cart_r.visibility = View.GONE
                }

                if (builder.isRefreshR) {
                    fragmentToolbar.fl_refresh_r.visibility = View.VISIBLE
                    fragmentToolbar.fl_refresh_r.setOnClickListener({
                        builder.iRefreshRListener?.onRefreshRClickec(it)
                    })
                } else {
                    fragmentToolbar.fl_refresh_r.visibility = View.GONE
                }

                if (builder.isSearch) {
                    fragmentToolbar.fl_search.visibility = View.VISIBLE
                    fragmentToolbar.fl_search.setOnClickListener({
                        builder.isSearchListener?.onToolbarSearchClicked(it)
                    })
                } else {
                    fragmentToolbar.fl_refresh_r.visibility = View.GONE
                }

                if (builder.isElectricLogo) {
                    fragmentToolbar.iv_e_logo.visibility = View.VISIBLE
                    fragmentToolbar.iv_logo.visibility = View.GONE

                } else {
                    fragmentToolbar.iv_e_logo.visibility = View.GONE
                    fragmentToolbar.iv_logo.visibility = View.VISIBLE
                }

                if (!builder.menuItems.isEmpty() && !builder.menuClicks.isEmpty()) {
                    val menu = fragmentToolbar.menu
                    for ((index, menuItemId) in builder.menuItems.withIndex()) {
                        (menu.findItem(menuItemId) as MenuItem).setOnMenuItemClickListener(builder.menuClicks[index])
                    }
                }
            }
        } catch (ex: Exception) {
            LogUtils.e(TAG, ex.message.toString())
        }
    }

    fun setCartItemCount(cartItemCount: Int) {
        val fragmentToolbar = container.findViewById(builder.resId) as Toolbar

        if (cartItemCount > 0) {
            fragmentToolbar.tv_cart_item_count_r.setText(cartItemCount.toString())
            fragmentToolbar.tv_cart_item_count_r.visibility = View.VISIBLE
        } else {
            fragmentToolbar.tv_cart_item_count_r.setText("0")
            fragmentToolbar.tv_cart_item_count_r.visibility = View.GONE
        }
    }

    fun showCountDownTimer(remainTimeInSec: Long?) {
        try {
            val fragmentToolbar = container.findViewById(builder.resId) as Toolbar
            val millInDiff = remainTimeInSec!! * 1000
            //val millInDiff = remainTimeInSec

            countDownTimer = object : CountDownTimer(millInDiff, 1000) {

                override fun onTick(millisUntilFinished: Long) {
                    //CounterManager.remainTimeToStop = millisUntilFinished / 1000
                    var displayDays: Long = 0
                    var displayHours: Long = 0
                    var displayMinutes: Long = 0
                    var displaySeconds: Long = 0
                    // decompose difference into days, hours, minutes and seconds
                    displayDays = (millisUntilFinished / 1000 / 86400)
                    displayHours = (millisUntilFinished / 1000 - displayDays * 86400) / 3600
                    displayMinutes = (millisUntilFinished / 1000 - (displayDays * 86400 + displayHours * 3600)) / 60
                    displaySeconds = (millisUntilFinished / 1000 % 60)

                    //fragmentToolbar.tv_hour.text = displayDays.toString()
                    fragmentToolbar.tv_hour.text = displayHours.toString()
                    fragmentToolbar.tv_minute.text = displayMinutes.toString()
                    fragmentToolbar.tv_second.text = displaySeconds.toString()
                }

                override fun onFinish() {
                    //fl_close.performClick()
                    val authIntent = Intent(Constants.BROADCAST_AUTH_INTENT)
                    LocalBroadcastManager.getInstance(fragmentToolbar.context).sendBroadcast(authIntent)
                }
            }.start()
        } catch (ex: Exception) {
            LogUtils.e(TAG, ex.message.toString())
        }
    }

    fun stopCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer?.cancel()
        }
    }
}