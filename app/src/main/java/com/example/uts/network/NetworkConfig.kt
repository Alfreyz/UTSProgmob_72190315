package com.example.uts.network

import com.example.uts.model.ResponseAdd
import com.example.uts.model.ResponseMahasiswa
import com.example.uts.model.ResponseMahasiswaItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.google.gson.GsonBuilder

import com.google.gson.Gson
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class NetworkConfig {
    // set interceptor
    fun getInterceptor() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return okHttpClient
    }
    var gson = GsonBuilder()
        .setLenient()
        .create()
    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            //.baseUrl("https://jsonplaceholder.typicode.com/")
            .baseUrl("http://192.168.100.145/SlimMobilePetani/SlimMobileMahasiswa/public/")
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    fun getService() = getRetrofit().create(Users::class.java)
}
interface Users {

    @GET("api/progmob/matkul/{nim_progmob}")
    fun getMahasiswa(@Path("nim_progmob") nim_progmob : String): Call<List<ResponseMahasiswaItem>>

    @POST("api/progmob/matkul/create")
    fun addMahasiswa(@Body req : ResponseMahasiswaItem): Call<ResponseAdd>

    @PUT("api/progmob/matkul/update")
    fun updateMahasiswa(@Body req : ResponseMahasiswaItem): Call<ResponseAdd>

    @DELETE("api/progmob/matkul/delete/{kode}")
    fun deleteMahasiswa(@Path("kode") kode : String): Call<ResponseAdd>
}

