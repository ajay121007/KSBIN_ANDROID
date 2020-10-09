package com.example.ks

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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

@BindingAdapter("bind:imageUrl",)
    fun loadImage(view: ImageView, imageUrl: String?) {
        Glide.with(view)
            .load(imageUrl)
            .placeholder(R.drawable.user_name_icon)
            .circleCrop()
            .into(view)


}
@BindingAdapter("bind:fullImage")
fun fullImage(view: ImageView, imageUrl: String?) {
    Glide.with(view)
        .load(imageUrl)
        .into(view)
}