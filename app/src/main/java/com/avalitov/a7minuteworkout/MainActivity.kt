package com.avalitov.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast

lateinit var llStart : LinearLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llStart = findViewById(R.id.ll_start)

        llStart.setOnClickListener() {
            Toast.makeText(this, "Here we will start the excercise", Toast.LENGTH_SHORT).show()
        }
    }
}