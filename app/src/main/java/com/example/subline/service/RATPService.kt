package com.example.subline.service

import retrofit2.http.GET
import retrofit2.http.Path

interface RATPService {
    //REQ1 get all metro lines
    @GET("/v4/lines/{type}")
    suspend fun getAllLines(@Path("type") type: String) : GetLinesResult

    //REQ2 get infos for one metro line
    @GET("/v4/lines/{type}/{code}")
    suspend fun getLineInfo(@Path("type") type: String, @Path ("code") code: String) : GetLineInfoResult

    //REQ3 get schedules for one line in a given station
    @GET("/v4/schedules/{type}/{code}/{station}/{way}")
    suspend fun getSchedules(@Path("type") type: String, @Path("code") code: String, @Path("station") station: String, @Path("way") way: String) : GetSchedulesResult

    //REQ4 get list of stations for one metro line
    @GET("/v4/stations/{type}/{code}")
    suspend fun getStations(@Path("type") type: String, @Path ("code") code: String) : GetStationsResult

    //REQ5 get traffic info for one metro line
    @GET("/v4/traffic/{type}/{code}")
    suspend fun getTrafficInfo(@Path("type") type: String, @Path ("code") code: String) : GetTrafficInfoResult

}

data class GetLinesResult(val result: Metros = Metros())
data class Metros(val metros : List<Metro> = emptyList())
data class Metro(val name: String = "", val directions: String = "")


data class GetLineInfoResult(val result: Line = Line())
data class Line(val code: String = "", val name: String = "", val directions: String = "", val id: String = "")

data class GetSchedulesResult(val result: Schedules = Schedules())
data class Schedules(val schedules : List<Schedule> = emptyList())
data class Schedule(val message: String = "", val destination: String = "")

data class GetStationsResult(val result: Stations = Stations())
data class Stations(val stations : List<Station> = emptyList())
data class Station(val name: String = "", val slug: String = "")

data class GetTrafficInfoResult(val result: TrafficInfo = TrafficInfo())
data class TrafficInfo(val line: String = "", val slug: String = "", val title: String = "", val message: String = "")