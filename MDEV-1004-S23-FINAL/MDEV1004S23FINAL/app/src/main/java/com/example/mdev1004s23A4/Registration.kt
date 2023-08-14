package com.example.mdev1004s23A4

// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// 08/23/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call

class Registration : AppCompatActivity(), RegisterApiResponseCallback {

    //var
    private val iodb = IOdbRegister(this)
    private val comLib = LibCom()
    private var btnReg: Button? = null
    private var btnCan: Button? = null

    //Main Setup Buttons.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        init()

        btnCan?.setOnClickListener { _ -> login() }
        btnReg?.setOnClickListener { _ -> reg() }
    }

    private fun init() {
        btnCan = findViewById(R.id.btnCan)
        btnReg = findViewById(R.id.btnReg)
    }

    // Jump to login.
    private fun login() {
        startActivity(Intent(this@Registration, Login::class.java))
    }

    // Get Values from form and submit.
    private fun reg() {

        val textInputLayoutFName: TextInputLayout = findViewById(R.id.fName)
        val editTextFName = textInputLayoutFName.editText
        val fName = editTextFName?.text?.toString() ?: ""

        val textInputLayoutLName: TextInputLayout = findViewById(R.id.lName)
        val editTextLName = textInputLayoutLName.editText
        val lName = editTextLName?.text?.toString() ?: ""

        val textInputLayoutUsername: TextInputLayout = findViewById(R.id.userName)
        val editTextUsername = textInputLayoutUsername.editText
        val username = editTextUsername?.text?.toString() ?: ""

        val textInputLayoutEmail: TextInputLayout = findViewById(R.id.eMail)
        val editTextEmail = textInputLayoutEmail.editText
        val email = editTextEmail?.text?.toString() ?: ""

        val textInputLayoutPassword: TextInputLayout = findViewById(R.id.pass)
        val editTextPass = textInputLayoutPassword.editText
        val password = editTextPass?.text?.toString() ?: ""

        val textInputLayoutPassCheck: TextInputLayout = findViewById(R.id.passCheck)
        val editTextPassCheck = textInputLayoutPassCheck.editText
        val passCheck = editTextPassCheck?.text?.toString() ?: ""

        // Check for all fields entered.
        if (username.isEmpty() || password.isEmpty() || passCheck.isEmpty() || fName.isEmpty()
            || lName.isEmpty() || email.isEmpty()) {
            Log.d("Login", "Enter All Fields...")
            comLib.showAlert(this,"Field Error.", "Need all fields Entered..")
            return
        }
        // Passwords must match
        if (password != passCheck) {
            comLib.showAlert(this, "Field Error.","Passwords need to match.")
            return
        }

        val parameters = mapOf(
            "FirstName" to fName,
            "LastName" to lName,
            "EmailAddress" to email,
            "username" to username,
            "password" to password)
        iodb.register(parameters)
    }

    // Wirte Success / Fail.
    override fun onRSuccess(response: RegisterResponse){
        // Handle the successful login response here
        // For example, navigate to another activity
        startActivity(Intent(this@Registration, Login::class.java))
    }

    override fun onRFailure(call: Call<RegisterResponse>, t: Throwable) {
        // Handle login failure here
        // Show an error message, log the error, etc.
        comLib.showAlert(this,"Failed", "Registration Failed - Try Again?")
    }

}