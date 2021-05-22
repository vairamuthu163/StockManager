package com.india.stockmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ChangeOwnerPassword : AppCompatActivity() {
    var auth: FirebaseUser = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_owner_password)

        val oldPasswordl:TextInputLayout = findViewById(R.id.oldPasswordl)
        val newPasswordl:TextInputLayout = findViewById(R.id.newPasswordl)
        val confirmPassl:TextInputLayout = findViewById(R.id.confirmPassl)

        val confirmPass:TextInputEditText = findViewById(R.id.confirmPass)
        val oldPassword:TextInputEditText = findViewById(R.id.oldPassword)
        val newPassword:TextInputEditText = findViewById(R.id.newPassword)

        val changePassBtn = findViewById<Button>(R.id.changePassBtn)
        val cancelPassBtn = findViewById<Button>(R.id.cancelPassBtn)

        val prog: ProgressBar = findViewById(R.id.progressBar)
        prog.visibility = View.INVISIBLE

        oldPassword.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                oldPasswordl.isErrorEnabled = false
            }
        })
        newPassword.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                newPasswordl.isErrorEnabled = false
            }
        })
        confirmPass.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                confirmPassl.isErrorEnabled = false
            }
        })
        changePassBtn.setOnClickListener {
            if(TextUtils.isEmpty(oldPassword.text.toString().trim()))
            {
                oldPasswordl.error = "Please enter old Password!"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(newPassword.text.toString().trim()))
            {
                newPasswordl.error = "Please enter new Password!"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(confirmPass.text.toString().trim()))
            {
                confirmPassl.error = "Please enter confirm Password!"
                return@setOnClickListener
            }
            if(!newPassword.text.toString().trim().equals(confirmPass.text.toString().trim()))
            {
                confirmPassl.error = "Password Mismatch!"
                return@setOnClickListener
            }
            prog.visibility = View.VISIBLE
            var authCred: AuthCredential =
                EmailAuthProvider.getCredential(auth.email, oldPassword.text.toString().trim())

            auth.reauthenticate(authCred).addOnCompleteListener {
                if (it.isSuccessful) {
                    auth.updatePassword(newPassword.text.toString().trim()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            prog.visibility = View.INVISIBLE
                            Toast.makeText(
                                applicationContext,
                                "Password Updated!",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            prog.visibility = View.INVISIBLE
                            Toast.makeText(
                                applicationContext,
                                "Update Error Try again!!",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                } else {
                    prog.visibility = View.INVISIBLE
                    Toast.makeText(applicationContext, "Old Password Mismatch", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        cancelPassBtn.setOnClickListener {
            finish()
        }

    }
}