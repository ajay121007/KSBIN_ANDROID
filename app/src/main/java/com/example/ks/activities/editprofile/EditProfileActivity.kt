package com.example.ks.activities.editprofile

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ks.R
import com.example.ks.activities.claim.FileClaimViewModel
import com.example.ks.activities.profile.ChangePasswordActivity
import com.example.ks.common.BaseActivity
import com.example.ks.constants.UserConstants
import com.example.ks.databinding.ActivityEditProfileBinding
import com.example.ks.databinding.ActivityFileClaimBinding
import com.example.ks.model.profile.ProfileReponse
import com.example.ks.utils.PathUtils
import com.google.gson.Gson
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import gun0912.tedimagepicker.builder.TedImagePicker

import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.File

class EditProfileActivity : BaseActivity() {
    lateinit var binding: ActivityEditProfileBinding
    val profileViewModel: EditProfileViewModel by viewModel { parametersOf(this) }
    var filePath=""
    var fileName=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        binding.apply {
            viewModel=profileViewModel
            lifecycleOwner=this@EditProfileActivity
            executePendingBindings()

        }
        binding.changePasswordCard.setOnClickListener {
            startActivity(Intent(this@EditProfileActivity,ChangePasswordActivity::class.java))
        }
        initView()

    }
    private fun initView() {
        updateProfileView()
        clickEvent()
    }

    private fun updateProfileView() {
        val fromJson = Gson().fromJson<ProfileReponse>(
            intent.getStringExtra("profile"),
            ProfileReponse::class.java
        )
            fromJson.let {
//               binding.editUserName.setText(it.name)
//               binding.editProfileEmailEditText.setText(it.email)
//               binding.editPhone.setText(it.mobileNumber?:"")
                profileViewModel.usernName.postValue(it.data?.user?.name)
                profileViewModel.userEmail.postValue(it.data?.user?.email)
                profileViewModel.userMobile.postValue(it.data?.user?.mobileNumber)
                var imageUrl = ""
                if (!it.data?.user?.userImageUrl.isNullOrEmpty()){
                    imageUrl = it.data?.user?.userImageUrl?:return@let
                    filePath= imageUrl
                    fileName= it.data.user.userImage?:return@let
                }
                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.drawable.user_defult)
                requestOptions.error(R.drawable.user_defult)
                Glide.with(binding.userProfile.context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(imageUrl).into(binding.userProfile)
           }

    }

    private fun clickEvent() {
        binding.userProfile.setOnClickListener {

            val permissions =
                arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
            Permissions.check(
                this /*context*/,
                permissions,
                null /*rationale*/,
                null /*options*/,
                object : PermissionHandler() {
                    override fun onGranted() {
//                        TedBottomPicker.with(this@EditProfileActivity)
//                            .show {
//                                val uriString: String = it.toString()
//                                val myFile = File(uriString)
//                                val path: String = PathUtils.getPath(this@EditProfileActivity,it)
//                                val bitmap =
//                                    MediaStore.Images.Media.getBitmap(getContentResolver(), it)
//                                binding.userProfile.setImageBitmap(bitmap)
//                                filePath= it.toString()
//                                fileName= myFile.name
//                                profileViewModel.filePath.postValue(path)
//                            }
                    }
                })

        }

        binding.updateButton.setOnClickListener {

            if (binding.editUserName.text.toString().isNotEmpty() &&binding.editProfileEmailEditText.text.toString().isNotEmpty()
                && binding.editPhone.text.toString().isNotEmpty() && binding.editProfileEmailEditText.text.toString().isNotEmpty()){

                profileViewModel.updateProfile()


            }
            else{
                Toast.makeText(this, "Please fill all the field", Toast.LENGTH_LONG).show()
            }

        }

        binding.backImage.setOnClickListener {
            finish()
        }

    }

    fun openImagePicker(view: View) {
        selectDocs()

    }
    private fun selectDocs(){
        val permissions =
            arrayOf<String>(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
            )
        Permissions.check(
            this /*context*/,
            permissions,
            null /*rationale*/,
            null /*options*/,
            object : PermissionHandler() {
                override fun onGranted() {
                    TedImagePicker.with(this@EditProfileActivity)
                        .showCameraTile(true)
                        .start {
                            profileViewModel.filePath.postValue(it.path)
                            val requestOptions = RequestOptions()
                            requestOptions.placeholder(R.drawable.user_defult)
                            requestOptions.error(R.drawable.user_defult)
                            Glide.with(binding.userProfile.context)
                                .setDefaultRequestOptions(requestOptions)
                                .load(it.path).into(binding.userProfile)
                        }



//                    val intent = Intent()
////                    intent.type = "/*/"
//                    intent.type = "*/*"
//                    intent.action = Intent.ACTION_GET_CONTENT
//
//                    startActivityForResult(Intent.createChooser(intent, "Select PDF"), requestCode)
                }
            })

    }

}