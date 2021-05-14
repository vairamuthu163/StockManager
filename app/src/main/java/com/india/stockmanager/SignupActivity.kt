package com.india.stockmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    lateinit var auth :FirebaseAuth
    var databaseReference:DatabaseReference? = null
    var database:FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Users")

        val signEmail1l:TextInputLayout = findViewById(R.id.signUpEmail_1l)
        val signPassword1l:TextInputLayout = findViewById(R.id.signUpPassword_1l)
        val signUpConfirmPassword1l:TextInputLayout = findViewById(R.id.signUpConfirmPassword_1l)
        val signUpEmail1:TextInputEditText = findViewById(R.id.signUpEmail_1)
        val signUpPassword1:TextInputEditText = findViewById(R.id.signUpPassword_1)
        val signUpConfirmPassword1:TextInputEditText = findViewById(R.id.signUpConfirmPassword_1)
        val signUpBtn1:Button = findViewById(R.id.signUpBtn_1)
        signUpBtn1.setOnClickListener {
            if(TextUtils.isEmpty(signUpEmail1.text.toString())){
                signEmail1l.error = "Please Enter Email"
                return@setOnClickListener
            }
             if(TextUtils.isEmpty(signUpPassword1.text.toString())){
                signPassword1l.error = "Please Enter password"
                 return@setOnClickListener
            }
             if(TextUtils.isEmpty(signUpConfirmPassword1.text.toString())){
                signUpConfirmPassword1l.error = "Please Enter confirm password"
                 return@setOnClickListener
            }
            if(!(signUpPassword1.text.toString().equals(signUpConfirmPassword1.text.toString()))){
                signUpConfirmPassword1l.error = "password mismatch!"
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(signUpEmail1.text.toString(),signUpPassword1.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful)
                        {
                            var currentUser = auth.currentUser
                            val currentUserDb = databaseReference?.child((currentUser.uid!!))
                            var model = UserDetails(currentUser.uid,"#fghs#hsd&c%s")
                            currentUserDb?.setValue(model)
                            Toast.makeText(applicationContext,"Registration Success",Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            Toast.makeText(applicationContext,"Registration Failed Try again!!",Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(applicationContext,it.toString(),Toast.LENGTH_SHORT).show()
                    }


        }
    }
}