package com.ank.androidmvvmarchitecture.utils


/**
 * Created by CIS Dev 816 on 9/2/18.
 */
object Constants {

    val PREF_NAME = "perf_"

    val SPLASH_TIME: Long = 2000
    val REQUEST_CODE_WRITE_CALENDAR_PERMISSION = 1

    val AM = "AM"
    val PM = "PM"
    val COUNTRY_ID = "45"
    val STATE_ID = "0"

    val DOLLER_SIZE = 0.65f
    val BROADCAST_AUTH_INTENT = "broadcast_auth_intent"

    /*enum class DealsHomeTabs(val value: String, val iconUnchecked: Int, val iconChecked: Int) {
        TODAYS_DEALS(androidmvvm.Application.context.resources.getString(R.string.tab_todays_deal), R.mipmap.todays_deal_unchecked, R.mipmap.todays_deal_checked),
        PACKAGES(androidmvvmApplication.context.resources.getString(R.string.tab_packages), R.mipmap.packages_unchecked, R.mipmap.packages_checked),
        FLIGHTS(androidmvvmApplication.context.resources.getString(R.string.tab_flights), R.mipmap.flights_unchecked, R.mipmap.flights_checked),
        TOUR(androidmvvmApplication.context.resources.getString(R.string.tab_tour), R.mipmap.tour_unchecked, R.mipmap.tour_checked),
        PASSOVER(androidmvvmApplication.context.resources.getString(R.string.tab_passover), R.mipmap.passover_unchecked, R.mipmap.passover_checked),
        SPORTS(androidmvvmApplication.context.resources.getString(R.string.tab_sports), R.mipmap.sport_unchecked, R.mipmap.sport_checked),
        CONCERTS(androidmvvmApplication.context.resources.getString(R.string.tab_concerts), R.mipmap.concert_unchecked, R.mipmap.concert_checked),
    }

    enum class PackageDetailsTabs(val value: String) {
        HOTEL(androidmvvmApplication.context.resources.getString(R.string.details_tab_hotel)),
        FLIGHTS(androidmvvmApplication.context.resources.getString(R.string.details_tab_flights)),
        PRICE(androidmvvmApplication.context.resources.getString(R.string.details_tab_price))
    }*/



    interface KEY {
        companion object {
            val DEAL = "key_deal"
            val FLIGHT = "key_flight"
            val FROM = "key_from"
            val FILTER = "key_filter"
            val TOTAL_PRICE = "key_total_price"
            val PASSENGER_COUNT = "key_passenger_count"
            val ALERT_MESSAGE = "key_alert_message"
            val POSITION = "key_position"
            val FLIGHTS_COUNT = "key_flight_count"
            val CREDIT_MESSAGE = "credit_message"
            val FLIGHT_DETAIL = "flight_detail"
            val RES_UNIQUE_ID = "res_unique_id"
            val COMFIRMATION_NUMBER = "confirmation_number"
        }
    }

    interface CATEGORY {
        companion object {
            val FLIGHT = "1"
            val PACKAGE = "2"
            val TOUR = "3"
            val SPORTS = "4"
            val CONCERT = "5"
            val PASSOVER = "6"
        }
    }

    interface Flight {
        companion object {
            val NON_STOP = "Non Stop"
            val ONE_STOP = "One Stop"
        }
    }

    interface Currency {
        companion object {
            val US = "US"
            val EU = "EU"
            val DOLLER = "$"
            val EURO = "€"
            var CURRENT_CURRENCY = "$"
        }
    }

    interface API {
        interface KEY {
            companion object {
                val STATUS = "Status"
                val ERROR = "error"
                val CODE = "code"
                val MESSAGE = "Message"
                val APPLICATION_ID = "application_id"
                val DATA = "Data"
                val UPPER_TEXT = "Upper_Text"
                val MIDDLE_TEXT = "Middle_Text"
                val REMAINING_TIME = "RemainingTime"
                val STATUS_CODE = "StatusCode"
                val ERROR_LIST = "ErrorList"
                val SUCCESS_MESSAGE = "SuccessMessage"
            }
        }

        interface STATUS {
            companion object {
                val ERROR = "error"
                val OK = "OK"
                val ZERO = "0"
                val ONE = "1"
            }
        }
    }

    interface NOTIFICATION_MODE {
        companion object {
            val NO_NOTIFICATION = 0
            val FLIGHT = 1
            val PACKAGE = 2
        }
    }

    interface BAGGAGE {
        companion object {
            val YES = "Y"
            val NO = "X"
        }
    }
}