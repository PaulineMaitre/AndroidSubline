package com.example.subline.data

object TrafficResult {

    data class Traffic (
        val result: Result
    )

    data class Result (
        val metros: List<Transport>,
        val rers: List<Transport>,
        val tramways: List<Transport>
    )

    data class Transport (
        val type : String,
        val line: String,
        val slug: String,
        val title: String,
        val message: String,
        val picto : Int
    )

}