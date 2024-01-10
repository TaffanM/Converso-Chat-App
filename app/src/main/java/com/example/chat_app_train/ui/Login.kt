package com.example.chat_app_train.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.chat_app_train.R
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignIn: Button
    private lateinit var tvSignUp: TextView
    private lateinit var mAuth: FirebaseAuth
    private var isPasswordVisible = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.inputEmail)
        edtPassword = findViewById(R.id.inputPassword)
        btnSignIn = findViewById(R.id.button)
        tvSignUp = findViewById(R.id.textViewSignUp)
        val passwordToggle = R.drawable.custom_show_password
//        val passwordToggleDrawable = edtPassword.compoundDrawables[0]

        // sign in
        btnSignIn.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            } else {
                Toast.makeText(this@Login, "Please fill the username and the password", Toast.LENGTH_LONG).show()
            }

        }

        // sign up
        tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        // toggle password
        edtPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
            ContextCompat.getDrawable(this, R.drawable.custom_password_icon), null, ContextCompat.getDrawable(this, passwordToggle), null
        )

        // Toggle password visibility
        edtPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                // Check if the click was within the bounds of the drawable
                if (event.rawX >= (edtPassword.right - edtPassword.compoundDrawables[2].bounds.width())) {
                    togglePasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            false
        }


    }

    private fun login(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful ){
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login, "Either the username or password is incorrect", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun togglePasswordVisibility() {
        if(isPasswordVisible) {
            // hide password
            edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            edtPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.custom_password_icon, 0,
                R.drawable.baseline_visibility_24, 0)
            isPasswordVisible = false
        } else {
            // show password
            edtPassword.transformationMethod = null
            edtPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.custom_password_icon, 0,
                R.drawable.baseline_visibility_off_24, 0)
            isPasswordVisible = true
        }

        // move cursor to the end of the text
        edtPassword.setSelection(edtPassword.text.length)
    }
}