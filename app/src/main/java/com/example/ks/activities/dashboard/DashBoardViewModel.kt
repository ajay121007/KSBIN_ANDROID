package com.example.ks.activities.dashboard

import android.util.Log
import com.example.ks.common.UICallBacks
import com.example.ks.repo.UserRepo
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import kotlinx.coroutines.launch

/**
 * Created by skycap.
 */
class DashBoardViewModel(override val uiCallBacks: UICallBacks,private val userRepo: UserRepo) :MyViewModel(uiCallBacks){
fun getDashBoardData(){
    coroutineScope.launch {
        when(val response=userRepo.getDashBoardData()){
            is ResultWrapper.Success -> {
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