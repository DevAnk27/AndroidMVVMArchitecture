package com.ank.androidmvvmarchitecture.data.remote.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by CIS Dev 816 on 3/9/18.
 */
interface EAuthAPIService {

    @POST("/")
    fun register(@Body jsonObject: JsonObject): Observable<JsonObject>

    @POST("")
    fun login(@Body jsonObject: JsonObject): Observable<JsonObject>

    @POST("/")
    fun changePassword(@Body jsonObject: JsonObject): Observable<JsonObject>

    @POST("/")
    fun updateCustomerInfo(@Body jsonObject: JsonObject): Observable<JsonObject>

    @POST("//")
    fun addAddress(@Body jsonArray: JsonArray): Observable<JsonObject>

    @GET("/")
    fun getAddress(): Observable<JsonObject>

    @POST("///{ID}")
    fun updateAddress(@Path("ID") id: Int, @Body jsonArray: JsonArray): Observable<JsonObject>
}