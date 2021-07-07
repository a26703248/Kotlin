package com.example.app_tickets_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_console.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_order_list.*

class Console : AppCompatActivity() {

    val database = Firebase.database
    val myRef = database.getReference("ticketsstock")
    var firebasePirce = 0
    var firebaseDiscount = 0.0
    var firebaseAmount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_console)
        val userName = "Engineer"
        title = "Hi " + userName + "後臺控制"
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                children.forEach {
                    when(it.key.toString()) {
                        "discount" -> firebaseDiscount = it.value.toString().toDouble()
                        "price" -> firebasePirce = it.value.toString().toInt()
                        "totalAmount" -> firebaseAmount = it.value.toString().toInt()
                    }
                }
                selectFirbase()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun selectFirbase(){
        ed_console_price.setText(firebasePirce.toString())
        ed_console_discount.setText((firebaseDiscount * 10).toString())
        ed_console_amount.setText(firebaseAmount.toString())
    }

    fun updateFirebase(view: View){
        var price = ed_console_price.text.toString().toInt()
        var discount = (ed_console_discount.text.toString().toDouble() / 10.0)
        var totalAmount = ed_console_amount.text.toString().toInt()
        myRef.child("price").setValue(price)
        myRef.child("discount").setValue(discount)
        myRef.child("totalAmount").setValue(totalAmount)
    }
}