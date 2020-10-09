package com.example.ks.activities.claim

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ks.R
import com.example.ks.activities.signabledocument.SignableDocumentModel
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityFileClaimBinding
import gun0912.tedbottompicker.TedBottomPicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.File


class FileClaimActivity : BaseActivity() {

    lateinit var binding: ActivityFileClaimBinding
    val viewModel: FileClaimViewModel by viewModel { parametersOf(this) }
     var filePath:String=""
     var fileName:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_file_claim)
        initView()
    }

    private fun initView() {
      //  viewModel.getContractList()

        clickEvent()
    }

    private fun clickEvent() {

        binding.attacheClaimPics.setOnClickListener {
            TedBottomPicker.with(this)
                .show {
                    val uriString: String = it.toString()
                    val myFile = File(uriString)
                    val path: String = myFile.getAbsolutePath()
                    binding.image.setImageURI(it)
                    binding.image.setImageURI(Uri.parse( path))
                    binding.fileName.text = path
                    filePath= it.toString()
                    fileName= myFile.name


                }
        }
        binding.uploadDocumentBtn.setOnClickListener {

            if (filePath.isNotEmpty()) {
                viewModel.uploadDocs(filePath,fileName)
            }

        }
    }
}