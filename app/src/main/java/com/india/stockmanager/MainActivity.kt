package com.india.stockmanager

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.india.stockmanager.adapters.AlphaAdaptors
import com.india.stockmanager.model.AlphaChar

class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var arrayList:ArrayList<AlphaChar>? = null
    private var alphaAdaptors: AlphaAdaptors? = null
    var auth: FirebaseAuth? = FirebaseAuth.getInstance()
    lateinit var root:DatabaseReference
    //  arrayListfinal.add(AlphaChar(uri, title,count))
   // companion object {
        var arrayListfinal: ArrayList<AlphaChar> = ArrayList()
   // }
   lateinit var uName:DatabaseReference
    var uNameStr:String = "null"
    var uPassStr:String = "null"
    override fun onBackPressed() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure!")
        builder.setMessage("Do you want to close the app?")
        builder.setPositiveButton("Yes", { dialogInterface: DialogInterface, i: Int -> finish() })
        builder.setNegativeButton("No", { dialogInterface: DialogInterface, i: Int -> })
        builder.show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolBar))

        val inflater:View = layoutInflater.inflate(R.layout.custom_loading_dialog,null)
        val imageClick = AlertDialog.Builder(this).setView(inflater)
        val mAlertDialog = imageClick.show()
        if(auth!!.currentUser!=null)
        {
            uName = FirebaseDatabase.getInstance().getReference()
            uName.child("Users").child(auth!!.currentUser.uid).get().addOnSuccessListener {
                if(it.exists())
                {
                    uNameStr = it.child("userName").value.toString()
                    uPassStr = it.child("passWord").value.toString()
                    root = FirebaseDatabase.getInstance().getReference().child("data").child(uNameStr)

                    val btnfab = findViewById<FloatingActionButton>(R.id.fab)
                    btnfab.setOnClickListener {
                        val intent = Intent(applicationContext, AddStock::class.java).apply {
                            putExtra("UserName",uNameStr)
                        }
                        //Toast.makeText(applicationContext,uNameStr,Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }

                    recyclerView = findViewById(R.id.dataList)
                    gridLayoutManager =
                            GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)

                    recyclerView?.layoutManager = gridLayoutManager
                    recyclerView?.setHasFixedSize(false)

                    arrayList = ArrayList()
                    arrayList = setDataInList()

                    alphaAdaptors = AlphaAdaptors(applicationContext, arrayList!!,object :AlphaAdaptors.RecyclerViewClickListener{
                        override fun OnLongClickListener(position: Int) {
                            Toast.makeText(applicationContext,"OnLongClick Adaptor"+position,Toast.LENGTH_SHORT).show()
                           var builder = AlertDialog.Builder(this@MainActivity)
                            builder.setTitle("Are you sure!")
                            builder.setMessage("Do you want to Delete the stock?")
                            builder.setPositiveButton("Yes", { dialogInterface: DialogInterface, i: Int ->  removevk(position)})
                            builder.setNegativeButton("No", { dialogInterface: DialogInterface, i: Int -> })
                            builder.show()
                        }
                        private fun removevk(pos:Int) {
                            root.child(arrayList!!.get(pos).title).removeValue()
                        }
                    })
                    recyclerView?.adapter = alphaAdaptors

                    root.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            // arrayListfinal = ArrayList()
                            arrayListfinal.clear()

                            for (datasnapshot: DataSnapshot in snapshot.children) {
                                if(datasnapshot.getValue()!= uPassStr) {
                                   // Log.d("SnapShotData",datasnapshot.getValue().toString())
                                    val alpha: AlphaChar? = datasnapshot.getValue(AlphaChar::class.java)
                                    if (alpha != null) {
                                        arrayListfinal.add(alpha)
                                        alphaAdaptors!!.notifyDataSetChanged()
                                    }
                                }

                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")

                            mAlertDialog.dismiss()
                        }
                    })
                    mAlertDialog.dismiss()

                }
                else{
                    Toast.makeText(applicationContext,"User does not Exist!",Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(applicationContext,"Reopen again",Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }

        }
        else{

            uPassStr = intent.getStringExtra("UserPassword")!!
            uNameStr = intent.getStringExtra("UserName")!!
            root = FirebaseDatabase.getInstance().getReference().child("data").child(uNameStr)

            val btnfab = findViewById<FloatingActionButton>(R.id.fab)
            btnfab.setOnClickListener {
                val intent = Intent(applicationContext, AddStock::class.java).apply {
                    putExtra("UserName",uNameStr)
                }
                //Toast.makeText(applicationContext,uNameStr,Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }

            recyclerView = findViewById(R.id.dataList)
            gridLayoutManager =
                    GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)

            recyclerView?.layoutManager = gridLayoutManager
            recyclerView?.setHasFixedSize(false)

            arrayList = ArrayList()
            arrayList = setDataInList()
            alphaAdaptors = AlphaAdaptors(applicationContext, arrayList!!,object :AlphaAdaptors.RecyclerViewClickListener{

                override fun OnLongClickListener(position: Int) {
                    Toast.makeText(applicationContext,"Need Owner Acces to delete Stock",Toast.LENGTH_SHORT).show()
                }

            })
            recyclerView?.adapter = alphaAdaptors


            root.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // arrayListfinal = ArrayList()
                    arrayListfinal.clear()
                    for (datasnapshot: DataSnapshot in snapshot.children) {
                        if(datasnapshot.getValue()!= uPassStr) {
                            val alpha: AlphaChar? = datasnapshot.getValue(AlphaChar::class.java)
                            if (alpha != null) {
                                arrayListfinal.add(alpha)
                                alphaAdaptors!!.notifyDataSetChanged()
                            }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                    mAlertDialog.dismiss()

                }
            })
            mAlertDialog.dismiss()

        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val menuItem = menu!!.findItem(R.id.searchBar)
            val searchView = menuItem!!.actionView as androidx.appcompat.widget.SearchView
            searchView.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchRecord(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchRecord(newText)
                    return true
                }

            })

        return super.onCreateOptionsMenu(menu)
    }

    private fun searchRecord(query: String?) {
        var recordList:ArrayList<AlphaChar> = ArrayList()
       // var charSequence:CharSequence = query.toString()
        for (alpha: AlphaChar in arrayListfinal) {
                if(alpha.title.contains(query.toString(),true))
                {
                    recordList.add(alpha)
                }
        }

        val adaptor:AlphaAdaptors = AlphaAdaptors(applicationContext,recordList!!,object :AlphaAdaptors.RecyclerViewClickListener{
            override fun OnLongClickListener(position: Int) {

            }
        })
        recyclerView?.adapter = adaptor

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       // val snack = Snackbar.make(applicationContext,"This is a simple Snackbar",Snackbar.LENGTH_LONG)
        when(item.itemId) {
            R.id.item1 -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.item2 ->{
                if(auth!!.currentUser!=null){

                    var userName:String = uNameStr
                    var password:String = uPassStr
                    val intent = Intent(applicationContext, SettingActivity::class.java).apply {
                        putExtra("userName",userName)
                        putExtra("password",password)
                    }
                    startActivity(intent)
                }
                else{
                    Toast.makeText(applicationContext, "Need owner Access!", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            else-> {
                Toast.makeText(applicationContext, "No item is clicked", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun setDataInList(): ArrayList<AlphaChar> {
        return arrayListfinal
    }

   /* override fun onResume() {
        super.onResume()

        Log.d("StockVairam", arrayListfinal.size.toString())
        recyclerView = findViewById(R.id.dataList)
        gridLayoutManager =
            GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(false)

        arrayList = ArrayList()
        arrayList = setDataInList()
        alphaAdaptors = AlphaAdaptors(applicationContext, arrayList!!)
        recyclerView?.adapter = alphaAdaptors

    }*/


}


