package com.ank.androidmvvmarchitecture.utils

import androidmvvm.data.model.Age
import java.util.*


/**
 * Created by CIS Dev 816 on 31/10/18.
 */
class AgeCalculator {

    companion object {
        fun calculateAge(birthDate: Long): Age {
            var years = 0
            var months = 0
            var days = 0

            //create calendar object for birth day
            val birthDay = Calendar.getInstance()
            birthDay.setTimeInMillis(birthDate)

            //create calendar object for current day
            val currentTime = System.currentTimeMillis()
            val now = Calendar.getInstance()
            now.setTimeInMillis(currentTime)

            //Get difference between years
            years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR)
            val currMonth = now.get(Calendar.MONTH) + 1
            val birthMonth = birthDay.get(Calendar.MONTH) + 1

            //Get difference between months
            months = currMonth - birthMonth

            //if month difference is in negative then reduce years by one
            //and calculate the number of months.
            if (months < 0) {
                years--
                months = 12 - birthMonth + currMonth
                if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                    months--
            } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                years--
                months = 11
            }

            //Calculate the days
            if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
                days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE)
            else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                val today = now.get(Calendar.DAY_OF_MONTH)
                now.add(Calendar.MONTH, -1)
                days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today
            } else {
                days = 0
                if (months == 12) {
                    years++
                    months = 0
                }
            }
            //Create new Age object
            return Age(days, months, years)
        }
    }
}