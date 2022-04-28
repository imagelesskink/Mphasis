package com.example.mphasis.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mphasis.model.School
import com.example.mphasis.networking.ResponseState
import com.example.mphasis.networking.SchoolRepository
import com.example.mphasis.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolViewModel @Inject constructor(
    private val schoolRepo: SchoolRepository,
    private val ioDispatcher: CoroutineDispatcher
): BaseViewModel() {

    //retrieve school list
    private val _schools: MutableLiveData<ResponseState> = MutableLiveData()
    val schools: LiveData<ResponseState> get() = _schools

    // get SAT scores
    private val _scores: MutableLiveData<ResponseState> = MutableLiveData()
    val satScore: LiveData<ResponseState> get() = _scores

    var school : School? = null


    // get the list of schools from the server
    fun getAll() {
        viewModelSafeScope.launch(ioDispatcher) {
            schoolRepo.getAll().collect() {
                _schools.postValue(it)
            }
        }
    }

    // get the SAT scores for the selected school
    fun getSat(schoolDbn: String) {
        viewModelSafeScope.launch {
            schoolRepo.getSat(schoolDbn).collect() {
                _scores.postValue(it)
            }
        }
    }

    // Resets the score data
    fun resetScore() {
        _scores.value = ResponseState.Loading
    }
}