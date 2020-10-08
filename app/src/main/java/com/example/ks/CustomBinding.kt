package com.example.ks

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
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

@BindingAdapter("bind:imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view)
        .load(imageUrl).circleCrop()
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Log.i(this.javaClass.simpleName, "Glide Failed with ${e?.localizedMessage}: ")
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                Log.i(this.javaClass.simpleName, "Glide Loaded ")
                return true
            }

        })
        .into(view)
}