package com.india.stockmanager

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(findViewById(R.id.toolBar))
        supportActionBar!!.title = "Settings"

        val collaboratorSettingTxt:TextView = findViewById(R.id.collaboratorSettingTxt)
        val changePassTxt:TextView = findViewById(R.id.changePassTxt)
        val deleteAccTxt:TextView = findViewById(R.id.deleteAccTxt)

        val userName:String = intent.getStringExtra("userName")!!
        val password:String = intent.getStringExtra("password")!!
        collaboratorSettingTxt.setOnClickListener {
            val intent = Intent(applicationContext, ChangeUserName::class.java).apply {
                putExtra("userName", userName)
                putExtra("password", password)
            }
            startActivity(intent)
        }
        changePassTxt.setOnClickListener {
            startActivity(Intent(applicationContext, ChangeOwnerPassword::class.java))
        }
        deleteAccTxt.setOnClickListener {
            var builder = AlertDialog.Builder(applicationContext)
            builder.setTitle("Are you sure!")
            builder.setMessage("Do you want to Delete your account? this can't be undone.")
            builder.setPositiveButton("Yes", { dialogInterface: DialogInterface, i: Int -> deleteacc() })
            builder.setNegativeButton("No", { dialogInterface: DialogInterface, i: Int -> })
            builder.show()
        }
    }

    private fun deleteacc() {
        var auth: FirebaseUser = FirebaseAuth.getInstance().currentUser
        var authCred: AuthCredential =
                EmailAuthProvider.getCredential(auth.email, intent.getStringExtra("passmain")!!)
        auth.reauthenticate(authCred).addOnCompleteListener {
            if (it.isSuccessful) {
                auth.delete().addOnCompleteListener {
                    if (it.isSuccessful) {
                        var root: DatabaseReference =FirebaseDatabase.getInstance().getReference()
                        root.child("data").child(intent.getStringExtra("userName")!!).removeValue()
                        root.child("Users").child(intent.getStringExtra("userName")!!).removeValue()
                        val intent = Intent(applicationContext, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        intent.putExtra("EXIT", true)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(applicationContext,"Error Try again!!",Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(applicationContext,"Check Network",Toast.LENGTH_SHORT).show()
            }
        }
    }
}