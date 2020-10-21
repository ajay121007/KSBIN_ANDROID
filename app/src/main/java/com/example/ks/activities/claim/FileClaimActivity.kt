package com.example.ks.activities.claim

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.example.ks.R
import com.example.ks.activities.webview.WebViewActivity
import com.example.ks.api.Constants
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityFileClaimBinding
import com.example.ks.utils.PathUtils
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import gun0912.tedbottompicker.TedBottomPicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.File


class FileClaimActivity : BaseActivity(), OnDownloadListener {

    private val PDF_PICKER_RESULTS: Int=1010
    lateinit var binding: ActivityFileClaimBinding
    val viewModel: FileClaimViewModel by viewModel { parametersOf(this) }
     var filePath:String=""
     var fileName:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_file_claim)
        binding.apply {
            setSupportActionBar(toolbar)
            executePendingBindings()
        }
        initView()
        bindObeserver()
    }

    private fun bindObeserver() {
        viewModel.onData.observe(this, Observer {
            startActivityForResult(Intent(this, WebViewActivity::class.java).apply {
                putExtra("token", it)
                putExtra("title","File a claim")
            }, 101)
        })
    }

    private fun initView() {
      //  viewModel.getContractList()
        clickEvent()
    }

    private fun clickEvent() {

        binding.attacheClaimPics.setOnClickListener {
            val permissions =
                arrayOf<String>(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
            Permissions.check(
                this /*context*/,
                permissions,
                null /*rationale*/,
                null /*options*/,
                object : PermissionHandler() {
                    override fun onGranted() {

                        selectDocs()
                    }
                })

        }
        binding.uploadDocumentBtn.setOnClickListener {

            if (filePath.isNotEmpty()) {
                viewModel.uploadDocs(filePath, fileName)
            }

        }
    }

    private fun selectDocs() {
        val intent = Intent(this, FilePickerActivity::class.java)
        intent.putExtra(
            FilePickerActivity.CONFIGS, Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(true)
                .enableImageCapture(true)
                .setMaxSelection(1)
                .setSkipZeroSizeFiles(true)
                .build()
        )
        startActivityForResult(intent, PDF_PICKER_RESULTS)
//        TedBottomPicker.with(this)
//            .show {
//                val uriString: String = PathUtils.getPath(this, it)
//                val myFile = File(uriString)
//                val path: String = myFile.getAbsolutePath()
//                binding.image.setImageURI(it)
//                binding.image.setImageURI(Uri.parse(path))
//                binding.fileName.text = path
//                filePath= it.toString()
//                fileName= myFile.name
//
//
//            }
    }

    fun downloadFile(view: View) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.FILE_BASE_URL + "storage/claim.pdf"))
        startActivity(browserIntent)
//        onLoading(true)
//       PRDownloader.download(
//           Constants.FILE_BASE_URL + "storage/claim.pdf",
//           Environment.getExternalStoragePublicDirectory(
//               Environment.DIRECTORY_DOWNLOADS
//           ).absolutePath,
//           fileName
//       )
//        .build().start(this)

    }

    override fun onDownloadComplete() {
        onLoading(false)
        onToast("Downloaded successfully in downloads")
    }

    override fun onError(error: Error?) {
        onLoading(false)
        onToast("Failed to Download")
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==PDF_PICKER_RESULTS) {

            val mediaFile: MediaFile? =
                data?.getParcelableArrayListExtra<MediaFile>(FilePickerActivity.MEDIA_FILES)
                    ?.get(0)

            val path: String? = mediaFile?.path

            binding.image.setImageURI(Uri.parse(path))
            binding.fileName.text = path
            if (path != null) {
                filePath = path
            }
            fileName = mediaFile?.name.toString()


            if (requestCode == 101 && resultCode == RESULT_OK) {
                showAlertDialog("File claimed Successfully")
            }
        }}
}