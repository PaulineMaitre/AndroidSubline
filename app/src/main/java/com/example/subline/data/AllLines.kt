package com.example.subline.data

object AllLines {

    data class Lines(val allLines: MutableList<Line> = ArrayList()) {
        fun addLine(line: Line) {
            allLines.add(line)
        }
    }

    data class Line(val lineCode: String = "", val lineName: String = "", val directions: String = "",
                    val listStations: MutableList<Station> = ArrayList(), val transportType: String = "") {
    }

    data class Station(val name: String = "", val slug: String = "")
}
