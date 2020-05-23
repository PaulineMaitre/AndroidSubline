package com.example.subline.data

object TrafficResult {

    data class Traffic (
        val result: Result
    )

    data class Result (
        val metros: List<Metro>,
        val rers: List<Metro>,
        val tramways: List<Metro>
    )

    data class Metro (
        val line: String,
        val slug: String,
        val title: String,
        val message: String
    )

}