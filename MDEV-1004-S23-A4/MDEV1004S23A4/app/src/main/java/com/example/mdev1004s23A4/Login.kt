package com.example.mdev1004s23A4

// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// 08/23/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call

class Login : AppCompatActivity(), LoginApiResponseCallback {

    // Var
    private val iodb = IOdbLogin(this)
    private val comLib = LibCom()
    private var btnLog: Button? = null
    private var btnReg: Button? = null

    // Main Load
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Clear Auth From Local SharedPrefs.
        comLib.shareClear(applicationContext,"auth")

        //Set Buttons.
        init()
        btnLog?.setOnClickListener { _ -> login() }
        btnReg?.setOnClickListener { _ -> reg() }
    }

    //Buttons Setup
    private fun init() {
        btnLog = findViewById(R.id.btnLogin)
        btnReg = findViewById(R.id.btnReg)
    }

    // Store username and password and pass it though checker.
    private fun login() {

        val textInputLayoutEmail: TextInputLayout = findViewById(R.id.eMail)
        val editTextEmail = textInputLayoutEmail.editText
        val username = editTextEmail?.text?.toString() ?: ""

        val textInputLayoutPassword: TextInputLayout = findViewById(R.id.password)
        val editTextPass = textInputLayoutPassword.editText
        val password = editTextPass?.text?.toString() ?: ""

        if (username.isEmpty() || password.isEmpty()) {
            Log.d("Login", "Please enter both username and password.")
            comLib.showAlert(this, "Login", "Needs all fields.")
            return
        }

        val parameters = mapOf("username" to username, "password" to password)
        iodb.login(parameters)

    }

    // If Successful
    override fun onSuccess(response: LoginResponse){
        // Handle the successful login response here
        // For example, navigate to another activity
        comLib.sharePWrite(applicationContext, "auth" , response.token)
        startActivity(Intent(this@Login, Movielisting::class.java))
    }

    // If Failed.
    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        // Handle login failure here
        // Show an error message, log the error, etc.
        Log.d("crap","JSON Call Error")
        comLib.showAlert(this, "Login", "Failed - Try Again?")
    }

    // Jump to Registration for new user.
    private fun reg() {
        startActivity(Intent(this@Login, Registration::class.java))
    }


}

