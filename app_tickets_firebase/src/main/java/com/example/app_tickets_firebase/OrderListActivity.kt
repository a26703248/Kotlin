package com.example.app_tickets_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_order_list.*

class OrderListActivity : AppCompatActivity() {

    val database = Firebase.database
    val myRef = database.getReference("ticketsstock")
    lateinit var userName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        //取得主頁傳來的參數資料
        userName = intent.getStringExtra("userName").toString()
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                children.forEach{
                    when(it.key.toString()) {
                        "order" -> {
                            tv_info.setText(it.child(userName).toString())
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}