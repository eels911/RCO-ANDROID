package com.example.android.rco_android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    var sharedPreference:SharedPreference? = null
    lateinit var btn_log_out: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        sharedPreference =SharedPreference(this)

        btn_log_out = findViewById(R.id.btn_log_out)
        btn_log_out.setOnClickListener {

            sharedPreference!!.clearSharedPreference()
            Toast.makeText(this,"User LogOut Successfully.",Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
