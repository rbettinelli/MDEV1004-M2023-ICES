package com.example.mdev1004s23ice7c

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call

class Login : AppCompatActivity(), LoginApiResponseCallback {

    private val iodb = IOdbLogin(this)
    private val comLib = LibCom()
    private var btnLog: Button? = null
    private var btnReg: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Clear Auth From Local SharedPrefs.
        comLib.ShareClear(applicationContext,"auth")

        init()

        btnLog?.setOnClickListener { _ -> login() }
        btnReg?.setOnClickListener { _ -> reg() }
    }

    private fun init() {
        btnLog = findViewById(R.id.btnLogin)
        btnReg = findViewById(R.id.btnReg)
    }

    private fun login() {

        val textInputLayoutEmail: TextInputLayout = findViewById(R.id.eMail)
        val editTextEmail = textInputLayoutEmail.editText
        val username = editTextEmail?.text?.toString() ?: ""

        val textInputLayoutPassword: TextInputLayout = findViewById(R.id.password)
        val editTextPass = textInputLayoutPassword.editText
        val password = editTextPass?.text?.toString() ?: ""

        if (username.isEmpty() || password.isEmpty()) {
            Log.d("Login", "Please enter both username and password.")
            return
        }

        val parameters = mapOf("username" to username, "password" to password)
        iodb.login(parameters)

    }

    override fun onSuccess(response: LoginResponse){
        // Handle the successful login response here
        // For example, navigate to another activity
        comLib.SharePWrite(applicationContext, "auth" , response.token)
        startActivity(Intent(this@Login, Movielisting::class.java))
    }

    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        // Handle login failure here
        // Show an error message, log the error, etc.
        Log.d("crap","JSON Call Error")
    }

    private fun reg() {
        startActivity(Intent(this@Login, Registration::class.java))
    }


}

