package com.stameni.com.moviebrowser.data.retrofit.schemas

import com.google.gson.annotations.SerializedName

abstract class BaseSchema{
    @SerializedName("status_code")
    val statusCode: String = ""
    @SerializedName("status_message")
    val statusMessage: String = ""
}
