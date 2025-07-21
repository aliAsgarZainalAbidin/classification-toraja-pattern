package com.devalid.yolov8tflite.view.components

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.devalid.yolov8tflite.view.result.ResultViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import yolov8tflite.databinding.BottomSheetPatternBinding

class BottomSheetDialogPattern : BottomSheetDialogFragment() {

    lateinit var _binding: BottomSheetPatternBinding
    val binding get() = _binding

    private val resultViewModel : ResultViewModel  by activityViewModels<ResultViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetPatternBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                resultViewModel.resultScreenState.collect { state ->
                    binding.apply {
                        Log.d(TAG, "onViewCreated: ${state.patterns}")
                        val adapter = ModalPatternAdapter()
                        adapter.setPatterns(state.patterns)
                        rvModalPattern.adapter = adapter
                        rvModalPattern.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                }
            }
        }

    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}