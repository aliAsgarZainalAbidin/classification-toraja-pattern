package com.devalid.yolov8tflite.view.home.event

import android.net.Uri
import com.devalid.yolov8tflite.view.result.ResultViewModel

sealed interface HomeScreenEvent {
    data class NavigateToResult(val uri: Uri? = null, val resultViewModel: ResultViewModel) : HomeScreenEvent
    data class OnCameraClicked(val uri : Uri? = null) : HomeScreenEvent
    data class FailedToPickImage(val msg : String) : HomeScreenEvent
    data class FailedNavigateToCamera(val msg : String) : HomeScreenEvent
}