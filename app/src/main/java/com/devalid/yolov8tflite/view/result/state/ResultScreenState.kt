package com.devalid.yolov8tflite.view.result.state

import android.net.Uri

data class ResultScreenState(
    val isLoading: Boolean = false,
    val uri : Uri? = null
)
