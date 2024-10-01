package com.prafull.contestifyme.app.problemsFeature.domain.model.acmsguru

import com.google.gson.annotations.SerializedName

data class AcmsguruDto(
    val result: Result,
    @SerializedName("status")
    val status: String
)