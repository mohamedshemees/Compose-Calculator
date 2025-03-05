package com.example.calculator

sealed class CalculatorAction {
    data class Number(val number: Int) : CalculatorAction()
    object Clear : CalculatorAction()
    object Delete : CalculatorAction()
    object Decimal : CalculatorAction()
    object Calculate : CalculatorAction()
    data class Operation(val operation: CalculatorOperation) : CalculatorAction() // Uses separate enum class
}

// Separate Enum for Operations
enum class CalculatorOperation(val symbol: String) {
    Addition("+"),
    Subtraction("-"),
    Times("×"),
    Divide("/")
}