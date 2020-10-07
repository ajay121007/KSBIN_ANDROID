package com.example.ks

/**
 * Created by skycap.
 */
sealed class FieldValidation {
     class HasData(val data:String):FieldValidation()
     class HasError(val error:String):FieldValidation()
}