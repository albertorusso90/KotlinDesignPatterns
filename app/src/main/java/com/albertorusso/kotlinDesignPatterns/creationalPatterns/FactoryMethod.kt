package com.albertorusso.kotlinDesignPatterns.creationalPatterns

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * The Factory Method pattern is a creational design pattern that provides an interface for creating objects in a superclass but allows subclasses to
 * alter the type of objects that will be created. It defines an interface for creating an object, but the exact class of the object can be
 * determined at runtime by the subclass.
 *
 * Here are the key components of the Factory Method pattern:
 *
 * Product: This is the interface or abstract class for the objects created by the factory method.
 *
 * ConcreteProduct: These are the concrete implementations of the product interface.
 *
 * Creator: This is the interface or abstract class that declares the factory method for creating objects. It may also provide a default
 * implementation of the factory method that creates a default product.
 *
 * ConcreteCreator: These are the concrete implementations of the creator class. Each concrete creator overrides the factory method to create a
 * specific type of product.
 */

sealed class Country {
    
    object Canada: Country()
}

object Spain: Country()
class Greece(val someProperty: String): Country()
data class USA(val someProperty: String): Country()
//class Poland: Country()

class Currency (val code: String)

object CurrencyFactory{
    fun currencyForCountry(country: Country): Currency =
        when(country) {
            is Spain -> Currency("EUR")
            is Greece -> Currency("EUR")
            is USA -> Currency("USD")
            is Country.Canada -> Currency("CAD")
        }
}

class FactoryMethodTest {
    @Test
    fun currencyTest() {
        val greekCurrency = CurrencyFactory.currencyForCountry(Greece("")).code
        println("Greek currency: $greekCurrency")
        
        val usaCurrency = CurrencyFactory.currencyForCountry(USA("")).code
        println("USA currency: $usaCurrency")
        
        Assertions.assertThat(greekCurrency).isSameAs("EUR")
        Assertions.assertThat(usaCurrency).isSameAs("USD")
        
    }
}
