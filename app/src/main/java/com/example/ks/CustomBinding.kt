package com.example.ks

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.ks.models.DashBoardResponse
import com.example.ks.utils.DateTimeUtils
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
@BindingAdapter("bind:setCustomProgress")
fun ProgressBar.setCustomProgress( model:DashBoardResponse.Data.Policy) {
    this.progress=DateTimeUtils.getProgressDuration(model.policyFrom,model.policyTo)
}

@BindingAdapter("bind:setDaysLeft")
fun TextView.setDaysLeft( model:DashBoardResponse.Data.Policy) {
    this.text=DateTimeUtils.getDaysLeft(model.policyFrom,model.policyTo)+" Days"
}
@BindingAdapter("bind:setDaysStatus")
fun TextView.setDaysStatus( model:DashBoardResponse.Data.Policy) {
    this.text=if(DateTimeUtils.getDaysLeft(model.policyFrom,model.policyTo)=="0") "Completed" else "Pending"
}
@BindingAdapter("bind:setCountVisibility")
fun TextView.setCountVisibility( count:Int) {
    this.visibility=if(count==0)View.GONE else View.VISIBLE
}

@BindingAdapter("bind:setQuery")
fun SearchView.setQuery( search:MutableLiveData<String>) {
    this.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(p0: String?): Boolean {
           search.postValue(p0)
            return true
        }

        override fun onQueryTextChange(p0: String?): Boolean {
            search.postValue(p0)
            return true
        }

    })
}
fun String.getProgressDuration(d2: String?): Int {
    val totalDays= DateTimeUtils.getDifferenceDays(this, d2)
    val daysGone= DateTimeUtils.getDifferenceFroMCurrentDate(this)
    return ((daysGone.toDouble()/totalDays)*100).toInt()
}

@BindingAdapter("bind:expiryText")
fun TextView.expiryText(data:String?) {
    if(data?.length==2){
        this.text= "$data/"
    }
}