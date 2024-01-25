package com.albertorusso.kotlinDesignPatterns.behaviouralPatterns

import org.junit.jupiter.api.Test
import java.util.Locale

/**
 * The Strategy pattern is a behavioral design pattern that defines a family of algorithms, encapsulates each algorithm, and makes them interchangeable.
 * It allows the client to choose the appropriate algorithm at runtime without altering the client's code.
 *
 * Here are the key components of the Strategy pattern:
 *
 * #1 Strategy: This is an interface or abstract class that defines a family of algorithms. Each concrete strategy class implements this interface,
 * providing a specific algorithm.
 *
 * #2 ConcreteStrategy: These are the concrete implementations of the strategy interface. Each concrete strategy represents a different algorithm.
 *
 * #3 Context: This is the class that uses the strategy. It has a reference to a strategy object and can switch between different strategies at
 * runtime.
 */

class Printer(private val stringFormatterStrategy: (String) -> String) {
    fun printString(message: String) {
        println(stringFormatterStrategy(message))
    }
}

val lowerCaseFormatter = {it: String -> it.lowercase(Locale.getDefault()) }
val upperCaseFormatter = {it: String -> it.uppercase(Locale.getDefault()) }


class StrategyTest {
    @Test
    fun testStrategy() {
        val inputString = "LOREM ipsum DOLOR sit amet"
        
        val lowerCasePrinter = Printer(lowerCaseFormatter)
        lowerCasePrinter.printString(inputString)
        
        println("----------------")
        
        val upperCasePrinter = Printer(upperCaseFormatter)
        upperCasePrinter.printString(inputString)
    }
}
