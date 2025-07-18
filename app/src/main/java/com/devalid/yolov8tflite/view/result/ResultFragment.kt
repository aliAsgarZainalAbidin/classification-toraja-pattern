package com.devalid.yolov8tflite.view.result

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.load
import coil3.request.placeholder
import com.devalid.yolov8tflite.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import yolov8tflite.R
import yolov8tflite.databinding.FragmentResultBinding
import javax.inject.Inject

@AndroidEntryPoint
class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val resultViewModel: ResultViewModel by activityViewModels<ResultViewModel>()

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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                resultViewModel.resultScreenState.collect { state ->
                    binding.apply {
                        sivResultContainer.load(state.uri){
//                            placeholder(R.drawable.sample)
//                            error(R.drawable.baseline_broken_image_24)
                        }
                    }
                    Log.d(TAG, "onViewCreated: URI -> ${state.uri}")

                    Log.d(TAG, "onViewCreated: URI 2 -> ${resultViewModel.resultScreenState.value.uri}")
                }
                

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}