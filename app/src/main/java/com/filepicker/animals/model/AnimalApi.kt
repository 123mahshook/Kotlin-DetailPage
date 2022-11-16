package com.filepicker.animals.model

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimalApi {

    @GET("users?page=2")
    fun getApiKey(): Single<Page>

 /*   @FormUrlEncoded
    @POST("getAnimals")
    fun getAnimals(@Field("key") key: String):Single<List<Animal>>*/
}