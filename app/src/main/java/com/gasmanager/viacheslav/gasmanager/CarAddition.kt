package com.gasmanager.viacheslav.gasmanager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.ImageView
import io.realm.Realm
import java.util.*


class CarAddition : AppCompatActivity() {

    val MY_SETTINGS = "my_settings"
    val MY_CAR = "MY_CAR"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_car_addition)
        Realm.init(this)
        val mRealm: Realm = Realm.getDefaultInstance()
        val sp1 = getSharedPreferences(MY_CAR, Context.MODE_PRIVATE)
        val sp = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE)

        val edit1 = findViewById(R.id.edit1) as EditText
        val edit2 = findViewById(R.id.edit2) as EditText
        val edit3 = findViewById(R.id.edit3) as EditText

        val addbtn = findViewById(R.id.addbtn) as ImageView

        addbtn.setOnClickListener {
            val e = sp.edit();
            e.putBoolean("hasVisited", true);
            e.apply();
            val model = edit1.text.toString()
            val odo = edit2.text.toString()
            val ok = sp1.edit();
            val tank = edit3.text.toString()
            ok.putString("Model", model)
            ok.putString("odometer", odo)
            ok.putString("tank", tank)
            ok.putLong("id", 0)
            ok.apply()

            mRealm.beginTransaction()
            val md = MyData(0, Date().getTime(), odo.toDouble(), 0.0, 0.0, 0.0, 0.0)
            mRealm.insert(md)
            mRealm.commitTransaction()
            mRealm.close()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
