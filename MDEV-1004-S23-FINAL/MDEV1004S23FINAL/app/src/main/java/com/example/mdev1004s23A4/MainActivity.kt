package com.example.mdev1004s23A4

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
import com.stripe.android.PaymentConfiguration

class MainActivity : AppCompatActivity() {

    // MainActivity - Main Load Function. (Splash Screen)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitymain)
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51NarMsGNNzTOXg3lMrvML0stHyODbyESTzai2tH964dgXEMe977WnfoLnJbbB6EYnkSQOqLFGDwjcM14vAisWnnr00aRsbEO8M"
        )
    }

    // Move on to Login
    fun onClickNext(view: View?) {
        startActivity(Intent(this@MainActivity, Login::class.java))
    }


}

