package com.devalid.yolov8tflite.view.result.event

import android.net.Uri

sealed interface ResultScreenEvent {
    data object OnBackPress : ResultScreenEvent
    data class OnImageSelected(val uri: Uri?) : ResultScreenEvent
}