package com.example.mycalnew.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DecimalFormat

/**
 * ViewModel handling calculator logic. It keeps the display string and processes button clicks.
 */
class MainViewModel : ViewModel() {
    private val _display = MutableLiveData("0")
    val display: LiveData<String> = _display

    private var operand1: Double? = null
    private var pendingOperator: String? = null
    private var lastInputWasOperator = false
    private val decimalFormat = DecimalFormat("#.##########") // limit to 10 decimal places

    /**
     * Called by the UI when a button is pressed.
     */
    fun onButtonClick(label: String) {
        when (label) {
            "C" -> clearAll()
            "⌫" -> backspace()
            "=" -> calculateResult()
            "/", "*", "-", "+" -> handleOperator(label)
            else -> handleDigitOrDot(label)
        }
    }

    private fun clearAll() {
        _display.value = "0"
        operand1 = null
        pendingOperator = null
        lastInputWasOperator = false
    }

    private fun backspace() {
        val current = _display.value ?: return
        if (current.isNotEmpty() && current != "0") {
            val newText = if (current.length == 1) "0" else current.dropLast(1)
            _display.value = newText
        }
    }

    private fun handleDigitOrDot(digit: String) {
        var current = _display.value ?: "0"
        if (lastInputWasOperator) {
            current = "0"
            lastInputWasOperator = false
        }
        if (digit == ".") {
            if (!current.contains('.')) {
                _display.value = "$current."
            }
        } else {
            if (current == "0") {
                _display.value = digit
            } else {
                _display.value = current + digit
            }
        }
    }

    private fun handleOperator(op: String) {
        val currentValue = _display.value?.toDoubleOrNull() ?: return
        if (operand1 == null) {
            operand1 = currentValue
        } else if (pendingOperator != null && !lastInputWasOperator) {
            // chain calculations
            operand1 = performOperation(operand1!!, currentValue, pendingOperator!!)
            _display.value = decimalFormat.format(operand1)
        }
        pendingOperator = op
        lastInputWasOperator = true
    }

    private fun calculateResult() {
        val currentValue = _display.value?.toDoubleOrNull() ?: return
        if (operand1 != null && pendingOperator != null) {
            val result = performOperation(operand1!!, currentValue, pendingOperator!!)
            _display.value = decimalFormat.format(result)
            // reset state for next calculation
            operand1 = null
            pendingOperator = null
            lastInputWasOperator = false
        }
    }

    private fun performOperation(a: Double, b: Double, op: String): Double {
        return when (op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> if (b != 0.0) a / b else Double.NaN
            else -> b
        }
    }
}
