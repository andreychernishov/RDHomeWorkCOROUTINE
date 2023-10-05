package com.example.superherocoroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(var repo: Repository): ViewModel() {
    private val _uiState = MutableLiveData<UIState>(UIState.Empty)
    val uiState: LiveData<UIState> = _uiState

    fun getData() {
        _uiState.value = UIState.Processing
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = repo.getCurrentMeme()
                    withContext(Dispatchers.Main) {
                        _uiState.postValue(
                            UIState.Result(response))
                    }
                }catch (e: Throwable){
                    withContext(Dispatchers.Main){
                        _uiState.postValue(UIState.Error(e.localizedMessage))
                    }
                }
            }
        }
    }
    sealed class UIState {
        object Empty:UIState()
        object Processing:UIState()
        class Result(val mem: MutableList<JsData>):UIState()
        class Error(val description: String) : UIState()
    }
}



