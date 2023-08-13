package com.example.mdev1004s23ice7c

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Registration : AppCompatActivity() {

    private var btnReg: Button? = null
    private var btnCan: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        init()

        btnCan?.setOnClickListener { v: View? -> login() }
        btnReg?.setOnClickListener { v: View? -> reg() }
    }

    private fun init() {
        btnCan = findViewById(R.id.btnCan)
        btnReg = findViewById(R.id.btnReg)
    }

    private fun login() {
        startActivity(Intent(this@Registration, Login::class.java))
    }

    private fun reg() {

    }

}