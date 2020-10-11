package com.example.ks.activities.loginsignup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.ks.common.UICallBacks
import com.example.ks.repo.AuthRepo
import com.example.ks.utils.FieldValidators
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.internal.common.CrashlyticsCore
import kotlinx.coroutines.launch

class SignUpViewModel(
    override val uiCallBacks: UICallBacks,
    private val authRepo: AuthRepo
) : MyViewModel(uiCallBacks) {

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    val nameError: LiveData<String> =
        Transformations.switchMap(name, FieldValidators::setNotEmptyError)
    val emailError: LiveData<String> =
        Transformations.switchMap(email, FieldValidators::setEmailError)
    val phoneError: LiveData<String> =
        Transformations.switchMap(phone, FieldValidators::setPhoneError)
    val passwordError: LiveData<String> =
        Transformations.switchMap(password, FieldValidators::setPassError)

    val confirmPasswordErrorMed: MediatorLiveData<String> = MediatorLiveData()

    val confirmPasswordError  = MediatorLiveData<String>()

    init {
        confirmPasswordError.addSource(confirmPassword) {
            print("testing $it")
            if (password.value.toString() != confirmPassword.value.toString()) confirmPasswordError.postValue("Password Mismatched")
            else confirmPasswordError.postValue(null)
        }
    }

    fun signUp() {
        if (nameError.value == null
            && emailError.value == null
            && phoneError.value == null &&
            passwordError.value == null &&
            confirmPasswordError.value == null
            && name.value?.isNotEmpty()==true
            && email.value?.isNotEmpty()==true
            && phone.value?.isNotEmpty()==true
            && password.value?.isNotEmpty()==true
            && confirmPassword.value?.isNotEmpty()==true
        ) {
            val bodyMap = HashMap<String, String?>().apply {
                put("name", name.value)
                put("password", password.value)
                put("email", email.value)
                put("mobile", phone.value)
                put("password_confirmation", confirmPassword.value)
            }
            coroutineScope.launch {
                uiCallBacks.onLoading(true)
                when (val response = authRepo.signUp(bodyMap)) {
                    is ResultWrapper.Success -> {
                        uiCallBacks.onLoading(false)
                        if(response.value?.code !=200)
                        uiCallBacks.onToast(response.value?.message)
                        else {
                            uiCallBacks.onToast(response.value.message)
                            onNavigate.postValue(true)
                        }
                    }
                    is ResultWrapper.GenericError -> {
                        Log.i(this.javaClass.simpleName, "Generic: ")
                    }
                    ResultWrapper.SocketTimeOutError -> {
                        Log.i(this.javaClass.simpleName, "SocketTimeOutError: ")
                    }
                    ResultWrapper.NetworkError -> {
                        Log.i(this.javaClass.simpleName, "NetworkError: ")
                    }
                }
            }
        }

    }


}
