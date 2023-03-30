package com.example.personalfinance_v12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.personalfinance_v12.MainActivity
import com.example.personalfinance_v12.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var btnCancle: Button

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // remove action bar
        supportActionBar?.hide()

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btn_login)
        btnSignUp = findViewById(R.id.btn_signup)
        btnCancle = findViewById(R.id.cancel_button)

        mAuth = FirebaseAuth.getInstance()

        // double colon means get class
        btnSignUp.setOnClickListener {
            // redirect
            val intent = Intent(this@Login, SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString()

            if (!isPasswordValid(edtPassword.text!!)) {
                edtPassword.error = getString(R.string.shr_error_password)
            } else {
                //Clear the error.
                edtPassword.error = null
            }

            if (!isEmailValid(edtEmail.text!!)){
                edtEmail.error = "Please enter your email"
            } else {
                edtEmail.error = null
            }


            login(email, password)
        }

        edtPassword.setOnClickListener {
            if (isPasswordValid(edtPassword.text!!)) {
                edtPassword.error = null
            }
        }

        btnCancle.setOnClickListener {
            finish()
        }

    }

    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 8
    }
    private fun isEmailValid(text: Editable?): Boolean {
        return text != null
    }


    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithEmail:success")
//                    val user = auth.currentUser
//                    updateUI(user)
                    val intent = Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithEmail:failure", task.exception)
//                    Toast.makeText(baseContext, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                    Toast.makeText(this@Login, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
    }
}






