package com.example.ks.sharedpref

import android.content.Context
import android.content.SharedPreferences
import com.example.ks.models.LoginResponse
import com.google.gson.Gson


/**
 * Created by skycap.
 */
class SharedPreferenceHelper(val context: Context) {
    private  val PREF_FILE = "PREF"

    /**
     * Set a string shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    fun setSharedPreferenceString( key: String?, value: String?) {
        val settings: SharedPreferences = context.getSharedPreferences(PREF_FILE, 0)
        val editor = settings.edit()
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * Set a integer shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    fun setSharedPreferenceInt( key: String?, value: Int) {
        val settings: SharedPreferences = context.getSharedPreferences(PREF_FILE, 0)
        val editor = settings.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    /**
     * Set a Boolean shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    fun setSharedPreferenceBoolean(key: String?, value: Boolean) {
        val settings: SharedPreferences = context.getSharedPreferences(PREF_FILE, 0)
        val editor = settings.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    /**
     * Get a string shared preference
     * @param key - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    fun getSharedPreferenceString(key: String?, defValue: String?): String? {
        val settings: SharedPreferences = context.getSharedPreferences(PREF_FILE, 0)
        return settings.getString(key, defValue)
    }

    /**
     * Get a integer shared preference
     * @param key - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    fun getSharedPreferenceInt( key: String?, defValue: Int): Int {
        val settings: SharedPreferences = context.getSharedPreferences(PREF_FILE, 0)
        return settings.getInt(key, defValue)
    }

    /**
     * Get a boolean shared preference
     * @param key - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    fun getSharedPreferenceBoolean(key: String?, defValue: Boolean): Boolean {
        val settings: SharedPreferences = context.getSharedPreferences(PREF_FILE, 0)
        return settings.getBoolean(key, defValue)
    }

    fun saveUser(model:LoginResponse?){
        setSharedPreferenceString("user",Gson().toJson(model))
    }

    fun getUser():LoginResponse?{
        val sharedPreferenceString = getSharedPreferenceString("user", "")
        return if(sharedPreferenceString.isNullOrEmpty()) null
        else Gson().fromJson<LoginResponse>(sharedPreferenceString,LoginResponse::class.java)
    }
}
