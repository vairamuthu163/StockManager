package com.india.stockmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val emaill:TextInputLayout = findViewById(R.id.emaill)
        val email:TextInputEditText = findViewById(R.id.email)

        val submit = findViewById<Button>(R.id.submit)
        val forgotProgress:ProgressBar = findViewById(R.id.forgotProgress)
        forgotProgress.visibility = View.INVISIBLE

        email.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               emaill.isErrorEnabled = false
            }

        })
        submit.setOnClickListener {
            forgotProgress.visibility = View.VISIBLE
            if(TextUtils.isEmpty(email.text.toString().trim()))
            {
                emaill.error = "Please enter Email Address!"
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().sendPasswordResetEmail(email.text.toString()).addOnCompleteListener { 
                if(it.isSuccessful)
                {
                    forgotProgress.visibility = View.INVISIBLE
                    Toast.makeText(applicationContext,"Email sent Successfully to Reset your password",Toast.LENGTH_SHORT).show()
                    finish()
                }
                else{
                    forgotProgress.visibility = View.INVISIBLE
                    Toast.makeText(applicationContext,it.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}