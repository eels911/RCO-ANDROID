package com.example.android.rco_android

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar

const val APP_PREFERNCES = "APP_PREFERNCES"
const val PREF_SOME_TEXT_VALUE = "PREF_SOME_TEXT_VALUE"
class MainActivity : AppCompatActivity() {
    var sharedPreference:SharedPreference? = null
  private lateinit var preference:SharedPreferences
  private val preferencesListener = SharedPreferences.OnSharedPreferenceChangeListener{_,key->
    if( key == PREF_SOME_TEXT_VALUE){
        tv_login.text = preference.getString(key," ")
    }
  }

    lateinit var edt_email: EditText
    lateinit var edt_password: EditText
    lateinit var btn_login: Button
    lateinit var btn_signin: Button
    lateinit var tv_login:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)

        sharedPreference =SharedPreference(this)
        edt_email = findViewById(R.id.et_email)
        edt_password = findViewById(R.id.et_password)
        btn_login = findViewById(R.id.btn_login)
        btn_signin = findViewById(R.id.btn_signIn)
        tv_login = findViewById(R.id.tv_login)
        preference = getSharedPreferences(APP_PREFERNCES, Context.MODE_PRIVATE)
        val currentValue = preference.getString(PREF_SOME_TEXT_VALUE, "")
        edt_email.setText(currentValue)
        tv_login.text = currentValue
        val str_login_status = sharedPreference!!.getPreferenceString("login_status")
        if (str_login_status!=null){
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

//        btn_login.setOnClickListener {
//            val value = edt_email.text.toString()
//            preference.edit()
//                .putString(PREF_SOME_TEXT_VALUE,value)
//                .apply()
            btn_login.setOnClickListener {
                val str_email_id = edt_email.text.toString()
                val str_password = edt_password.text.toString()

                if (str_email_id.equals("") || str_password.equals("")) {
                    Toast.makeText(this, "Please Enter Details.", Toast.LENGTH_SHORT).show()
                } else {
                    val email_id = sharedPreference!!.getPreferenceString("email_id")
                    val password = sharedPreference!!.getPreferenceString("password")

                    if (email_id.equals(str_email_id) && password.equals(str_password)) {
                        sharedPreference!!.save_String("login_status", "1")
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "User Not Available.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        btn_signin.setOnClickListener {
            fun_SignUp_PopupWindow()
        }
    }
    private fun fun_SignUp_PopupWindow() {

        val layoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popView: View = layoutInflater.inflate(R.layout.sign_up_window, null)

        val popupWindow: PopupWindow
        popupWindow = PopupWindow(popView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT,true)
        popupWindow.showAtLocation(popView, Gravity.CENTER, 0, 0)

        val btn_dia_submit = popView.findViewById(R.id.btn_dia_submit) as Button
        btn_dia_submit.setOnClickListener {

            val edt_dia_email_id = popView.findViewById<EditText>(R.id.edt_dia_email_id)
            val edt_dia_password = popView.findViewById<EditText>(R.id.edt_dia_password)
            val str_dia_email_id = edt_dia_email_id.text.toString()
            val str_dia_password = edt_dia_password.text.toString()

            if(str_dia_email_id.equals("") || str_dia_password.equals("")){
                Toast.makeText(this,"Please Enter Details.",Toast.LENGTH_SHORT).show()
            }else {
                popupWindow.dismiss()
                sharedPreference!!.save_String("email_id",str_dia_email_id)
                sharedPreference!!.save_String("password",str_dia_password)
                Toast.makeText(this,"Data Saved Successfully.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        preference.unregisterOnSharedPreferenceChangeListener(preferencesListener)
    }

}