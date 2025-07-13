package com.devalid.yolov8tflite.view.main.event

import android.net.Uri
import com.devalid.yolov8tflite.view.result.ResultViewModel

sealed interface MainScreenEvent {
    data class NavigateToResult(val uri: Uri? = null, val resultViewModel: ResultViewModel) : MainScreenEvent
    data class OnCameraClicked(val uri : Uri? = null) : MainScreenEvent
    data class FailedToPickImage(val msg : String) : MainScreenEvent
    data class FailedNavigateToCamera(val msg : String) : MainScreenEvent
}