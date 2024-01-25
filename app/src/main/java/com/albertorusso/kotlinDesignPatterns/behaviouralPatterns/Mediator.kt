package com.albertorusso.kotlinDesignPatterns.behaviouralPatterns

import org.junit.jupiter.api.Test

/**
 * The Mediator pattern is a behavioral design pattern that defines an object (the mediator) that encapsulates the interaction between a set of
 * objects (colleagues). It promotes loose coupling by centralizing communication between objects, preventing them from directly referring to
 * each other.
 *
 * In a system using the Mediator pattern, objects don't communicate directly with each other; instead, they communicate through the mediator. This
 * helps reduce the dependencies between objects and makes the system more maintainable and flexible.
 *
 * Here are the key components of the Mediator pattern:
 *
 * Mediator: This is an interface or an abstract class that declares the communication interface between the colleagues. It defines methods for
 * communication and typically keeps references to all colleague objects.
 *
 * ConcreteMediator: This is the concrete implementation of the mediator interface. It knows about and coordinates the communication between colleagues.
 *
 * Colleague: This is an interface or an abstract class that declares the communication methods. Colleagues interact with each other through the mediator.
 *
 * ConcreteColleague: These are the concrete implementations of the colleague interface. Each colleague class knows its mediator and communicates
 * with other colleagues through the mediator.
 */

class ChatUser(private val mediator: Mediator, private val name: String) {
    fun send(msg: String) {
        println("$name: Sending message: $msg")
        mediator.sendMessage(msg, this)
    }
    
    fun receive(msg: String) {
        println("$name: Received message: $msg")
    }
}

class Mediator {
    private val users = arrayListOf<ChatUser>()
    
    fun sendMessage(msg: String, user: ChatUser){
        users.filter { it != user }
            .forEach { it.receive(msg) }
    }
    
    fun addUser(user: ChatUser): Mediator = apply { users.add(user) }
}

class MediatorTest {
    @Test
    fun testMediator() {
        val mediator = Mediator()
        val alice = ChatUser(mediator, "Alice")
        val bob = ChatUser(mediator, "Bob")
        val carol = ChatUser(mediator, "Carol")
        
        mediator.addUser(alice)
            .addUser(bob)
            .addUser(carol)
        
        carol.send("Hi everyone")
    }
}
