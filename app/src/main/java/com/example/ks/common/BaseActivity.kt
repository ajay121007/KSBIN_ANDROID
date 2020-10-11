package com.example.ks.common

import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ks.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.reflect.KClass

/**
 * Created by skycap.
 */
open class BaseActivity :AppCompatActivity(),UICallBacks{
     var  dialogView: View?=null
     var   dialog:BottomSheetDialog?=null

    override fun onToast(message: String?) {
        runOnUiThread { Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
        }
    }

    override fun onLoading(loading: Boolean) {
        showLoading(loading)
    }

    override fun showDialog(message: String?) {
        showAlertDialog(message)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else ->false
        }
    }

    private  fun showLoading(loading: Boolean){
        if(loading){
            runOnUiThread {
                dialogView=layoutInflater.inflate(R.layout.progress_layout, null)
                dialog= BottomSheetDialog(this)
                dialog?.setContentView(dialogView ?: return@runOnUiThread)
//                dialog.setCancelable(false)
                dialogView?.minimumHeight=600
                dialog?.show()
            }
        }
        else dialog?.dismiss()
    }
    open fun showAlertDialog(message: String?) {

        // setup the alert builder
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage(message)

        // add a button
        builder.setPositiveButton("OK"
        ) { p0, p1 -> p0.dismiss()
        finish()
        }

        // create and show the alert dialog
       runOnUiThread {  val dialog: AlertDialog = builder.create()
           dialog.show() }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

}