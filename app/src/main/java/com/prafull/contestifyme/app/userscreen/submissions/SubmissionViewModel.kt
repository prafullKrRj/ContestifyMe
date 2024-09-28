package com.prafull.contestifyme.app.userscreen.submissions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.profileFeature.domain.model.UserSubmissions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class SubmissionViewModel(
    var submissions: String
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow<BaseClass<List<UserSubmissions>>>(BaseClass.Loading)
    val state = _state.asStateFlow()
    private val gson = Gson()

    init {
        decodeString()
    }

    private fun decodeString() {
        viewModelScope.launch {
            val listType = object : TypeToken<List<UserSubmissions>>() {}.type
            val item: List<UserSubmissions> = gson.fromJson(submissions, listType)
            _state.update {
                BaseClass.Success(item)
            }
        }
    }
}