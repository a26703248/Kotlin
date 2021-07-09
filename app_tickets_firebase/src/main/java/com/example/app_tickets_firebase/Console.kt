package com.example.app_tickets_firebase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.nio.charset.Charset

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
                // 個別購買者的統計資料
                var statListByUser = mutableListOf<Map<String,Int>>()
                children.forEach {
                    when(it.key.toString()) {
                        "discount" -> firebaseDiscount = it.value.toString().toDouble()
                        "price" -> firebasePirce = it.value.toString().toInt()
                        "totalAmount" -> firebaseAmount = it.value.toString().toInt()
                        // 訂單明細
                        "order" ->{
                            it.children.forEach{// 訂單人名
                                //
                                var mapUser = mutableMapOf<String,Int>()
                                val mapUserName = it.key.toString()
                                mapUser.put(mapUserName,0)
                                it.children.forEach{// 訂票日期
                                    it.children.forEach {
                                        when(it.key.toString()){// 項目
                                            "allTickets" -> sumAllTickets += it.value.toString().toInt()
                                            "oneWay" -> sumOneWay += it.value.toString().toInt()
                                            "roundTrip" -> sumRoundTrip += it.value.toString().toInt()
                                            "total" -> {
                                                sumTotal += it.value.toString().toInt()
                                                mapUser.put(mapUserName,mapUser.get(mapUserName)!! + it.value.toString().toInt())
                                            }
                                        }
                                    }
                                }
                                statListByUser.add(mapUser)
                            }
                        }
                    }
                }
                Log.d("Console",statListByUser.toString())
                selectFirbase()
                // 統計資料
                tv_stat.text = "總賣票數: ${String.format("%,d",sumAllTickets)} 張\n" +
                        "總單程票: ${String.format("%,d",sumOneWay)} 張\n" +
                        "總來回票: ${String.format("%,d",sumRoundTrip*2)} 張(${String.format("%,d",sumRoundTrip)} 組)\n" +
                        "總銷售金額: ${String.format("%,d",sumTotal)} 元"
                // 載錄圖表
                loadChart(statListByUser)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    fun loadChart(statListByUser: List<Map<String,Int>>) {
        var rowDataByChart: String = ""
        statListByUser.forEach {
            val key = it.keys.iterator().next()
            val value = it[key]
            rowDataByChart += "['$key', $value],"
        }
        Log.d("ConsoleActivity", rowDataByChart)

        var webSettings =  web_view.settings;
        webSettings.setJavaScriptEnabled(true); // 啟用 Javascript
        webSettings.setBuiltInZoomControls(true); // 啟用 Zoom
        var asset_path = "file:///android_asset/";
        var html = getHtml("chart.html");
        html = String.format(html!!, rowDataByChart)
        web_view.loadDataWithBaseURL(asset_path, html!!, "text/html", "utf-8", null);
        web_view.requestFocusFromTouch();
    }

    // 取得 html 內容字串
    private fun getHtml(filename: String): String? {
        var html: String? = null
        try {
            val `in`: InputStream = assets.open(filename)
            val out = ByteArrayOutputStream()
            val buffer = ByteArray(`in`.available())
            `in`.read(buffer) // 讀出
            out.write(buffer) // 寫入
            html = String(out.toByteArray(), Charset.forName("UTF-8"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return html
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
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            1 -> { // "訂單細目"
                val intent = Intent(context, OrderListActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}