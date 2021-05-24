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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class LoginActivity2 : AppCompatActivity() {
    lateinit var uName: DatabaseReference
    var UpassStr:String = "null"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        val loginUserNamel2:TextInputLayout = findViewById(R.id.loginUserNamel2)
        val loginPasswordl2:TextInputLayout = findViewById(R.id.loginPasswordl2)

        val loginUserName2: TextInputEditText = findViewById(R.id.loginUserName2)
        val loginPassword2: TextInputEditText = findViewById(R.id.loginPassword2)

        val login2: Button = findViewById(R.id.login2)

        val progress:ProgressBar = findViewById(R.id.progress)
        progress.visibility = View.INVISIBLE
        loginUserName2.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginUserNamel2.isErrorEnabled = false
            }
        })
        loginPassword2.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginPasswordl2.isErrorEnabled = false
            }

        })
        login2.setOnClickListener {

            if(TextUtils.isEmpty(loginUserName2.text.toString().trim()))
            {
                loginUserNamel2.error = "Please enter email"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(loginPassword2.text.toString().trim()))
            {
                loginPasswordl2.error = "Please enter password"
                return@setOnClickListener
            }
            if(loginPassword2.text.toString().trim().equals("#fghs#hsd&c%s")){
                Toast.makeText(applicationContext,"Password does not match!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            progress.visibility = View.VISIBLE
            uName = FirebaseDatabase.getInstance().getReference()
            uName.child("data").child(loginUserName2.text.toString().trim()).get().addOnSuccessListener {
                if (it.exists())
                {
                    UpassStr = it.child("PaSsWoRd").value.toString()
                    if(loginPassword2.text.toString().trim().equals(UpassStr))
                    {
                        val intent = Intent(applicationContext,MainActivity::class.java).apply {
                            putExtra("UserName",loginUserName2.text.toString().trim())
                            putExtra("UserPassword",loginPassword2.text.toString().trim())
                        }
                        startActivity(intent)
                        finish()
                    }
                    else{
                        progress.visibility = View.INVISIBLE
                        Toast.makeText(applicationContext,"Password does not match!", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    progress.visibility = View.INVISIBLE
                    Toast.makeText(applicationContext,"UserName does not Exist!", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                progress.visibility = View.INVISIBLE
                Toast.makeText(applicationContext,it.toString(), Toast.LENGTH_SHORT).show()
            }
            progress.visibility = View.INVISIBLE
        }
    }
}