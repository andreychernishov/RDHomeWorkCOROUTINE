package com.example.superherocoroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel: ViewModel() {
    private val _uiState = MutableLiveData<UIState>(UIState.Empty)
    val uiState: LiveData<UIState> = _uiState

    private val repo
        get() = MyApplication.getApp().repo
    fun getData() {
        _uiState.value = UIState.Processing
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = repo.getCurretnMeme()
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



