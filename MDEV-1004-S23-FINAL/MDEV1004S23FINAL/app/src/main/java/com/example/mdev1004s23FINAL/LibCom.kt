package com.example.mdev1004s23FINAL

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class LibCom {

    private val inCompTag: String = "comLib"

    fun errorOccurred(c: Context?, txt: String?) {
        val toast = Toast.makeText(c, txt, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 200)
        toast.show()
        Log.d(inCompTag, txt!!)
    }

    fun shareContains(c: Context, k: String?): Boolean {
        val pref: SharedPreferences =
            c.getSharedPreferences("MyPref", 0) // 0 - for private mode
        return pref.contains(k)
    }

    fun sharePWrite(c: Context, k: String?, s: String?) {
        val pref: SharedPreferences =
            c.getSharedPreferences("MyPref", 0) // 0 - for private mode
        val editor = pref.edit()
        editor.putString(k, s)
        editor.apply()
    }

    fun sharePRead(c: Context, k: String?): String? {
        val pref: SharedPreferences = c.getSharedPreferences("MyPref", 0)
        return pref.getString(k, null)
    }

    fun shareClear(c: Context, k: String?) {
        val pref: SharedPreferences = c.getSharedPreferences("MyPref", 0)
        val editor = pref.edit()
        editor.remove(k)
        editor.apply()
    }

    fun showToast(c: Context, message: String) {
            Toast.makeText(c,  message, Toast.LENGTH_LONG).show()
    }

    fun showAlert(c: Context, title: String, message: String? = null) {
            val builder = AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(message)
            builder.setPositiveButton("Ok", null)
            builder.create().show()
    }

}

