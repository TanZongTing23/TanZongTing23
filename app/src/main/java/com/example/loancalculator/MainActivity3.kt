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

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val button4 = findViewById<Button>(R.id.bt_back)
        button4.setOnClickListener {
            Toast.makeText(this, "Back button clicked", Toast.LENGTH_SHORT).show()
            val intent3 = Intent(this, MainActivity2::class.java)
            startActivity(intent3)
        }

        val amountEditText: EditText = findViewById(R.id.line2)
        val interestRateEditText: EditText = findViewById(R.id.line4)
        val numInstallmentsEditText: EditText = findViewById(R.id.line6)
        val startDateEditText: EditText = findViewById(R.id.line8)
        val calculateButton: Button = findViewById(R.id.bt_calculate)
        val monthlyInstallmentTextView: TextView = findViewById(R.id.tx_outputanswer)
        val lastPaymentDateTextView: TextView = findViewById(R.id.line10)
        val totalAmountTextView: TextView = findViewById(R.id.line12)

        calculateButton.setOnClickListener {
            Toast.makeText(this, "Calculate button clicked", Toast.LENGTH_SHORT).show()
            val amount = amountEditText.text.toString().toDoubleOrNull()
            val interestRate = interestRateEditText.text.toString().toDoubleOrNull()
            val numInstallments = numInstallmentsEditText.text.toString().toIntOrNull()
            val startDate = startDateEditText.text.toString()

            if (amount != null && interestRate != null && numInstallments != null && startDate.isNotEmpty()) {
                val monthlyInstallment = calculateMonthlyInstallment(amount, interestRate, numInstallments)
                val lastPaymentDate = calculateLastPaymentDate(startDate, numInstallments)
                val totalAmount = calculateTotalAmount(amount, interestRate, numInstallments)

                monthlyInstallmentTextView.text = "Monthly Installment: $%.2f".format(monthlyInstallment)
                lastPaymentDateTextView.text = "Last Payment Date: $lastPaymentDate"
                totalAmountTextView.text = "Total Amount: $%.2f".format(totalAmount)

                // Pass data to MainActivity5
                val intent = Intent(this, MainActivity5::class.java)
                intent.putExtra("loanAmount", amount)
                intent.putExtra("interestRate", interestRate)
                intent.putExtra("months", numInstallments)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculateMonthlyInstallment(amount: Double, interestRate: Double, numInstallments: Int): Double {
        val monthlyInterestRate = (interestRate / 100) / 12
        return (amount * (1 + (monthlyInterestRate * numInstallments))) / numInstallments
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
