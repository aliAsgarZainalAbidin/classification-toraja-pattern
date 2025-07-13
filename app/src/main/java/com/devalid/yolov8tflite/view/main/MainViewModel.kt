package com.devalid.yolov8tflite.view.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devalid.yolov8tflite.view.main.effect.MainScreenEffect
import com.devalid.yolov8tflite.view.main.event.MainScreenEvent
import com.devalid.yolov8tflite.view.main.state.MainScreenState
import com.devalid.yolov8tflite.view.result.ResultViewModel
import com.devalid.yolov8tflite.view.result.event.ResultScreenEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//@HiltViewModel
class MainViewModel: ViewModel() {
    private var _mainScreenState: MutableStateFlow<MainScreenState> =
        MutableStateFlow(MainScreenState())
    val mainScreenState: StateFlow<MainScreenState> = _mainScreenState

    private var _mainScreenEffect: MutableSharedFlow<MainScreenEffect> = MutableSharedFlow()
    val mainScreenEffect: SharedFlow<MainScreenEffect> = _mainScreenEffect

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.NavigateToResult -> {
                onPickImageClicked(event.uri, event.resultViewModel)
            }

            is MainScreenEvent.OnCameraClicked -> {
                onCameraClicked()
            }

            is MainScreenEvent.FailedToPickImage -> {
                onFailedPickImage(event.msg)
            }

            is MainScreenEvent.FailedNavigateToCamera -> {
                onFailedNavigateToCamera(event.msg)
            }
        }
    }

    private fun onFailedPickImage(msg : String){
        viewModelScope.launch {
            _mainScreenEffect.emit(MainScreenEffect.OnFailedPickImage(msg))
        }
    }

    private fun onFailedNavigateToCamera(msg : String){
        viewModelScope.launch {
            _mainScreenEffect.emit(MainScreenEffect.OnFailedCamera(msg))
        }
    }

    private fun onCameraClicked() {}

    private fun onPickImageClicked(uri: Uri? = null, resultViewModel: ResultViewModel) {
        viewModelScope.launch {
            resultViewModel.onEvent(ResultScreenEvent.OnImageSelected(uri))
            _mainScreenEffect.emit(MainScreenEffect.OnSuccessPickImage)
        }
    }
}