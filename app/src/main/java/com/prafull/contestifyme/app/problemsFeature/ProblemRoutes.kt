package com.prafull.contestifyme.app.problemsFeature

import kotlinx.serialization.Serializable

interface ProblemRoutes {

    @Serializable
    data object ProblemsMain : ProblemRoutes

    @Serializable
    data object AcmsGuru : ProblemRoutes


}