package com.quinndaisies.calculatorapplication

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import com.quinndaisies.calculatorapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding : ActivityMainBinding
    private var number : String? = null

    private var firstNumber : Double = 0.0
    private var lastNumber : Double = 0.0
    private var status : String? = null
    private var operator : Boolean = false
    private var myFormatter = DecimalFormat("######.######")

    private var history : String = ""
    private var currentResult : String = ""

    private var dotControl : Boolean = true
    private var buttonEqualsControl : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        // Default Digit in the Application
        mainBinding.textViewResult.text = "0"

        // Calling The Button
        mainBinding.btnZero.setOnClickListener {
            onNumberClicked("0")
        }

        mainBinding.btnNine.setOnClickListener {
            onNumberClicked("9")
        }

        mainBinding.btnEight.setOnClickListener {
            onNumberClicked("8")
        }

        mainBinding.btnSeven.setOnClickListener {
            onNumberClicked("7")
        }

        mainBinding.btnSix.setOnClickListener {
            onNumberClicked("6")
        }

        mainBinding.btnFive.setOnClickListener {
            onNumberClicked("5")
        }

        mainBinding.btnFour.setOnClickListener {
            onNumberClicked("4")
        }

        mainBinding.btnThree.setOnClickListener {
            onNumberClicked("3")
        }

        mainBinding.btnTwo.setOnClickListener {
            onNumberClicked("2")
        }

        mainBinding.btnOne.setOnClickListener {
            onNumberClicked("1")
        }

        // Operator Button Functions
        mainBinding.btnDot.setOnClickListener {

            if (dotControl) {

                number = if (number == null) {
                    "0."
                } else if (buttonEqualsControl) {
                    if (mainBinding.textViewResult.text.toString().contains(".")) {
                        mainBinding.textViewResult.text.toString()
                    } else {
                        mainBinding.textViewResult.text.toString().plus(".")
                    }
                } else {
                    "$number."
                }

                mainBinding.textViewResult.text = number
            }

            dotControl = false
        }

        mainBinding.btnEqual.setOnClickListener {
            if (operator) {
                when(status) {
                    "multiplication" -> multiply()
                    "division" -> divide()
                    "subtraction" -> minus()
                    "addition" -> plus()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }

                mainBinding.textViewHistory.text = history.plus(currentResult).plus("=").plus(mainBinding.textViewResult.text.toString())
            }
            operator = false
            dotControl = true
            buttonEqualsControl = true
        }

        mainBinding.btnAC.setOnClickListener {
            onButtonACClicked()
        }

        mainBinding.btnDivide.setOnClickListener {

            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewHistory.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("/")

            if (operator) {
                when(status) {
                    "multiplication" -> multiply()
                    "division" -> divide()
                    "subtraction" -> minus()
                    "addition" -> plus()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }
            }

            status = "division"
            operator = false
            number = null
            operator = true
        }

        mainBinding.btnDel.setOnClickListener {
            number?.let {
                if (it.length == 1) {
                    onButtonACClicked()
                } else {
                    number = it.substring(0,it.length-1)
                    mainBinding.textViewResult.text = number
                    dotControl = !number!!.contains(".")
                }
            }
        }

        mainBinding.btnPlus.setOnClickListener {

            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewHistory.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("+")

            if (operator) {
                when(status) {
                    "multiplication" -> multiply()
                    "division" -> divide()
                    "subtraction" -> minus()
                    "addition" -> plus()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }
            }

            status = "addition"
            operator = false
            number = null
            operator = true
        }

        mainBinding.btnMulti.setOnClickListener {

            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewHistory.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("*")

            if (operator) {
                when(status) {
                    "multiplication" -> multiply()
                    "division" -> divide()
                    "subtraction" -> minus()
                    "addition" -> plus()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }
            }

            status = "multiplication"
            operator = false
            number = null
            operator = true
        }

        mainBinding.btnMinus.setOnClickListener {

            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewHistory.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("-")

            if (operator) {
                when(status) {
                    "multiplication" -> multiply()
                    "division" -> divide()
                    "subtraction" -> minus()
                    "addition" -> plus()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }
            }

            status = "subtraction"
            operator = false
            number = null
            operator = true
        }

        mainBinding.toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.settings_item -> {
                    val intent = Intent(this@MainActivity,ChangeThemeActivity::class.java)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }

                else -> return@setOnMenuItemClickListener false
            }
        }
    }
    private fun onButtonACClicked() {
        number = null
        status = null
        mainBinding.textViewResult.text = "0"
        mainBinding.textViewHistory.text = "0"
        firstNumber = 0.0
        lastNumber = 0.0
        dotControl = true
        buttonEqualsControl = false
    }

    private fun onNumberClicked(clickNumber: String) {
        if (number == null) {
            number = clickNumber
        } else if (buttonEqualsControl) {
            number = if (dotControl) {
                clickNumber
            } else {
                mainBinding.textViewResult.text.toString().plus(clickNumber)
            }

            firstNumber = number!!.toDouble()
            lastNumber = 0.0
            status = null
            mainBinding.textViewHistory.text = ""

        }
        else {
            number += clickNumber
        }

        mainBinding.textViewResult.text = number

        operator = true
        buttonEqualsControl = false

    }

    private fun plus() {
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber += lastNumber
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }

    private fun minus() {
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber -= lastNumber
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }

    private fun multiply() {
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber *= lastNumber
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }

    private fun divide() {
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        if (lastNumber == 0.0) {
            Toast.makeText(applicationContext, "The divisor cannot be Zero", Toast.LENGTH_LONG).show()
        } else {
            firstNumber /= lastNumber
            mainBinding.textViewResult.text = myFormatter.format(firstNumber)
        }
    }
}