package com.ank.androidmvvmarchitecture.data.remote.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dagger.Module
import io.reactivex.Observable
import retrofit2.http.*


/**
 * Created by CIS Dev 816 on 9/2/18.
 */
@Module
interface BaseAPIService {

    @GET("")
    fun getDeals(): Observable<JsonObject>

    @GET("")
    fun getOnlyDeals(): Observable<JsonObject>

    @GET("")
    fun getDestinations(): Observable<JsonObject>

    @GET("")
    fun getGeneralDeals(): Observable<JsonObject>

    @GET("")
    fun getFlightDetailsByPCK_Id(@Query("PCK_Id") pckID: String?): Observable<JsonObject>

    @GET("")
    fun getFlightDetailsForPackageOnlyByPCK_Id(@Query("PCK_Id") pckID: String?): Observable<JsonObject>

    @GET("")
    fun getPackageDetailsByPCK_Id(@Query("PCK_Id") pckID: String, @Query("La_ID") La_ID: String): Observable<JsonObject>

    @GET("")
    fun getItineraryDetailsByPCK_Id(@Query("PCK_Id") pckID: String): Observable<JsonObject>

    @POST("")
    fun callCreditXML(@Body jsonObject: JsonObject): Observable<JsonObject>

    @GET("/")
    fun getCounterText(): Observable<JsonObject>

    @GET("/")
    fun getHotelImagesbyLA_Id(@Query("LA_Id") LA_Id: String): Observable<JsonObject>

    @GET("/")
    fun getFlightImagesbyLA_Id(@Query("Dst_Destination") Dst_Destination: String): Observable<JsonObject>

    @FormUrlEncoded
    @POST("/")
    fun reservePlaceForFlights(@Field("FlyIdGo") flyIdGo: String?, @Field("FlyClassGo") flyClassGo: String?,
                               @Field("FlySeatGo") flySeatGo: String, @Field("FlyIdReturn") flyIdReturn: String?,
                               @Field("FlyClassReturn") flyClassReturn: String?, @Field("FlySeatReturn") flySeatReturn: String): Observable<JsonObject>

    @FormUrlEncoded
    @POST("/")
    fun reservePlaceForFlightAndHotel(@Field("FlyIdGo") flyIdGo: String, @Field("FlyClassGo") flyClassGo: String?,
                                      @Field("FlySeatGo") flySeatGo: String, @Field("FlyIdReturn") flyIdReturn: String,
                                      @Field("FlyClassReturn") flyClassReturn: String?, @Field("FlySeatReturn") flySeatReturn: String,
                                      @Field("LaId") laId: String?, @Field("Supplier") supplier: String?,
                                      @Field("HotelClass") hotelClass: String?, @Field("CheckInDate") checkInDate: String?,
                                      @Field("CheckOutDate") checkOutDate: String?, @Field("Room") room: JsonArray?): Observable<JsonObject>

    @POST("/")
    fun reservePlaceForFlightAndHotel(@Body jsonObject: JsonObject): Observable<JsonObject>

    @POST("/")
    fun responseReservationForFlightAndHotel(@Body jsonObject: JsonObject): Observable<JsonObject>

    @POST("/")
    fun deleteReservePlaceForFlightAndHotel(@Body jsonObject: JsonObject): Observable<JsonObject>

    @POST("/")
    fun deleteReservePlaceForFlight(@Body jsonObject: JsonObject): Observable<JsonObject>

    @POST("/")
    fun insertNotificationSetting(@Body jsonObject: JsonObject): Observable<JsonObject>

    @POST("/")
    fun updateNotificationSetting(@Body jsonObject: JsonObject): Observable<JsonObject>

    @GET("/")
    fun getHotelDetailsByLA_ID_Mobile(@Query("La_ID") LA_Id: String): Observable<JsonObject>

    @GET("/")
    fun getCounterToStart(): Observable<JsonObject>
}