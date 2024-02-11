package com.prafull.contestifyme.features.profileFeature.domain.model.submissionsInfo

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("handle")
    val handle: String
)