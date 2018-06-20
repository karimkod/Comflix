package com.example.slash.comflix.entities

/**
 * Created by Slash on 13/04/2018.
 */
data class Serie(
        val first_air_date:String,
        val name:String,
        val poster_path:String,
        val popularity:Float,
        val id:Int,
        val backdrop:String?,
        val vote_average:Double,
        val overview:String
)