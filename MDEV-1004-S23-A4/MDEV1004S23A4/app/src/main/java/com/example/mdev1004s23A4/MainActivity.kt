package com.example.mdev1004s23ice7c

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.stripe.android.PaymentConfiguration

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitymain)
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51NarMsGNNzTOXg3lMrvML0stHyODbyESTzai2tH964dgXEMe977WnfoLnJbbB6EYnkSQOqLFGDwjcM14vAisWnnr00aRsbEO8M"
        )
    }

    fun onClickNext(view: View?) {
        startActivity(Intent(this@MainActivity, Login::class.java))
    }


}

