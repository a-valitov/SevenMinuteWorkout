package com.avalitov.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

lateinit var llStart : LinearLayout
lateinit var llBMI : LinearLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llStart = findViewById(R.id.ll_start)
        llBMI = findViewById(R.id.ll_BMI)

        llStart.setOnClickListener() {
            //Toast.makeText(this, "Here we will start the exercise", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        llBMI.setOnClickListener() {
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }

        ll_History.setOnClickListener() {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}