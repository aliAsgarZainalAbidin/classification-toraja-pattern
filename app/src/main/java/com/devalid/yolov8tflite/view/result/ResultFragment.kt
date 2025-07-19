package com.devalid.yolov8tflite.view.result

import android.annotation.SuppressLint
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.camera.core.AspectRatio
import androidx.camera.core.ImageAnalysis
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.Bitmap
import coil3.load
import coil3.request.placeholder
import com.devalid.yolov8tflite.util.BoundingBox
import com.devalid.yolov8tflite.util.Constants.LABELS_PATH
import com.devalid.yolov8tflite.util.Constants.MODEL_PATH
import com.devalid.yolov8tflite.util.Detector
import com.devalid.yolov8tflite.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.support.image.TensorImage
import yolov8tflite.R
import yolov8tflite.databinding.FragmentResultBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import javax.inject.Inject

@AndroidEntryPoint
class ResultFragment : Fragment(), Detector.DetectorListener {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val resultViewModel: ResultViewModel by activityViewModels<ResultViewModel>()

    private lateinit var backgroundExecutor: ExecutorService
    private lateinit var objectDetectorHelper: Detector

    private var imageAnalyzer: ImageAnalysis? = null

    companion object {
        private val TAG = "ResultFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundExecutor = Executors.newSingleThreadExecutor()


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                resultViewModel.resultScreenState.collect { state ->
                    state.uri?.let { runDetectionOnImage(it) }
                }
            }
        }
    }

    private fun runDetectionOnImage(uri: Uri) {
        binding.sivResultContainer.load(uri)

        backgroundExecutor.execute {
            objectDetectorHelper = Detector(requireContext(), MODEL_PATH, LABELS_PATH, this) {
                Log.d(TAG, "onViewCreated: $it")
            }

            uriToBitmap(uri)?.let {
                objectDetectorHelper.detect(it)
                Log.d(TAG, "runDetectionOnImage: $it")
            }
        }

    }

    private fun uriToBitmap(uri: Uri): android.graphics.Bitmap? {

        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(
                    requireActivity().contentResolver,
                    uri
                )
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver,
                    uri
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }?.copy(android.graphics.Bitmap.Config.ARGB_8888, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onEmptyDetect() {
        requireActivity().runOnUiThread {
            binding.overlay.clear()
        }
    }

    override fun onDetect(boundingBoxes: List<BoundingBox>, inferenceTime: Long) {
        requireActivity().runOnUiThread {
            binding.overlay.apply {
                setResults(boundingBoxes)
                invalidate()
            }
        }
    }
}