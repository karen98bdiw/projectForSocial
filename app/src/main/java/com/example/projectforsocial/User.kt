package com.example.projectforsocial

import java.io.Serializable

data class User(
    val id:String = "",
    var name:String = "",
    var surname:String = "",
    var mail:String = "",
    var password:String = ""
):Serializable