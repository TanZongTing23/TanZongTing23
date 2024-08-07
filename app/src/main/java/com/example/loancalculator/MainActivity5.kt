package com.example.loancalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

class MainActivity5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        val tableLayout: TableLayout = findViewById(R.id.amortizationTable)
        val backBtn: Button = findViewById(R.id.backToCalculatorBtn)

        val loanAmountGenerate: TextView = findViewById(R.id.loanAmountGenerate)
        val interestGenerate: TextView = findViewById(R.id.interestGenerate)
        val loanTenureGenerate: TextView = findViewById(R.id.loanTenureGenerate)

        // Receive data from the intent
        val principal = intent.getDoubleExtra("loanAmount", 0.0)
        val interestRate = intent.getDoubleExtra("interestRate", 0.0)
        val months = intent.getIntExtra("months", 0)

        // Display data
        loanAmountGenerate.text = String.format("Loan amount (RM): %.2f", principal)
        interestGenerate.text = String.format("Interest rate (%% per annum): %.2f", interestRate)
        loanTenureGenerate.text = String.format("Number of Repayments (months): %d", months)

        val amortizationSchedule = generateAmortization(principal, interestRate, months)

        for (month in amortizationSchedule) {
            val row = TableRow(this)
            val monthView = TextView(this)
            val beginningBalanceView = TextView(this)
            val interestPaidView = TextView(this)
            val principalPaidView = TextView(this)
            val monthlyPaymentView = TextView(this)

            monthView.text = month.month.toString()
            beginningBalanceView.text = String.format("%.2f", month.beginningBalance)
            interestPaidView.text = String.format("%.2f", month.interestPaid)
            principalPaidView.text = String.format("%.2f", month.principalPaid)
            monthlyPaymentView.text = String.format("%.2f", month.monthlyPayment)

            row.addView(monthView)
            row.addView(beginningBalanceView)
            row.addView(interestPaidView)
            row.addView(principalPaidView)
            row.addView(monthlyPaymentView)
            tableLayout.addView(row)
        }

        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun generateAmortization(principal: Double, interestRate: Double, months: Int): List<AmortizationMonth> {
        val amortizationSchedule = mutableListOf<AmortizationMonth>()
        val monthlyRate = (interestRate / 100) / 12
        val monthlyPayment = (principal + (principal * (interestRate / 100))) / months
        val interestPaid = principal * monthlyRate
        val principalPaid = monthlyPayment - interestPaid

        var remainingPrincipal = principal

        for (i in 1..months) {
            val beginningBalance = remainingPrincipal
            remainingPrincipal -= principalPaid

            amortizationSchedule.add(AmortizationMonth(i, beginningBalance, interestPaid, principalPaid, monthlyPayment))
        }

        return amortizationSchedule
    }

    data class AmortizationMonth(
        val month: Int,
        val beginningBalance: Double,
        val interestPaid: Double,
        val principalPaid: Double,
        val monthlyPayment: Double
    )
}
