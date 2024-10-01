package com.prafull.contestifyme.app.userscreen

import com.prafull.contestifyme.app.userscreen.submissions.SubmissionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val submissionModule = module {
    viewModel {
        SubmissionViewModel()
    }
}