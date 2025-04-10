package com.example.oneshort.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.oneshort.network.ScheduleApi

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080"  // ⚠️ 에뮬레이터에서 localhost는 10.0.2.2로 접근해야 함

    val instance: retrofit2.Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val scheduleApi: ScheduleApi by lazy {
        instance.create(ScheduleApi::class.java)
    }

}