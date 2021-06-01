package com.exwara.jobflex.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.exwara.jobflex.core.data.source.local.entity.UserEntity
import com.exwara.jobflex.core.utils.Config.EMAIL_USER
import com.exwara.jobflex.core.utils.Config.FULL_NAME
import com.exwara.jobflex.core.utils.Config.ID_USER
import com.exwara.jobflex.core.utils.Config.LOGGED_IN_SHARED_PREF
import com.exwara.jobflex.core.utils.Config.PHONE_NUMBER_USER

/**
 * Created by robby on 16/05/21.
 */
class Preferences {

    private lateinit var sharedPreferences: SharedPreferences

    fun hasUserLoggedIn(context: Context): Boolean {
        sharedPreferences = context.getSharedPreferences(
            Config.USER_SHARED_PREF_NAME, Context.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean(LOGGED_IN_SHARED_PREF, false)
    }

    fun deletePref(context: Context) {
        sharedPreferences =
            context.getSharedPreferences(Config.USER_SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        editor.commit()
    }

    fun saveDataUser(context: Context, userEntity: UserEntity) {
        sharedPreferences = context.getSharedPreferences(
            Config.USER_SHARED_PREF_NAME, Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(ID_USER, userEntity.userID)
        editor.putString(FULL_NAME, userEntity.fullName)
        editor.putString(EMAIL_USER, userEntity.email)
        editor.putString(PHONE_NUMBER_USER, userEntity.phoneNumber)
        editor.putBoolean(LOGGED_IN_SHARED_PREF, true)
        editor.apply()
        editor.commit()
    }

    fun getUserId(context: Context): String {
        sharedPreferences =
            context.getSharedPreferences(Config.USER_SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(ID_USER, "").toString()
    }

    fun getUserFullName(context: Context): String {
        sharedPreferences =
            context.getSharedPreferences(Config.USER_SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(FULL_NAME, "").toString()
    }

}