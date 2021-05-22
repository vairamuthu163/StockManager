package com.india.stockmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class LoginActivity : AppCompatActivity() {
    var auth:FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        val loginUserNamel:TextInputLayout = findViewById(R.id.loginUserNamel)
        val loginPasswordl:TextInputLayout = findViewById(R.id.loginPasswordl)
        val loginUserName:TextInputEditText = findViewById(R.id.loginUserName)
        val loginPassword:TextInputEditText = findViewById(R.id.loginPassword)
        val login:Button = findViewById(R.id.login)
        val newUser : TextView = findViewById(R.id.newUser)
        val forgotPass : TextView = findViewById(R.id.forgotPass)

        newUser.paint?.isUnderlineText = true
        val prog:ProgressBar = findViewById(R.id.progressBar3)
        prog.visibility = View.INVISIBLE
        if(auth!!.currentUser!=null)
        {
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginUserName.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginUserNamel.isErrorEnabled = false
            }

        })
        loginPassword.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginPasswordl.isErrorEnabled = false
            }
        })
        login.setOnClickListener {
            if(TextUtils.isEmpty(loginUserName.text.toString().trim()))
            {
                loginUserNamel.error = "Please Enter Email"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(loginPassword.text.toString().trim()))
            {
                loginPasswordl.error = "Please Enter password"
                return@setOnClickListener
            }

            prog.visibility = View.VISIBLE
            auth!!.signInWithEmailAndPassword(loginUserName.text.toString().trim(),loginPassword.text.toString().trim())
                    .addOnCompleteListener {
                        if(it.isSuccessful)
                        {
                            val intent = Intent(applicationContext,MainActivity::class.java).apply {
                                putExtra("passmain",loginPassword.text.toString().trim())
                            }
                            startActivity(intent)
                            finish()
                        }
                        else
                        {
                            prog.visibility = View.INVISIBLE
                            Toast.makeText(applicationContext,"Login Failed Try again!!", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(applicationContext,it.toString(),Toast.LENGTH_SHORT).show()
                    }
        }
        newUser.setOnClickListener {
            val intent = Intent(applicationContext,SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
        forgotPass.setOnClickListener {
            val intent = Intent(applicationContext,ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}