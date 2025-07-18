package com.devalid.yolov8tflite.view.result

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devalid.yolov8tflite.view.result.effect.ResultScreenEffect
import com.devalid.yolov8tflite.view.result.event.ResultScreenEvent
import com.devalid.yolov8tflite.view.result.state.ResultScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ResultViewModel @Inject constructor() : ViewModel() {
    private var _resultScreenState: MutableStateFlow<ResultScreenState> =
        MutableStateFlow(ResultScreenState())
    val resultScreenState: StateFlow<ResultScreenState> = _resultScreenState

    private var _resultScreenEffect: MutableSharedFlow<ResultScreenEffect> = MutableSharedFlow()
    val resultScreenEffect: SharedFlow<ResultScreenEffect> = _resultScreenEffect

    fun onEvent(event: ResultScreenEvent) {
        when (event) {
            is ResultScreenEvent.OnBackPress -> {
                onBackPress()
            }

            is ResultScreenEvent.OnImageSelected -> {
                onImageSelected(event.uri)
            }
        }
    }

    private fun onBackPress() {
        viewModelScope.launch {
            _resultScreenEffect.emit(ResultScreenEffect.OnBackPressed)
        }
    }

    private fun onImageSelected(uri: Uri?) {
        viewModelScope.launch {
            _resultScreenState.update { it.copy(uri = uri) }
            Log.d(TAG, "onImageSelected: ${resultScreenState.value.uri}")
        }
    }

    companion object {
        private const val TAG = "ResultViewModel"
    }
}