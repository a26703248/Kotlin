package com.example.app_tickets_firebase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*

class AdminLoginActivity : AppCompatActivity() {
    lateinit var context : Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
        context = this
        title = "後台登入"
    }

    fun consoleLogin(view: View){
        val userName = et_username.text.toString()
        if(userName == TicketsStock.admin){
            val intent = Intent(context, Console::class.java)
            startActivity(intent)
        }
    }
}