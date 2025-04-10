package com.example.oneshort.network

import com.example.oneshort.data.Schedule
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApi {

    @GET("/schedules/today")
    fun getTodaySchedules(
        @Query("userId") userId: Long
    ): Call<List<Schedule>>
}
