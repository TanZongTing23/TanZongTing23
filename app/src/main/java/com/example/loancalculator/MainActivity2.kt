package com.example.loancalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        var button2 = findViewById<Button>(R.id.tx_personalLoan)
        button2.setOnClickListener(){
            val Intent2 = Intent(this, MainActivity3::class.java)
            startActivity(Intent2)
        }

        var button3 = findViewById<Button>(R.id.tx_housingLoan)
        button3.setOnClickListener(){
            val Intent3 = Intent(this, MainActivity4::class.java)
            startActivity(Intent3)
        }
    }
}