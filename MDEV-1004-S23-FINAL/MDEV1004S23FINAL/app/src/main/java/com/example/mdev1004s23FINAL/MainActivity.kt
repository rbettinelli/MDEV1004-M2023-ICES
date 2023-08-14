package com.example.mdev1004s23FINAL

// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// 08/23/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // MainActivity - Main Load Function. (Splash Screen)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitymain)
    }

    // Move on to Login
    fun onClickNext(view: View?) {
        startActivity(Intent(this@MainActivity, Login::class.java))
    }

}

