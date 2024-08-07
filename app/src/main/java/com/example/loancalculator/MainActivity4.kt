package com.example.loancalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.pow

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val button5 = findViewById<Button>(R.id.bt_back2)
        button5.setOnClickListener {
            val intent4 = Intent(this, MainActivity2::class.java)
            startActivity(intent4)
        }

        val button7 = findViewById<Button>(R.id.bt_gnrate2)
        button7.setOnClickListener {
            Toast.makeText(this, "Convert button clicked", Toast.LENGTH_SHORT).show()
            val intent6 = Intent(this, MainActivity6::class.java)
            startActivity(intent6)
        }

        val amountEditText: EditText = findViewById(R.id.line22)
        val interestRateEditText: EditText = findViewById(R.id.line44)
        val numInstallmentsEditText: EditText = findViewById(R.id.line66)
        val startDateEditText: EditText = findViewById(R.id.line88)
        val calculateButton: Button = findViewById(R.id.Calculatebutton)
        val monthlyInstallmentTextView: TextView = findViewById(R.id.tx_outputanswer2)
        val lastPaymentDateTextView: TextView = findViewById(R.id.line100)
        val totalAmountTextView: TextView = findViewById(R.id.line122)

        calculateButton.setOnClickListener {
            Toast.makeText(this, "Calculate button clicked", Toast.LENGTH_SHORT).show()
            val amount = amountEditText.text.toString().toDoubleOrNull()
            val interestRate = interestRateEditText.text.toString().toDoubleOrNull()
            val numInstallments = numInstallmentsEditText.text.toString().toIntOrNull()
            val startDate = startDateEditText.text.toString()

            if (amount != null && interestRate != null && numInstallments != null && startDate.isNotEmpty()) {
                val monthlyInstallment = calculateMonthlyInstallment(amount, interestRate, numInstallments)
                val lastPaymentDate = calculateLastPaymentDate(startDate, numInstallments)
                val totalAmount = calculateTotalAmount(amount, interestRate, numInstallments) // Calculate total amount

                monthlyInstallmentTextView.text = "Monthly Installment: $%.2f".format(monthlyInstallment)
                lastPaymentDateTextView.text = "Last Payment Date: $lastPaymentDate"
                totalAmountTextView.text = "Total Amount: $%.2f".format(totalAmount)

                val intent = Intent(this, MainActivity6::class.java)
                intent.putExtra("loanAmount", amount)
                intent.putExtra("interestRate", interestRate)
                intent.putExtra("months", numInstallments)
                startActivity(intent)

            } else {
                Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculateMonthlyInstallment(
        amount: Double,
        interestRate: Double,
        numInstallments: Int
    ): Double {
        val monthlyInterestRate = (interestRate / 100) / 12
        return if (monthlyInterestRate == 0.0) {
            amount / numInstallments // Handle zero interest rate
        } else {
            val factor = (1 + monthlyInterestRate).pow(numInstallments)
            (amount * monthlyInterestRate * factor) / (factor - 1)
        }
    }

    private fun calculateLastPaymentDate(startDate: String, numInstallments: Int): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.parse(startDate)
        return if (date != null) {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.MONTH, numInstallments)
            sdf.format(calendar.time)
        } else {
            null
        }
    }

    private fun calculateTotalAmount(amount: Double, interestRate: Double, numInstallments: Int): Double {
        val monthlyInstallment = calculateMonthlyInstallment(amount, interestRate, numInstallments)
        return monthlyInstallment * numInstallments
    }
}
