package com.example.ks.activities.profile

import androidx.lifecycle.MutableLiveData
import com.example.ks.common.UICallBacks
import com.example.ks.constants.UserConstants
import com.example.ks.models.ProfileResponse
import com.example.ks.repo.AuthRepo
import com.example.ks.repo.UserRepo
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import com.example.ks.utils.SingleLiveEvent
import kotlinx.coroutines.launch

/**
 * Created by skycap.
 */
class ProfileViewModel (override val uiCallBacks: UICallBacks, private val userRepo: AuthRepo) : MyViewModel(uiCallBacks){
    val liveData=MutableLiveData<ProfileResponse>()
    val logout=SingleLiveEvent<Boolean>()
    fun getProfileData(){
        uiCallBacks.onLoading(true)
        coroutineScope.launch {
            when(val response=userRepo.getProfileInfo()){
                is ResultWrapper.Success -> {
                    UserConstants.userProfile=response.value

                    liveData.postValue(response.value)
                    uiCallBacks.onLoading(false)
                    uiCallBacks.onToast(response.value?.message)
                }
                is ResultWrapper.GenericError -> {
                    uiCallBacks.onLoading(false)
                    uiCallBacks.onToast(response.error?.error_des)
                }
                ResultWrapper.SocketTimeOutError -> {

                }
                ResultWrapper.NetworkError -> {

                }
            }
        }
    }
    fun logoutUser(){
        uiCallBacks.onLoading(true)
        coroutineScope.launch {
            when(userRepo.logOut()){
                is ResultWrapper.Success ->logout.postValue(true)
                is ResultWrapper.GenericError -> {
                    uiCallBacks.onLoading(false)
                    uiCallBacks.onToast("Something went wrong")
                }
                ResultWrapper.SocketTimeOutError -> {
                    uiCallBacks.onLoading(false)
                    uiCallBacks.onToast("Something went wrong")
                }
                ResultWrapper.NetworkError -> {
                    uiCallBacks.onLoading(false)
                    uiCallBacks.onToast("Something went wrong")
                }
            }
        }
    }
    fun silentLogOut(){
//        uiCallBacks.onLoading(loading)
        coroutineScope.launch {
            when(userRepo.logOut()){
                is ResultWrapper.Success ->logout.postValue(true)
                is ResultWrapper.GenericError -> {


                }
                ResultWrapper.SocketTimeOutError -> {

                }
                ResultWrapper.NetworkError -> {

                }
            }
        }
    }
}