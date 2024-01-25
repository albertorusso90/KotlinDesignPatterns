package com.albertorusso.kotlinDesignPatterns.structuralPatterns

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * The Bridge pattern is a structural design pattern that separates the abstraction from its implementation so that both can vary independently.
 * It involves creating a bridge interface that contains the abstraction, and the implementation is then provided by concrete classes.
 *
 * The Bridge pattern is used when you want to avoid a permanent binding between an abstraction and its implementation. It allows you to change both
 * the abstraction and the implementation independently, making the system more flexible and extensible.
 *
 * Here are the key components of the Bridge pattern:
 *
 * Abstraction: This is an interface or abstract class that defines the high-level functionality and maintains a reference to an implementation object.
 *
 * RefinedAbstraction: This is a class that extends the abstraction, adding more details or additional features.
 *
 * Implementation: This is an interface or abstract class that defines the low-level functionality. It is implemented by concrete classes.
 *
 * ConcreteImplementation: These are the concrete implementations of the implementation interface. They provide specific implementations for the
 * low-level functionality.
 */

interface Device {
    var volume: Int
    fun getName(): String
}

class Radio: Device {
    override var volume = 0
    override fun getName() = "Radio $this"
}

class TV: Device {
    override var volume = 0
    override fun getName() = "TV $this"
}

interface Remote {
    fun volumeUp()
    fun volumeDown()
}

class BasicRemote(private val device: Device): Remote {
    override fun volumeUp() {
        device.volume++
        println("${device.getName()} volume up: ${device.volume}")
    }
    
    override fun volumeDown() {
        device.volume--
        println("${device.getName()} volume down: ${device.volume}")
    }
}

class BridgeTest {
    @Test
    fun testBridge() {
        val tv = TV()
        val radio = Radio()
        
        val tvRemote = BasicRemote(tv)
        val radioRemote = BasicRemote(radio)
        
        tvRemote.volumeUp()
        tvRemote.volumeUp()
        tvRemote.volumeDown()
    
        radioRemote.volumeUp()
        radioRemote.volumeUp()
        radioRemote.volumeUp()
        radioRemote.volumeDown()
        
        Assertions.assertThat(tv.volume).isEqualTo(1)
        Assertions.assertThat(radio.volume).isEqualTo(2)
    }
}
