package com.example.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    var state by mutableStateOf(CalculatorState())
    var result by mutableStateOf("")


    private val MAX_NUM_LENGTH = 15

    fun onAction(action: CalculatorAction) {
        when (action) {
            CalculatorAction.Calculate -> evaluate()
            CalculatorAction.Clear -> clearArea()
            CalculatorAction.Decimal -> performDecimal()
            CalculatorAction.Delete -> performDeletion()
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Operation -> enterOperation(action.operation)
        }
    }


    private fun clearArea() {
        state=state.copy(number1 = "",number2 = "",operation = null)
        result=""
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.number1.isNotBlank()) {
            state = state.copy(operation = operation)

        }
    }

    private fun evaluate():String {
        val number1: Double? = state.number1.toDoubleOrNull()
        val number2: Double? = state.number1.toDoubleOrNull()
        if (number1 != null && number2 != null) {
             result =
                when (state.operation) {
                    CalculatorOperation.Addition -> performAddition().toString()
                    CalculatorOperation.Subtraction -> performSubstraction().toString()
                    CalculatorOperation.Times -> performMultiplication().toString()
                    CalculatorOperation.Divide -> performDevision().toString()
                    null -> {
                        ""
                    }
                }
        }
        return ""
    }


    private fun performDeletion() {
        when {
            state.number2.isNotBlank() -> {
                state = state.copy(number2 = state.number2.dropLast(1))

            }

            state.operation != null -> {
                state = state.copy(operation = null)
            }

            state.number1.isNotBlank() -> {
                state = state.copy(number1 = state.number1.dropLast(1))

            }
        }
    }

    private fun performDecimal() {

        if (state.operation == null) {
            if (!state.number1.contains(".")
                && state.number1.isNotBlank()
            ) {
                state = state.copy(number1 = state.number1 + ".")
                return
            } else if (!state.number2.contains(".")
                && state.number2.isNotBlank()
            ) {
                state = state.copy(number2 = state.number2 + ".")
                return
            }


        }

    }

    private fun performMultiplication(): Double {
       return state.number1.toDouble() * state.number2.toDouble()
    }

    private fun performSubstraction(): Double {
        return state.number1.toDouble() - state.number2.toDouble()
    }

    private fun performDevision(): Double {
        return state.number1.toDouble() / state.number2.toDouble()
    }

    private fun performAddition(): Double {
        return state.number1.toDouble() + state.number2.toDouble()
    }

    private fun enterNumber(number: Int) {
        if (state.operation == null) {

            if (state.number1.length >= MAX_NUM_LENGTH) {
                return
            }
            state = state.copy(number1 = state.number1 + number)

        } else {
            if (state.number1.length >= MAX_NUM_LENGTH) {
                return
            }
            state = state.copy(number2 = state.number2 + number)

        }
    }


}