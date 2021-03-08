package com.example.ks.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.ks.R
import com.example.ks.databinding.PickerLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PickerFragment : BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding=DataBindingUtil.inflate<PickerLayoutBinding>(inflater, R.layout.picker_layout,container,false)
        return binding.root
    }
}