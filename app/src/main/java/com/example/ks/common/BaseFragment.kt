package com.example.ks.common


import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ks.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.reflect.KClass


/**
 * Created by skycap.
 */
abstract class BaseFragment :Fragment(),UICallBacks{
    lateinit var  dialogView: View
    lateinit var   dialog:BottomSheetDialog
    override fun onToast(message: String?) {
        requireActivity().runOnUiThread { Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show() }
    }

    override fun onLoading(loading: Boolean) {
        showLoading(loading)
    }


    private  fun showLoading(loading: Boolean){
       if(loading){
           requireActivity().runOnUiThread {
               dialogView=layoutInflater.inflate(R.layout.progress_layout, null)
              dialog= BottomSheetDialog(requireContext())
               dialog.setContentView(dialogView)
               dialog.setCancelable(false)
               dialogView.minimumHeight=600
               dialog.show()
           }
       }
        else dialog.dismiss()
    }

    override fun showDialog(message: String?) {

    }


}

interface UICallBacks{
    fun onToast(message: String?)

    fun onLoading(loading: Boolean)

    fun showDialog(message:String?)


}