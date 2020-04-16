package com.example.subline.service

import retrofit2.http.GET
import retrofit2.http.Path

interface RATPService {
    @GET("/v4/lines/{type}/{code}")
    suspend fun getAllLines(@Path("type") type: String, @Path ("code") code: String) : GetLinesResult


    @GET("/v4/schedules/{type}/{code}/{station}/{way}")
    suspend fun getSchedules(@Path("type") type: String, @Path("code") code: String, @Path("station") station: String, @Path("way") way: String) : GetSchedulesResult

//suspend fun getDestinations(@Query("result") type : String, code : Int) : GetDestinationsResult

}


data class GetLinesResult(val result: Line = Line())
//data class Transports(val type: Type = Type())
data class Line(val code: String = "", val name: String = "", val directions: String = "", val id: String = "")

data class GetSchedulesResult(val result: Schedules = Schedules())
data class Schedules(val schedules : List<Schedule> = emptyList())
data class Schedule(val code: String = "", val message: String = "", val destination: String = "")


//data class GetDestinationsResult(val result: List<Destination> = emptyList())
//data class Destination(val name: String = "", val way: String = "")