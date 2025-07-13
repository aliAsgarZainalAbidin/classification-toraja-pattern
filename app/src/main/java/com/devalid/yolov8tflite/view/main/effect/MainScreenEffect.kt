package com.devalid.yolov8tflite.view.main.effect

import android.net.Uri

sealed interface MainScreenEffect {
    data class OnFailedPickImage(val message: String) : MainScreenEffect
    data object OnSuccessPickImage : MainScreenEffect
    data class OnSuccessCamera(val uri: Uri) : MainScreenEffect
    data class OnFailedCamera(val message: String) : MainScreenEffect
}