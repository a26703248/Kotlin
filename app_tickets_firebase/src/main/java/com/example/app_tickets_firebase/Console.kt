package com.example.app_tickets_firebase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
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
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_console)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Admin後臺控制"
        context = this
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                // 統計資料累計
                var sumAllTickets = 0
                var sumOneWay = 0
                var sumRoundTrip = 0
                var sumTotal = 0

                children.forEach {
                    when(it.key.toString()) {
                        "discount" -> firebaseDiscount = it.value.toString().toDouble()
                        "price" -> firebasePirce = it.value.toString().toInt()
                        "totalAmount" -> firebaseAmount = it.value.toString().toInt()
                        // 訂單明細
                        "order" ->{
                            it.children.forEach{// 訂單人名
                                it.children.forEach{// 訂票日期
                                    it.children.forEach {
                                        when(it.key.toString()){// 項目
                                            "allTickets" -> sumAllTickets += it.value.toString().toInt()
                                            "oneWay" -> sumOneWay += it.value.toString().toInt()
                                            "roundTrip" -> sumRoundTrip += it.value.toString().toInt()
                                            "total" -> sumTotal += it.value.toString().toInt()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                selectFirbase()
                // 統計資料
                tv_stat.text = "總賣票數: ${String.format("%,d",sumAllTickets)} 張\n" +
                        "總單程票: ${String.format("%,d",sumOneWay)} 張\n" +
                        "總來回票: ${String.format("%,d",sumRoundTrip*2)} 張(${String.format("%,d",sumRoundTrip)} 組)\n" +
                        "總銷售金額: ${String.format("%,d",sumTotal)} 元"
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // groupId(分組):0,itemId:1,order(順序):10,title:"訂單細目"
        menu?.add(0,1,10,"訂單細目")?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        menu?.add(0,2,20,"返回")?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            1 -> {
                val intent = Intent(context, OrderListActivity::class.java)
                startActivity(intent)
            } // "訂單細目"
            2 -> finish() // "離開"
        }
        return super.onOptionsItemSelected(item)
    }
}