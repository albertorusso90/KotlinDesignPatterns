package com.albertorusso.kotlinDesignPatterns.creationalPatterns

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * The Singleton pattern is a creational design pattern that ensures a class has only one instance and provides a global point of access to that
 * instance. It is often used when exactly one object is needed to coordinate actions across the system, such as managing configuration settings,
 * logging, driver objects, and other scenarios where a single instance is appropriate.
 *
 * The Singleton pattern involves a class that is responsible for instantiating itself and maintaining a reference to the created instance. If no
 * instance has been created, it creates one; otherwise, it returns the existing instance.
 */

object Singleton {
    
    init {
        println("Initializing: $this")
    }
    
    fun log(): Singleton = apply { println("Singleton: $this") }
}

class SingletonTest {
    
    @Test
    fun testSingleton() {
        println("Start")
        
        val singleton1 = Singleton.log()
        val singleton2 = Singleton.log()
        
        Assertions.assertThat(singleton1).isSameAs(Singleton)
        Assertions.assertThat(singleton2).isSameAs(Singleton)
    }
    
}
