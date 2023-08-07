package com.example.mdev1004s23ice7c

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.Gravity
import android.widget.Toast

class LibCom {

    private val inCompTag: String = "comLib"

    fun ErrorOccurred(c: Context?, txt: String?) {
        val toast = Toast.makeText(c, txt, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 200)
        toast.show()
        Log.d(inCompTag, txt!!)
    }

    fun ShareContains(c: Context, k: String?): Boolean {
        val pref: SharedPreferences =
            c.getSharedPreferences("MyPref", 0) // 0 - for private mode
        return pref.contains(k)
    }

    fun SharePWrite(c: Context, k: String?, s: String?) {
        val pref: SharedPreferences =
            c.getSharedPreferences("MyPref", 0) // 0 - for private mode
        val editor = pref.edit()
        editor.putString(k, s)
        editor.apply()
    }

    fun SharePRead(c: Context, k: String?): String? {
        val pref: SharedPreferences = c.getSharedPreferences("MyPref", 0)
        return pref.getString(k, null)
    }

    fun ShareClear(c: Context, k: String?) {
        val pref: SharedPreferences = c.getSharedPreferences("MyPref", 0)
        val editor = pref.edit()
        editor.remove(k)
        editor.apply()
    }

}