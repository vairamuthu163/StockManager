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
    var myRef:DatabaseReference? = null
    lateinit var uName:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Users")
        myRef = database?.reference!!.child("data")
        val signEmail1l:TextInputLayout = findViewById(R.id.signUpEmail_1l)
        val signPassword1l:TextInputLayout = findViewById(R.id.signUpPassword_1l)
        val signUpConfirmPassword1l:TextInputLayout = findViewById(R.id.signUpConfirmPassword_1l)
        val signUpEmail1:TextInputEditText = findViewById(R.id.signUpEmail_1)
        val signUpPassword1:TextInputEditText = findViewById(R.id.signUpPassword_1)
        val signUpConfirmPassword1:TextInputEditText = findViewById(R.id.signUpConfirmPassword_1)
        val signUpBtn1:Button = findViewById(R.id.signUpBtn_1)

        val loginEmailNamel:TextInputLayout = findViewById(R.id.loginEmailNamel)
        val loginEmailName:TextInputEditText = findViewById(R.id.loginEmailName)

        val progressNext:ProgressBar = findViewById(R.id.progressNext)
        progressNext.visibility = View.INVISIBLE
        loginEmailName.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginEmailNamel.isErrorEnabled = false
            }
        })
        signUpEmail1.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signEmail1l.isErrorEnabled = false
            }
        })
        signUpPassword1.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signPassword1l.isErrorEnabled = false
            }

        })
        signUpConfirmPassword1.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signUpConfirmPassword1l.isErrorEnabled = false
            }
        })
        signUpBtn1.setOnClickListener {

            if(TextUtils.isEmpty(signUpEmail1.text.toString().trim())){
                signEmail1l.error = "Please Enter Email"
                return@setOnClickListener
            }
             if(TextUtils.isEmpty(signUpPassword1.text.toString().trim())){
                signPassword1l.error = "Please Enter password"
                 return@setOnClickListener
            }
             if(TextUtils.isEmpty(signUpConfirmPassword1.text.toString().trim())){
                signUpConfirmPassword1l.error = "Please Enter confirm password"
                 return@setOnClickListener
            }
            if(!(signUpPassword1.text.toString().trim().equals(signUpConfirmPassword1.text.toString().trim()))){
                signUpConfirmPassword1l.error = "password mismatch!"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(loginEmailName.text.toString().trim()))
            {
                loginEmailNamel.error = "Please enter UserName"
                return@setOnClickListener
            }
            progressNext.visibility = View.VISIBLE
            uName = FirebaseDatabase.getInstance().getReference()
            uName.child("data").child(loginEmailName.text.toString().trim()).get().addOnSuccessListener {
                if(it.exists())
                {
                    progressNext.visibility = View.INVISIBLE
                    Toast.makeText(applicationContext,"UserName already Exist!",Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                else{
                    auth.createUserWithEmailAndPassword(signUpEmail1.text.toString().trim(),signUpPassword1.text.toString().trim())
                        .addOnCompleteListener {
                            if(it.isSuccessful)
                            {
                                var currentUser = auth.currentUser
                                val currentUserDb = databaseReference?.child((currentUser.uid!!))
                                val Pass = myRef?.child(loginEmailName.text.toString().trim())!!.child("PaSsWoRd").setValue("#fghs#hsd&c%s")

                                var model = UserDetails(loginEmailName.text.toString().trim(),"#fghs#hsd&c%s")
                                currentUserDb?.setValue(model)
                                Toast.makeText(applicationContext,"Registration Success",Toast.LENGTH_SHORT).show()
                                val intent = Intent(applicationContext,MainActivity::class.java).apply {
                                    putExtra("passmain",signUpPassword1.text.toString().trim())
                                }
                                startActivity(intent)
                                finish()
                            }
                            else{
                                progressNext.visibility = View.INVISIBLE
                                Toast.makeText(applicationContext,"Registration Failed Try again!!",Toast.LENGTH_SHORT).show()
                            }
                        }.addOnFailureListener {
                                progressNext.visibility = View.INVISIBLE
                                Toast.makeText(applicationContext,it.toString(),Toast.LENGTH_SHORT).show()
                        }
                }
            }.addOnFailureListener {

            }
        }
    }
}