package com.ank.androidmvvmarchitecture.data.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by CIS Dev 816 on 21/4/18.
 */
data class Destination(var ID: String) : Parcelable {

    var name: String? = null
    var isChecked = false

    var dst_destination: String? = null
    var dst_Name: String? = null
    var dst_Country_code: String? = null
    var dst_Name_Lcl: String? = null

    constructor(parcel: Parcel) : this(parcel.readString()) {
        name = parcel.readString()
        isChecked = parcel.readByte() != 0.toByte()
        dst_destination = parcel.readString()
        dst_Name = parcel.readString()
        dst_Country_code = parcel.readString()
        dst_Name_Lcl = parcel.readString()
    }
    //var Destination_id: String? = null
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ID)
        parcel.writeString(name)
        parcel.writeByte(if (isChecked) 1 else 0)
        parcel.writeString(dst_destination)
        parcel.writeString(dst_Name)
        parcel.writeString(dst_Country_code)
        parcel.writeString(dst_Name_Lcl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Destination> {
        override fun createFromParcel(parcel: Parcel): Destination {
            return Destination(parcel)
        }

        override fun newArray(size: Int): Array<Destination?> {
            return arrayOfNulls(size)
        }
    }


}