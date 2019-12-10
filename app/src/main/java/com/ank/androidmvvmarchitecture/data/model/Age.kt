package androidmvvm.data.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by CIS Dev 816 on 31/10/18.
 */
data class Age(var ID: String = "") : Parcelable {

    var days: Int = 0
    var months: Int = 0
    var years: Int = 0

    constructor(days: Int, months: Int, years: Int) : this() {
        this.days = days
        this.months = months
        this.years = years
    }

    constructor(parcel: Parcel) : this(parcel.readString()) {
        days = parcel.readInt()
        months = parcel.readInt()
        years = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ID)
        parcel.writeInt(days)
        parcel.writeInt(months)
        parcel.writeInt(years)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Age> {
        override fun createFromParcel(parcel: Parcel): Age {
            return Age(parcel)
        }

        override fun newArray(size: Int): Array<Age?> {
            return arrayOfNulls(size)
        }
    }
}