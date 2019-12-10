package com.ank.androidmvvmarchitecture.utils

import android.text.TextUtils
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


/**
 * Created by CIS Dev 816 on 14/2/18.
 */
class ValidationUtil() {

    companion object {

        fun isValidFirstName(firstname: String?): Boolean {
            val p = Pattern.compile("^[a-zA-Z]{2,30}$")
            if (firstname == null) {
                return false
            } else {
                val m = p.matcher(firstname)
                return m.matches()
            }
        }

        fun isValidLastName(lastName: String?): Boolean {
            val p = Pattern.compile("^[a-zA-Z]{2,30}$")
            if (lastName == null) {
                return false
            } else {
                val m = p.matcher(lastName)
                return m.matches()
            }
        }

        fun isValidEmail(email: String?): Boolean {
            return if (email == null) {
                false
            } else {
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        }

        fun isValidPassword(password: String?): Boolean {
            val p = Pattern.compile("((?!\\s)\\A)(\\s|(?<!\\s)\\S){8,20}\\Z")
            if (password == null) {
                return false
            } else {
                val m = p.matcher(password)
                return m.matches()
            }
        }

        fun isValidConfirmPasswrod(confirmPassword: String, password: String): Boolean {
            return confirmPassword == password
        }

        fun isValidAddress(address: String?): Boolean {
            return if (address == null || address == "") {
                false
            } else {
                android.util.Patterns.EMAIL_ADDRESS.matcher(address).matches()
            }
        }

        fun isValidPincode(pincode: String?): Boolean {
            if (pincode == null) {
                return false
            } else {
                val PINCODE_PATTERN = "^[0-9]{6}$"
                val pattern = Pattern.compile(PINCODE_PATTERN)
                val matcher = pattern.matcher(pincode)
                return matcher.matches()
            }
        }

        fun isValidMobile(mobile: String?): Boolean {
            val p = Pattern.compile("^[789]\\d{9,9}$")
            if (mobile == null) {
                return false
            } else {
                val m = p.matcher(mobile)
                return m.matches()
            }
        }

        fun isValidAge(age: String?): Boolean {
            val p = Pattern.compile("^[1-9]{1,3}$")
            if (age == null || age == "") {
                return false
            } else {
                val m = p.matcher(age)
                return m.matches()
            }
        }

        fun getDate(milliSeconds: Long, dateFormat: String): String {
            /*Sample for data formatter "dd/MM/yyyy hh:mm:ss.SSS"*/
            // Create a DateFormatter object for displaying date in specified format.
            val formatter = SimpleDateFormat(dateFormat)

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            val calendar = Calendar.getInstance()
            calendar.setTimeInMillis(milliSeconds)
            return formatter.format(calendar.getTime())
        }

        fun isEmpty(chars: CharSequence): Boolean {
            return TextUtils.isEmpty(chars)
        }

        fun isEmptyEdittext(editText: EditText): Boolean {
            return TextUtils.isEmpty(editText.text.toString().trim { it <= ' ' })
        }
    }
}