package com.india.stockmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Process
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ChangeUserName : AppCompatActivity() {
    lateinit var root: DatabaseReference
    var auth: FirebaseAuth? = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_user_name)

        var UserName = intent.getStringExtra("userName")!!
        var Password = intent.getStringExtra("password")

        var loginUserNamel2:TextInputLayout = findViewById(R.id.loginUserNamel2)
        var loginPasswordl2:TextInputLayout = findViewById(R.id.loginPasswordl2)

        var loginUserName2:TextInputEditText = findViewById(R.id.loginUserName2)
        var loginPassword2:TextInputEditText = findViewById(R.id.loginPassword2)

        val changeBtn = findViewById<Button>(R.id.changeBtn)
        val cancelBtn = findViewById<Button>(R.id.cancelBtn)

        val progressBarChange:ProgressBar = findViewById(R.id.progressBarChange)
        progressBarChange.visibility = View.INVISIBLE

        loginUserName2.text = UserName.toEditable()
        if(!Password.equals("#fghs#hsd&c%s"))
        {
            loginPassword2.text = Password!!.toEditable()
        }
        else{
            changeBtn.text = "Set Password"
        }
        //loginUserName2.focusable = true
        changeBtn.setOnClickListener {
            progressBarChange.visibility = View.VISIBLE
            root = FirebaseDatabase.getInstance().getReference().child("data").child(UserName)
            root.child("PaSsWoRd").setValue(loginPassword2.text.toString().trim())
            root = FirebaseDatabase.getInstance().getReference().child("Users").child(auth!!.currentUser.uid)
            root.child("passWord").setValue(loginPassword2.text.toString().trim())
            progressBarChange.visibility = View.INVISIBLE
        }
        cancelBtn.setOnClickListener {
            finish()
        }
    }
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}