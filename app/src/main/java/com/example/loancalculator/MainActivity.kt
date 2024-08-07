package com.example.loancalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.loancalculator.R.id.bt_nextpg
import com.example.loancalculator.R.id.tx_date

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateBtn = findViewById<Button>(bt_nextpg)
        dateBtn.setOnClickListener {
            val dateText = findViewById<EditText>(tx_date).text.toString()
            val age = dateText.toIntOrNull()

            if (dateText.isEmpty()) {
                Toast.makeText(this, "Please fill in your age!", Toast.LENGTH_SHORT).show()
            } else if (age == null || age < 21 || age > 60) {
                Toast.makeText(this, "Please enter an age between 21 to 60", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Submitted successfully!", Toast.LENGTH_SHORT).show()
                val intent1 = Intent(this, MainActivity2::class.java)
                startActivity(intent1)
            }
        }
    }
}
