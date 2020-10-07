package com.example.ks

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by skycap.
 */

    @BindingAdapter("setError")
    fun TextInputLayout.setCustomError(error: String?){
        this.error=error
    }

@BindingAdapter("setVisibility")
fun View.setVisibility(visibility: Boolean?){
    if(visibility==true)this.visibility=View.VISIBLE
    else View.GONE
}