package com.devalid.yolov8tflite.view.result.state

import android.net.Uri
import com.devalid.yolov8tflite.util.PatternType

data class ResultScreenState(
    val isLoading: Boolean = false,
    val uri : Uri? = null,
    val patterns : List<PatternType> = emptyList()
)
