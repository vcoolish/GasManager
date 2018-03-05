package com.gasmanager.viacheslav.gasmanager

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView


class AdditionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.hide()
        setContentView(R.layout.activity_addition)
        val btn = findViewById(R.id.addbtn) as ImageView

        btn.setOnClickListener {
            val intent = Intent(this, CarAddition::class.java)
            startActivity(intent)
        }
    }
}
