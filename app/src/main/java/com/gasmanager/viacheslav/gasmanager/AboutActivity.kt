package com.gasmanager.viacheslav.gasmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar!!.hide()

    }
}
