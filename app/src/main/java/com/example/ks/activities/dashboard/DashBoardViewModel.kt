package com.example.ks.activities.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ks.common.UICallBacks
import com.example.ks.constants.UserConstants
import com.example.ks.models.DashBoardResponse
import com.example.ks.repo.UserRepo
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import kotlinx.coroutines.launch

/**
 * Created by skycap.
 */
class DashBoardViewModel(override val uiCallBacks: UICallBacks,private val userRepo: UserRepo) :MyViewModel(uiCallBacks){
    val data=MutableLiveData<DashBoardResponse>()
fun getDashBoardData(){
    uiCallBacks.onLoading(true)
    coroutineScope.launch {
        when(val response=userRepo.getDashBoardData()){
            is ResultWrapper.Success -> {
                UserConstants.policyArrayList = ArrayList()
                 uiCallBacks.onLoading(false)
//                uiCallBacks.onToast(response.value?.data?.policies?.get(1)?.progress.toString())
               // UserConstants.policyArrayList = data?.value?.data?.policies as ArrayList<DashBoardResponse.Data.Policy>
                data.postValue(response.value)


                Log.i(this.javaClass.simpleName, "Success: ")
            }
            is ResultWrapper.GenericError -> {
                Log.i(this.javaClass.simpleName, "Success: ")
            }
            ResultWrapper.SocketTimeOutError -> {
                Log.i(this.javaClass.simpleName, "Success: ")
            }
            ResultWrapper.NetworkError -> {
                Log.i(this.javaClass.simpleName, "Success: ")
            }
        }
    }
}
}