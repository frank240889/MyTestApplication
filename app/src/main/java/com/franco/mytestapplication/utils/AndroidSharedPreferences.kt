package com.franco.mytestapplication.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.franco.mytestapplication.BuildConfig
import com.franco.mytestapplication.interfaces.AbstractSharedPreferences

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 7:20 p.m.
 */
class AndroidSharedPreferences(application: Application): AbstractSharedPreferences {
    private val sharedPreferences = application.getSharedPreferences(
        BuildConfig.APPLICATION_ID,
        Context.MODE_PRIVATE
    )
    private val editor = sharedPreferences.edit()

    override fun saveString(key: String, value: String) {
        editor.putString(key, value).commit()

    }

    override fun saveInt(key: String, value: Int) {
        editor.putInt(key, value).commit()

    }

    override fun saveFloat(key: String, value: Float) {
        editor.putFloat(key, value).commit()

    }

    override fun saveLong(key: String, value: Long) {
        editor.putLong(key, value).commit()

    }

    override fun saveBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).commit()

    }

    @SuppressLint("ApplySharedPref")
    override fun removeAll() {
        sharedPreferences.edit().clear().commit()
    }

    override fun delete(key: String) {
        editor.remove(key).commit()
    }

    override fun contains(key: String) = sharedPreferences.contains(key)

    override fun <T> remove(key: String) = run {
        editor.remove(key).apply()
        null as T?
    }

    override fun getString(key: String) = sharedPreferences.getString(key, null)

    override fun getInt(key: String) = sharedPreferences.getInt(key, -1)

    override fun getFloat(key: String) = sharedPreferences.getFloat(key, -1f)

    override fun getLong(key: String) = sharedPreferences.getLong(key, -1)

    override fun getBoolean(key: String) = sharedPreferences.getBoolean(key, false)
}