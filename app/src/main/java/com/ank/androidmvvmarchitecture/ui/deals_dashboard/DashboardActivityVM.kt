package androidmvvm.ui.deals_dashboard

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import androidmvvm.androidmvvmApplication
import androidmvvm.R
import androidmvvm.data.model.*
import androidmvvm.data.remote.api.BaseAPIService
import androidmvvm.data.remote.http.RestClient
import androidmvvm.utils.Constants
import androidmvvm.utils.Helper
import androidmvvm.utils.LogUtils
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by CIS Dev 816 on 4/4/18.
 */
class DashboardActivityVM @Inject constructor() : ViewModel() {

    private val TAG = DashboardActivityVM::class.java.getSimpleName()

    val tabs: MutableLiveData<List<Tab>>

    @Inject
    lateinit var context: Application

    var error = MutableLiveData<String>()
    var successMessage = MutableLiveData<String>()
    var isLoading = MutableLiveData<Boolean>()
    var isSuccess = MutableLiveData<Boolean>()
    var isNotificationSettingInserted = MutableLiveData<Boolean>()
    var confirmationNumber = MutableLiveData<String>()
    private var compositeDisposable = CompositeDisposable()

    init {
        tabs = MutableLiveData()
        loadTabs()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }

    private fun loadTabs() {
        val tabList = mutableListOf<Tab>()

        Constants.DealsHomeTabs.values().forEach {
            val tab = Tab(it.value, false, it.iconUnchecked, it.iconChecked)
            tabList.add(tab)
        }
        tabList[0].isChecked = true

        tabs.value = tabList
    }

    fun callDeleteReservePlaceForFlightAndHotel(passengerList: MutableList<Passenger>, flightDetail: FlightDetailsNew?,
                                                selectedRoomList: MutableList<Room>, hotel: Package?, deal: Deal?,
                                                resUniqueID: String?) {
        try {
            isLoading.value = true

            val observer = object : DisposableObserver<JsonObject>() {

                override fun onComplete() {
                    LogUtils.d(TAG, "onComplete")
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    LogUtils.d(TAG, "onError")
                    isLoading.value = false
                    error.value = androidmvvmApplication.context.getString(R.string.something_went_wrong)
                }

                override fun onNext(response: JsonObject) {
                    LogUtils.d(TAG, "onNext")
                    try {
                        isLoading.value = false
                        if (response.get(Constants.API.KEY.STATUS).asBoolean) {
                            confirmationNumber.value = response.get(Constants.API.KEY.DATA).asString
                            successMessage.value = response.get(Constants.API.KEY.MESSAGE).asString
                            isSuccess.value = true
                        } else if (!response.get(Constants.API.KEY.MESSAGE).isJsonNull) {
                            error.value = response.get(Constants.API.KEY.MESSAGE).asString
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        isLoading.value = false
                        error.value = androidmvvmApplication.context.getString(R.string.something_went_wrong)
                    }
                }
            }

            val jsonObject = JsonObject()

            val jaPassengers = JsonArray()
            for ((index, passenger) in passengerList.withIndex()) {
                if (index < passengerList.size.minus(1)) {
                    val joPassengerDetail = JsonObject()
                    joPassengerDetail.addProperty("IdNo", passenger.id)
                    joPassengerDetail.addProperty("DpcPxId", passenger.id)
                    joPassengerDetail.addProperty("Type", passenger.Type)
                    joPassengerDetail.addProperty("Age", "")
                    joPassengerDetail.addProperty("Lname", passenger.lastName)
                    joPassengerDetail.addProperty("Fname", passenger.firstName)
                    joPassengerDetail.addProperty("PxType", passenger.PxType)
                    joPassengerDetail.addProperty("BDate", passenger.dob)
                    joPassengerDetail.addProperty("Email", passengerList[passengerList.size.minus(1)].emailAddress)
                    joPassengerDetail.addProperty("Phone", passengerList[passengerList.size.minus(1)].phone)

                    jaPassengers.add(joPassengerDetail)
                }
            }
            jsonObject.add("PaxRec", jaPassengers)

            val jaFlights = JsonArray()

            val forthFlight = flightDetail?.forth?.get(0)

            val joForthFlight = JsonObject()
            joForthFlight.addProperty("FlyId", forthFlight?.FL_ID)
            joForthFlight.addProperty("FlyAirlineNumber", forthFlight?.FL_Flt_Number)
            joForthFlight.addProperty("City", forthFlight?.FL_From_Route)
            joForthFlight.addProperty("Airport1", "")
            joForthFlight.addProperty("DepDate", Helper.getDateForReservePlace(forthFlight?.FL_Dep_Hour))
            joForthFlight.addProperty("DepHour", "")
            joForthFlight.addProperty("TCity", forthFlight?.FL_To_Route)
            joForthFlight.addProperty("Airport2", "")
            joForthFlight.addProperty("ArrDate", Helper.getDateForReservePlace(forthFlight?.FL_Arrv_Hour))
            joForthFlight.addProperty("ArrHour", "")
            joForthFlight.addProperty("Class", forthFlight?.Class1)
            joForthFlight.addProperty("Route", "")
            jaFlights.add(joForthFlight)

            val backFlight = flightDetail?.back?.get(0)

            val joBackFlight = JsonObject()
            joBackFlight.addProperty("FlyId", backFlight?.FL_ID)
            joBackFlight.addProperty("FlyAirlineNumber", backFlight?.FL_Flt_Number)
            joBackFlight.addProperty("City", backFlight?.FL_From_Route)
            joBackFlight.addProperty("Airport1", "")
            joBackFlight.addProperty("DepDate", Helper.getDateForReservePlace(backFlight?.FL_Dep_Hour))
            joBackFlight.addProperty("DepHour", "")
            joBackFlight.addProperty("TCity", backFlight?.FL_To_Route)
            joBackFlight.addProperty("Airport2", "")
            joBackFlight.addProperty("ArrDate", Helper.getDateForReservePlace(backFlight?.FL_Arrv_Hour))
            joBackFlight.addProperty("ArrHour", "")
            joBackFlight.addProperty("Class", backFlight?.Class2)
            joBackFlight.addProperty("Route", "")
            jaFlights.add(joBackFlight)

            var intFilledPaxRec = 0
            for (room in selectedRoomList) {
                val intPaxCount = room.passengerCount

                val roomPaxRecList: MutableList<RoomPaxRec> = mutableListOf()
                var intPaxCounter = 0
                for ((index, passenger) in passengerList.withIndex()) {
                    if (index >= intFilledPaxRec && intPaxCounter < intPaxCount!!) {
                        val roomPaxRec = RoomPaxRec()
                        roomPaxRec.IdNo = passenger.id
                        roomPaxRecList.add(roomPaxRec)
                        intPaxCounter++
                        intFilledPaxRec++
                    }
                }
                room.roomPaxRecList = roomPaxRecList
            }

            val jaRooms = JsonArray()
            for (room in selectedRoomList) {
                val joRoom = JsonObject()
                val jaRoomPaxRecList = JsonArray()
                for (roomPaxRec in room.roomPaxRecList) {
                    val joRoomPaxRec = JsonObject()
                    joRoomPaxRec.addProperty("IdNo", roomPaxRec.IdNo)
                    jaRoomPaxRecList.add(joRoomPaxRec)
                }

                joRoom.add("RoomPaxRec", jaRoomPaxRecList)
                joRoom.addProperty("RoomClass", room.roomClass)
                joRoom.addProperty("RoomType", room.roomType)
                joRoom.addProperty("Accomodation", room.accomodation)
                joRoom.addProperty("Cc", "US")
            }

            val pnrReduce = hotel?.AppPrice?.toFloat()?.div(hotel.Price?.toFloat()!!)

            jsonObject.add("Flight", jaFlights)
            jsonObject.add("Rooms", jaRooms)
            jsonObject.addProperty("HotelCode", hotel?.Htl_Code)
            jsonObject.addProperty("HotelCity", "")
            jsonObject.addProperty("HotelName", "")
            jsonObject.addProperty("HotelFromDate", Helper.getDateForReservePlace(deal?.StartDate))
            jsonObject.addProperty("HotelToDate", Helper.getDateForReservePlace(deal?.EndDate))
            jsonObject.addProperty("HotelSupplier", hotel?.Supplier)
            jsonObject.addProperty("HotelBasis", hotel?.basic_fare)
            jsonObject.addProperty("PCK", deal?.PCK_ID)
            jsonObject.addProperty("ReservationUniqueID", resUniqueID)
            jsonObject.addProperty("PnrReduce", pnrReduce)
            jsonObject.addProperty("PnrDiscountCc", "US")

            compositeDisposable.add(RestClient.createRetrofitService(BaseAPIService::class.java, androidmvvmApplication.context)
                    .deleteReservePlaceForFlightAndHotel(jsonObject)
                    // Run on a background thread
                    .subscribeOn(Schedulers.io())
                    // Be notified on the main thread
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(observer))
        } catch (ex: Exception) {
            LogUtils.e(TAG, ex.message.toString())
            isLoading.value = false
            error.value = androidmvvmApplication.context.getString(R.string.something_went_wrong)
        }
    }

    fun callDeleteReservePlaceForFlight(passengerList: MutableList<Passenger>, flightDetail: FlightDetailsNew?,
                                        deal: Deal?, resUniqueID: String?) {
        try {
            isLoading.value = true

            val observer = object : DisposableObserver<JsonObject>() {

                override fun onComplete() {
                    LogUtils.d(TAG, "onComplete")
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    LogUtils.d(TAG, "onError")
                    isLoading.value = false
                    error.value = androidmvvmApplication.context.getString(R.string.something_went_wrong)
                }

                override fun onNext(response: JsonObject) {
                    LogUtils.d(TAG, "onNext")
                    try {
                        isLoading.value = false
                        if (response.get(Constants.API.KEY.STATUS).asBoolean) {
                            confirmationNumber.value = response.get(Constants.API.KEY.DATA).asString
                            successMessage.value = response.get(Constants.API.KEY.MESSAGE).asString
                            isSuccess.value = true
                        } else if (!response.get(Constants.API.KEY.MESSAGE).isJsonNull) {
                            error.value = response.get(Constants.API.KEY.MESSAGE).asString
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        isLoading.value = false
                        error.value = androidmvvmApplication.context.getString(R.string.something_went_wrong)
                    }
                }
            }

            val jsonObject = JsonObject()

            val jaPassengers = JsonArray()
            for ((index, passenger) in passengerList.withIndex()) {
                if (index < passengerList.size.minus(1)) {
                    val joPassengerDetail = JsonObject()
                    joPassengerDetail.addProperty("IdNo", passenger.id)
                    joPassengerDetail.addProperty("DpcPxId", passenger.id)
                    joPassengerDetail.addProperty("Type", passenger.Type)
                    joPassengerDetail.addProperty("Age", "")
                    joPassengerDetail.addProperty("Lname", passenger.lastName)
                    joPassengerDetail.addProperty("Fname", passenger.firstName)
                    joPassengerDetail.addProperty("PxType", passenger.PxType)
                    joPassengerDetail.addProperty("BDate", passenger.dob)
                    joPassengerDetail.addProperty("Email", passengerList[passengerList.size.minus(1)].emailAddress)
                    joPassengerDetail.addProperty("Phone", passengerList[passengerList.size.minus(1)].phone)

                    jaPassengers.add(joPassengerDetail)
                }
            }
            jsonObject.add("PaxRec", jaPassengers)

            val jaFlights = JsonArray()

            val forthFlight = flightDetail?.forth?.get(0)

            val joForthFlight = JsonObject()
            joForthFlight.addProperty("FlyId", forthFlight?.FL_ID)
            joForthFlight.addProperty("FlyAirlineNumber", forthFlight?.FL_Flt_Number)
            joForthFlight.addProperty("City", forthFlight?.FL_From_Route)
            joForthFlight.addProperty("Airport1", "")
            joForthFlight.addProperty("DepDate", Helper.getDateForReservePlace(forthFlight?.FL_Dep_Hour))
            joForthFlight.addProperty("DepHour", "")
            joForthFlight.addProperty("TCity", forthFlight?.FL_To_Route)
            joForthFlight.addProperty("Airport2", "")
            joForthFlight.addProperty("ArrDate", Helper.getDateForReservePlace(forthFlight?.FL_Arrv_Hour))
            joForthFlight.addProperty("ArrHour", "")
            joForthFlight.addProperty("Class", forthFlight?.Class1)
            joForthFlight.addProperty("Route", "")
            jaFlights.add(joForthFlight)

            val backFlight = flightDetail?.back?.get(0)

            val joBackFlight = JsonObject()
            joBackFlight.addProperty("FlyId", backFlight?.FL_ID)
            joBackFlight.addProperty("FlyAirlineNumber", backFlight?.FL_Flt_Number)
            joBackFlight.addProperty("City", backFlight?.FL_From_Route)
            joBackFlight.addProperty("Airport1", "")
            joBackFlight.addProperty("DepDate", Helper.getDateForReservePlace(backFlight?.FL_Dep_Hour))
            joBackFlight.addProperty("DepHour", "")
            joBackFlight.addProperty("TCity", backFlight?.FL_To_Route)
            joBackFlight.addProperty("Airport2", "")
            joBackFlight.addProperty("ArrDate", Helper.getDateForReservePlace(backFlight?.FL_Arrv_Hour))
            joBackFlight.addProperty("ArrHour", "")
            joBackFlight.addProperty("Class", backFlight?.Class2)
            joBackFlight.addProperty("Route", "")
            jaFlights.add(joBackFlight)

            //val pnrReduce = hotel?.AppPrice?.toFloat()?.div(hotel.Price?.toFloat()!!)

            jsonObject.add("Flight", jaFlights)
            jsonObject.addProperty("PCK", deal?.PCK_ID)
            jsonObject.addProperty("ReservationUniqueID", resUniqueID)
            jsonObject.addProperty("PnrReduce", "")
            jsonObject.addProperty("PnrDiscountCc", "US")

            compositeDisposable.add(RestClient.createRetrofitService(BaseAPIService::class.java, androidmvvmApplication.context)
                    .deleteReservePlaceForFlight(jsonObject)
                    // Run on a background thread
                    .subscribeOn(Schedulers.io())
                    // Be notified on the main thread
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(observer))
        } catch (ex: Exception) {
            LogUtils.e(TAG, ex.message.toString())
            isLoading.value = false
            error.value = androidmvvmApplication.context.getString(R.string.something_went_wrong)
        }
    }

    fun insertNotificationSetting(deviceID: String, token: String?, generalNotification: Boolean,
                                  packagesNotification: Boolean, flightsNotification: Boolean, active: Boolean) {
        try {
            //isLoading.value = true

            val observer = object : DisposableObserver<JsonObject>() {

                override fun onComplete() {
                    LogUtils.d(TAG, "onComplete")
                    //isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    LogUtils.d(TAG, "onError")
                    //isLoading.value = false
                    //error.value = androidmvvmApplication.context.getString(R.string.something_went_wrong)
                }

                override fun onNext(response: JsonObject) {
                    LogUtils.d(TAG, "onNext")
                    try {
                        //isLoading.value = false
                        if (response.get(Constants.API.KEY.STATUS).asBoolean) {
                            //successMessage.value = response.get(Constants.API.KEY.MESSAGE).asString
                            isNotificationSettingInserted.value = true
                        } else if (!response.get(Constants.API.KEY.MESSAGE).isJsonNull) {
                            //error.value = response.get(Constants.API.KEY.MESSAGE).asString
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        //isLoading.value = false
                        //error.value = androidmvvmApplication.context.getString(R.string.something_went_wrong)
                    }
                }
            }

            val jsonObject = JsonObject()
            jsonObject.addProperty("DeviceID", deviceID)
            jsonObject.addProperty("Token", token)
            jsonObject.addProperty("GeneralNotification", generalNotification)
            jsonObject.addProperty("PackagesNotification", packagesNotification)
            jsonObject.addProperty("FlightsNotification", flightsNotification)
            jsonObject.addProperty("Active", active)

            compositeDisposable.add(RestClient.createRetrofitService(BaseAPIService::class.java, androidmvvmApplication.context)
                    .insertNotificationSetting(jsonObject)
                    // Run on a background thread
                    .subscribeOn(Schedulers.io())
                    // Be notified on the main thread
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(observer))
        } catch (ex: Exception) {
            LogUtils.e(TAG, ex.message.toString())
            //isLoading.value = false
            //error.value = androidmvvmApplication.context.getString(R.string.something_went_wrong)
        }
    }

}