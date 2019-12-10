package com.ank.androidmvvmarchitecture.utils.toolbar

import android.content.Context
import android.view.MenuItem
import androidx.annotation.BoolRes
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import com.ank.androidmvvmarchitecture.utils.toolbar.callbacks.*

class FragmentToolbar
private constructor(@IdRes val resId: Int,
                    @StringRes val title: Int,
                    val cartItemCount: Int,
                    val remainingTime: Long,
                    @MenuRes val menuId: Int,
                    @BoolRes val isHamburger: Boolean,
                    @BoolRes val isBackButton: Boolean,
                    @BoolRes val isNotification: Boolean,
                    @BoolRes val isNotificationR: Boolean,
                    @BoolRes val isFilter: Boolean,
                    @BoolRes val isCartR: Boolean,
                    @BoolRes val isRefreshR: Boolean,
                    @BoolRes val isElectricLogo: Boolean,
                    @BoolRes val isSearch: Boolean,
                    val context: Context?,
                    val menuItems: MutableList<Int>,
                    val menuClicks: MutableList<MenuItem.OnMenuItemClickListener?>,
                    val backButtonListener: IBackButtonCallback?,
                    val hamburgerListener: IHamburgerCallback?,
                    val notificationListener: INotificationCallback?,
                    val notificationListenerR: INotificationCallbackR?,
                    val filterListener: IFilterCallback?,
                    val iCartRListener: ICartRCallback?,
                    val iRefreshRListener: IRefreshRCallback?,
                    val isSearchListener: ISearchCallback?) {

    companion object {
        @JvmField
        val NO_TOOLBAR = -1
        val NO_NOTIFICATION = -1
        val NO_FILTER = -1
    }


    class Builder {
        private var resId: Int = -1
        private var menuId: Int = -1
        private var title: Int = -1
        private var cartItemCount: Int = -1
        private var remainingTime: Long = -1
        private var isHamburger: Boolean = false
        private var isBackButton: Boolean = false
        private var isNotification: Boolean = false
        private var isNotificationR: Boolean = false
        private var isFilter: Boolean = false
        private var isCartR: Boolean = false
        private var isRefreshR: Boolean = false
        private var isElectricLogo: Boolean = false
        private var isSearch: Boolean = false
        private var context: Context? = null
        private var menuItems: MutableList<Int> = mutableListOf()
        private var menuClicks: MutableList<MenuItem.OnMenuItemClickListener?> = mutableListOf()
        private var backButtonListener: IBackButtonCallback? = null
        private var hamburgerListener: IHamburgerCallback? = null
        private var notificationListener: INotificationCallback? = null
        private var notificationListenerR: INotificationCallbackR? = null
        private var filterListener: IFilterCallback? = null
        private var iCartRListener: ICartRCallback? = null
        private var iRefreshRListener: IRefreshRCallback? = null
        private var iSearchListener: ISearchCallback? = null

        /***
         * Pass FragmentToolbar.NO_TOOLBAR as ID if no toolbar required.
         * */

        fun withId(@IdRes resId: Int) = apply { this.resId = resId }

        fun withTitle(title: Int) = apply { this.title = title }

        fun withCartItemCount(cartItemCount: Int) = apply { this.cartItemCount = cartItemCount }

        fun withRemainingTime(remainingTime: Long?) = apply {
            if(remainingTime != null) {
                this.remainingTime = remainingTime
            } else {
                this.remainingTime = -1
            }
        }

        fun withHamburger() = apply { this.isHamburger = true }

        fun withBackButton() = apply { this.isBackButton = true }

        fun withNotification() = apply {
            this.isNotification = true
        }

        fun withNotificationR() = apply {
            this.isNotificationR = true
        }

        fun withFilter() = apply {
            this.isFilter = true
        }

        fun withCartR() = apply {
            this.isCartR = true
        }

        fun withRefreshR() = apply {
            this.isRefreshR = true
        }

        fun withElectricLogo() = apply {
            this.isElectricLogo = true
        }

        fun withSearch() = apply {
            this.isSearch = true
        }

        fun withContext(context: Context) = apply {
            this.context = context
        }

        fun withMenu(@MenuRes menuId: Int) = apply { this.menuId = menuId }

        fun withMenuItems(menuItems: MutableList<Int>, menuClicks: MutableList<MenuItem.OnMenuItemClickListener?>) = apply {
            this.menuItems.addAll(menuItems)
            this.menuClicks.addAll(menuClicks)
        }

        fun withBackButtonCallback(backButtonListener: IBackButtonCallback) = apply {
            this.backButtonListener = backButtonListener
        }

        fun withHamburgerCallback(hamburgerListener: IHamburgerCallback) = apply {
            this.hamburgerListener = hamburgerListener
        }

        fun withNotificationCallback(notificationListener: INotificationCallback?) = apply {
            this.notificationListener = notificationListener
        }

        fun withNotificationRCallback(notificationListenerR: INotificationCallbackR?) = apply {
            this.notificationListenerR = notificationListenerR
        }

        fun withFilterCallback(filterListener: IFilterCallback?) = apply {
            this.filterListener = filterListener
        }

        fun withCartRCallback(iCartRListener: ICartRCallback?) = apply {
            this.iCartRListener = iCartRListener
        }

        fun withRefreshRCallback(iRefreshRListener: IRefreshRCallback?) = apply {
            this.iRefreshRListener = iRefreshRListener
        }

        fun withSearchCallback(iSearchListener: ISearchCallback?) = apply {
            this.iSearchListener = iSearchListener
        }

        fun build() = FragmentToolbar(resId, title, cartItemCount, remainingTime, menuId, isHamburger,
                isBackButton, isNotification, isNotificationR, isFilter, isCartR, isRefreshR, isElectricLogo, isSearch, context, menuItems, menuClicks, backButtonListener, hamburgerListener,
                notificationListener, notificationListenerR, filterListener, iCartRListener, iRefreshRListener, iSearchListener)
    }

}