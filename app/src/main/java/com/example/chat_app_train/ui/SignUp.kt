package com.example.chat_app_train.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.chat_app_train.R
import com.example.chat_app_train.helper.User
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edtEmailSign: EditText
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var signUpBtn: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()
        FirebaseApp.initializeApp(this)

        edtEmailSign = findViewById(R.id.inputEmailSign)
        edtUsername = findViewById(R.id.inputUsername)
        edtPassword = findViewById(R.id.inputPasswordSign)
        edtConfirmPassword = findViewById(R.id.inputConfirmPassword)
        signUpBtn = findViewById(R.id.signUpBtn)




        val customBackButton = findViewById<ImageView>(R.id.backArrow)
        customBackButton.setOnClickListener{
            onBackPressed()
        }

        signUpBtn.setOnClickListener{
            val email = edtEmailSign.text.toString()
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()
            val confirmPassword = edtConfirmPassword.text.toString()
            val currentUser = mAuth.currentUser
            var allFormsFilled = true

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || username.isEmpty()) {
                Toast.makeText(this@SignUp, "Please fill all the required forms", Toast.LENGTH_LONG).show()
                allFormsFilled = false
            }

            if (allFormsFilled) {
                if (password == confirmPassword) {
                    signup(email, username, password)
                } else {
                    Toast.makeText(this@SignUp, "Password and confirm password don't match please try again", Toast.LENGTH_LONG).show()
                }
            }

            if (currentUser != null) {
                currentUser.reload()
            }
        }


    }

    private fun signup(email: String, name: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(email, name, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, Login::class.java)
                    Toast.makeText(this@SignUp, "Sign Up is succeed, please login", Toast.LENGTH_LONG).show()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp, "Error occured please try again", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun addUserToDatabase(email: String, name: String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))
    }
}