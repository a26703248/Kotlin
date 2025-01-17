package com.example.arduino_iot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    val database = Firebase.database
    val buzeerRef = database.getReference("buzeer")
    val cdsRef = database.getReference("cds")
    val dht11Ref = database.getReference("dht11")
    val doorRef = database.getReference("door")
    val ledRef = database.getReference("led")
    val logRef = database.getReference("log")
    val face = database.getReference("face")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intRef()
        openWeatherOnClick(btn_message)
    }

    fun openWeatherOnClick(view: View) {
        GlobalScope.launch {
            val client = OkHttpClient()
            val area = "Taoyuan"
            val country = "TW"
            val key = resources.getString(R.string.open_weather_key)
            var base_url = resources.getString(R.string.open_weather_base_url)
            base_url = String.format(base_url, area, country, key)
            Log.d("MainActivity", base_url)

            val request = Request.Builder()
                .url(base_url)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("MainActivity", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    val json = response.body?.string()
                    Log.d("MainActivity", json.toString())
                    val gson = Gson()
                    val root = JsonParser.parseString(json).asJsonObject
                    val weather = root.getAsJsonArray("weather")[0].asJsonObject
                    val main = root.getAsJsonObject("main").asJsonObject
                    var icon_url = resources.getString(R.string.open_weather_icon_url)
                    icon_url = String.format(icon_url, weather.get("icon").asString)
                    Log.d("MainActivity", icon_url)
                    // UI 配置
                    runOnUiThread {
                        Picasso.get().load(icon_url).into(ib_icon);
                        btn_message.text   = weather.get("description").asString
                        tv_temp.text       = String.format("%.1f °C", main.get("temp").asDouble - 273.15)
                        tv_feels_temp.text = String.format("%.1f °C", main.get("feels_like").asDouble - 273.15)
                        tv_humi.text       = main.get("humidity").toString() + " %"
                    }
                }

            })

        }
    }


    fun intRef() {

        ledRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ib_led_red.setImageResource(R.drawable.grey)
                ib_led_green.setImageResource(R.drawable.grey)
                ib_led_yellow.setImageResource(R.drawable.grey)
                val value = snapshot.value.toString().toInt()
                when(value) {
                    1 -> ib_led_green.setImageResource(R.drawable.greenlight)
                    2 -> ib_led_red.setImageResource(R.drawable.redlight)
                    3 -> ib_led_yellow.setImageResource(R.drawable.yellowlight)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        buzeerRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value.toString().toInt()
                if(value == 1) {
                    ib_warnning.setImageResource(R.drawable.buzzeropen)
                } else {
                    ib_warnning.setImageResource(R.drawable.buzzerclose)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        doorRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value.toString().toInt()
                if(value == 1) {
                    ib_door.setImageResource(R.drawable.opendoor)
                } else {
                    ib_door.setImageResource(R.drawable.closedoor)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        cdsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value.toString()
                tv_house_cds.text = value
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        dht11Ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val humi_value = snapshot.child("humi").value.toString()
                val temp_value = snapshot.child("temp").value.toString()
                tv_house_humi.text = humi_value + " %"
                tv_house_temp.text = temp_value + " °C"
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        logRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.child("data").value.toString()
                tv_log.text = value
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("face")?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.title.equals("face")){
            face.setValue(1)
        }
        return super.onOptionsItemSelected(item)
    }
}