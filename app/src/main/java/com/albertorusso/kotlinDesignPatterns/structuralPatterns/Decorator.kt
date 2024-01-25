package com.albertorusso.kotlinDesignPatterns.structuralPatterns

import org.junit.jupiter.api.Test

/**
 * The Decorator pattern is a structural design pattern that allows behavior to be added to an individual object, either statically or dynamically,
 * without affecting the behavior of other objects from the same class. It is a flexible alternative to subclassing for extending functionality.
 *
 * In the Decorator pattern, you have the following key components:
 *
 * #1 Component: This is the interface or abstract class defining the interface for objects that can have responsibilities added to them dynamically.
 *
 * #2 ConcreteComponent: This is the class that implements the Component interface. It is the base class to which additional responsibilities can be
 * added.
 *
 * #3 Decorator: This is an abstract class that also implements the Component interface. It has a reference to a Component object and may define
 * additional behavior. The Decorator class has the same interface as the Component class, ensuring that it can be used interchangeably
 * with the ConcreteComponent.
 *
 * #4 ConcreteDecorator: This is the class that extends the Decorator class. It adds new responsibilities or modifies existing ones.
 */

interface CoffeeMachine {
    fun makeSmallCoffee()
    fun makeLargeCoffee()
}

class NormalCoffeeMachine: CoffeeMachine {
    override fun makeSmallCoffee() {
        println("Normal coffee machine: Making small coffee")
    }
    
    override fun makeLargeCoffee() {
        println("Normal coffee machine: Making large coffee")
    }
}

//Decorator
class EnhancedCoffeeMachine(private val coffeeMachine: CoffeeMachine): CoffeeMachine by coffeeMachine {
    
    // Overriding behaviour
    override fun makeLargeCoffee() {
        println("Enhanced coffee machine: Making large coffee")
    }
    
    // Extending behaviour
    fun makeMilkCoffee() {
        println("Enhanced coffee machine: Making milk coffee")
        coffeeMachine.makeSmallCoffee()
        println("Enhanced coffee machine: Adding milk")
    }
}

class DecoratorTest {
    @Test
    fun testDecorator() {
        val normalMachine = NormalCoffeeMachine()
        val enhancedCoffeeMachine = EnhancedCoffeeMachine(normalMachine)
        
        enhancedCoffeeMachine.makeSmallCoffee()
        println("----------------------")
        enhancedCoffeeMachine.makeLargeCoffee()
        println("----------------------")
        enhancedCoffeeMachine.makeMilkCoffee()
    }
}

