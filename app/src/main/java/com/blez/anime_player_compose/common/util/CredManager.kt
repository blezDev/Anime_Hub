package com.blez.anime_player_compose.common.util

import android.content.Context
import android.content.SharedPreferences
import com.blez.anime_player_compose.common.util.Constants.PREFS_TOKEN_FILE
import com.blez.anime_player_compose.common.util.Constants.USER_FAMILY_NAME
import com.blez.anime_player_compose.common.util.Constants.USER_GIVEN_NAME
import com.blez.anime_player_compose.common.util.Constants.USER_NAME
import com.blez.anime_player_compose.common.util.Constants.USER_PIC
import com.blez.anime_player_compose.common.util.Constants.USER_TOKEN

class CredManager(val context: Context) {
    private var sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = sharedPrefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPrefs.getString(USER_TOKEN, null)
    }

    fun saveProfilePic(pic: String) {
        val editor = sharedPrefs.edit()
        editor.putString(USER_PIC, pic)
        editor.apply()
    }

    fun getProfilePic(): String? {
        return sharedPrefs.getString(USER_PIC, null)
    }

    fun saveGivenName(name: String) {
        val editor = sharedPrefs.edit()
        editor.putString(USER_GIVEN_NAME, name)
        editor.apply()
    }

    fun getGivenName(): String? {
        return sharedPrefs.getString(USER_GIVEN_NAME, null)
    }

    fun saveName(name: String) {
        val editor = sharedPrefs.edit()
        editor.putString(USER_NAME, name)
        editor.apply()
    }

    fun getName(): String? {
        return sharedPrefs.getString(USER_NAME, null)
    }

    fun saveFamilyName(name: String) {
        val editor = sharedPrefs.edit()
        editor.putString(USER_FAMILY_NAME, name)
        editor.apply()
    }

    fun getFamilyName(): String? {
        return sharedPrefs.getString(USER_FAMILY_NAME, null)
    }
}