package com.example.ks.common

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ks.R
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * Created by skycap.
 */
open class BaseActivity :AppCompatActivity(),UICallBacks{
    lateinit var  dialogView: View
    lateinit var   dialog:BottomSheetDialog

    override fun onToast(message: String?) {
        runOnUiThread { Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show() }
    }

    override fun onLoading(loading: Boolean) {
        showLoading(loading)
    }
    private  fun showLoading(loading: Boolean){
        if(loading){
            runOnUiThread {
                dialogView=layoutInflater.inflate(R.layout.progress_layout, null)
                dialog= BottomSheetDialog(this)
                dialog.setContentView(dialogView)
                dialog.setCancelable(false)
                dialogView.minimumHeight=600
                dialog.show()
            }
        }
        else dialog.dismiss()
    }

}