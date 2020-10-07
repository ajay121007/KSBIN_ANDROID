package com.example.ks.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ks.common.UICallBacks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job

/**
 * Created by skycap.
 */
open class MyViewModel(open val uiCallBacks: UICallBacks) : ViewModel() {

    val errorMessage=MutableLiveData<String>()
    val onLoading=MutableLiveData<Boolean>().apply { value=false }

    /**
     * This is the job for all coroutines started by this ViewModel.
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    protected val viewModelJob = Job()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     * Since we pass viewModelJob, you can cancel all coroutines
     * launched by uiScope by calling viewModelJob.cancel()
     */
    protected val coroutineScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}