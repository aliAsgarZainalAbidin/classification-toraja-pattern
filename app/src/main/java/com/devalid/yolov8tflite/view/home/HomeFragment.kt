package com.devalid.yolov8tflite.view.home

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.AspectRatio
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.devalid.yolov8tflite.util.BoundingBox
import com.devalid.yolov8tflite.util.Constants.LABELS_PATH
import com.devalid.yolov8tflite.util.Constants.MODEL_PATH
import com.devalid.yolov8tflite.util.Detector
import com.devalid.yolov8tflite.util.showToast
import com.devalid.yolov8tflite.view.main.MainViewModel
import com.devalid.yolov8tflite.view.main.effect.MainScreenEffect
import com.devalid.yolov8tflite.view.main.event.MainScreenEvent
import com.devalid.yolov8tflite.view.result.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import yolov8tflite.R
import yolov8tflite.databinding.FragmentHomeBinding
import yolov8tflite.databinding.FragmentMainBinding
import java.io.File
import java.util.Date
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var latestTmpUri: Uri

    private val resultViewModel: ResultViewModel by activityViewModels<ResultViewModel>()
    private val mainViewModel: MainViewModel by activityViewModels<MainViewModel>()

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d(TAG, "pickImageGaleri: Selected Uri : $uri")
                mainViewModel.onEvent(MainScreenEvent.NavigateToResult(uri, resultViewModel))
            } else {
                Log.d(TAG, "pickImageGaleri: No Media Selected")
                mainViewModel.onEvent(MainScreenEvent.FailedToPickImage("No Media Selected"))
            }
        }

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                mainViewModel.onEvent(
                    MainScreenEvent.NavigateToResult(
                        latestTmpUri,
                        resultViewModel
                    )
                )
            } else {
                mainViewModel.onEvent(MainScreenEvent.FailedToPickImage("Failed to take a Picture"))
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (allPermissionsGranted()) {

        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }


        lifecycleScope.launch {
            mainViewModel.mainScreenEffect.collectLatest {
                when (it) {
                    is MainScreenEffect.OnSuccessPickImage -> {
                        findNavController().navigate(R.id.resultFragment)
                    }

                    is MainScreenEffect.OnFailedCamera -> {
                        requireActivity().showToast(it.message)
                    }

                    is MainScreenEffect.OnSuccessCamera -> {}
                    is MainScreenEffect.OnFailedPickImage -> {
                        requireActivity().showToast(it.message)
                    }
                }
            }
        }

        binding.apply {
            mbHomeCamera.setOnClickListener { takePhoto() }
            mbHomeGalery.setOnClickListener { pickImageGalery() }
        }
    }

    private fun takePhoto() {
        latestTmpUri = createImgUri()
        takePicture.launch(latestTmpUri)
    }

    private fun createImgUri(): Uri {
        val image = File(requireActivity().cacheDir, "${Date().time}-toraja.jpg")
        return FileProvider.getUriForFile(
            requireContext(),
            "com.devalid.yolov8tflite.provider",
            image
        )
    }

    private fun pickImageGalery() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        private const val TAG = "MainFragment"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA
        ).toTypedArray()
    }

}