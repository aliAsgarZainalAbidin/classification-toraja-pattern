package com.devalid.yolov8tflite.view.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.devalid.yolov8tflite.util.PatternType
import yolov8tflite.databinding.ItemPatternsBinding

class ModalPatternAdapter : RecyclerView.Adapter<ModalPatternAdapter.PatternViewHolder>() {
    private var patterns: List<PatternType> = emptyList()

    class PatternViewHolder(val binding: ItemPatternsBinding) :
        ViewHolder(binding.root) {
        fun bind(pattern: PatternType) {
            binding.apply {
                mtvItemTitle.text = pattern.name
                mtvItemDesc.text = pattern.desc
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatternViewHolder {
        return PatternViewHolder(
            ItemPatternsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setPatterns(patterns: List<PatternType>) {
        this.patterns = patterns
    }

    override fun getItemCount(): Int {
        return this.patterns.size
    }

    override fun onBindViewHolder(holder: PatternViewHolder, position: Int) {
        holder.bind(patterns[position])
    }
}