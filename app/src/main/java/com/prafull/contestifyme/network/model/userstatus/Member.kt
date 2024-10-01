package com.prafull.contestifyme.network.model.userstatus

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("handle")
    val handle: String
)