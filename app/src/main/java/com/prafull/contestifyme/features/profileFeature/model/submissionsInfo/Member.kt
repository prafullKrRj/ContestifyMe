package com.prafull.contestifyme.features.profileFeature.model.submissionsInfo

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("handle")
    val handle: String
)