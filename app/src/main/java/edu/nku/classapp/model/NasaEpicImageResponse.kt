package edu.nku.classapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class NasaEpicImageResponse : ArrayList<NasaEpicImageResponse.EpicImageItem>() {
    @JsonClass(generateAdapter = true)
    data class EpicImageItem(
        @Json(name = "identifier")
        val identifier: String,
        @Json(name = "caption")
        val caption: String,
        @Json(name = "image")
        val image: String,
        @Json(name = "version")
        val version: String,
        @Json(name = "date")
        val date: String,
        @Json(name = "centroid_coordinates")
        val centroidCoordinates: Coordinates,
        @Json(name = "dscovr_j2000_position")
        val dscovrPosition: Position,
        @Json(name = "lunar_j2000_position")
        val lunarPosition: Position,
        @Json(name = "sun_j2000_position")
        val sunPosition: Position,
        @Json(name = "attitude_quaternions")
        val attitudeQuaternions: Quaternions
    ) {
        @JsonClass(generateAdapter = true)
        data class Coordinates(
            @Json(name = "lat")
            val lat: Double,
            @Json(name = "lon")
            val lon: Double
        )

        @JsonClass(generateAdapter = true)
        data class Position(
            @Json(name = "x")
            val x: Double,
            @Json(name = "y")
            val y: Double,
            @Json(name = "z")
            val z: Double
        )

        @JsonClass(generateAdapter = true)
        data class Quaternions(
            @Json(name = "q0")
            val q0: Double,
            @Json(name = "q1")
            val q1: Double,
            @Json(name = "q2")
            val q2: Double,
            @Json(name = "q3")
            val q3: Double
        )
    }
}