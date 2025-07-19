package com.devalid.yolov8tflite.view.home.effect

import android.net.Uri

sealed interface HomeScreenEffect {
    data class OnFailedPickImage(val message: String) : HomeScreenEffect
    data object OnSuccessPickImage : HomeScreenEffect
    data class OnSuccessCamera(val uri: Uri) : HomeScreenEffect
    data class OnFailedCamera(val message: String) : HomeScreenEffect
}