package com.devalid.yolov8tflite.view.result.effect

sealed interface ResultScreenEffect {
    data object OnBackPressed : ResultScreenEffect
}