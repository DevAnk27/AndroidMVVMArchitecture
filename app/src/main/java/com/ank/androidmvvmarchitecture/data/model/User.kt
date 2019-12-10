package androidmvvm.data.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by CIS Dev 816 on 26/3/18.
 */
data class User(var CustomerId: Int) : Parcelable {

    var FirstName: String? = null
    var LastName: String? = null
    var Email: String? = null
    var Username: String? = null
    var StreetAddress: String? = null
    var StreetAddress2: String? = null
    var City: String? = null
    var Phone: String? = null
    var CountryId: String? = null
    var StateProvinceId: String? = null
    var Token: String? = null
    var SuccessMessage: String? = null
    var StatusCode: String? = null
    var ErrorList: MutableList<String>? = null
    var Password: String? = null

    constructor(parcel: Parcel) : this(parcel.readInt()) {
        FirstName = parcel.readString()
        LastName = parcel.readString()
        Email = parcel.readString()
        Username = parcel.readString()
        StreetAddress = parcel.readString()
        StreetAddress2 = parcel.readString()
        City = parcel.readString()
        Phone = parcel.readString()
        CountryId = parcel.readString()
        StateProvinceId = parcel.readString()
        Token = parcel.readString()
        SuccessMessage = parcel.readString()
        StatusCode = parcel.readString()
        Password = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(CustomerId)
        parcel.writeString(FirstName)
        parcel.writeString(LastName)
        parcel.writeString(Email)
        parcel.writeString(Username)
        parcel.writeString(StreetAddress)
        parcel.writeString(StreetAddress2)
        parcel.writeString(City)
        parcel.writeString(Phone)
        parcel.writeString(CountryId)
        parcel.writeString(StateProvinceId)
        parcel.writeString(Token)
        parcel.writeString(SuccessMessage)
        parcel.writeString(StatusCode)
        parcel.writeString(Password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }


}