package com.albertorusso.kotlinDesignPatterns.behaviouralPatterns

import org.junit.jupiter.api.Test

/**
 * The Command pattern is a behavioral design pattern that turns a request into a standalone object. This object contains all the information
 * about the request, allowing for parameterization of clients with different requests, queuing of requests, and logging of the requests. It also
 * provides the ability to undo the operations.
 *
 * Here are the key components of the Command pattern:
 *
 * #1 Command: This is an interface or abstract class that declares an execute method. Concrete command classes implement this interface,
 * encapsulating the details of a request.
 *
 * #2 ConcreteCommand: These are the concrete implementations of the Command interface. Each ConcreteCommand class is associated with a specific
 * operation and contains the information required to execute that operation.
 *
 * #3 Invoker: This is an object that requests a command to perform an operation. The invoker does not need to know the details of the concrete
 * command; it just invokes the execute method on the command object.
 *
 * #4 Receiver: This is an object that performs the actual work associated with a request. It knows how to carry out the operation.
 *
 * #5 Client: This is the object that creates and configures the command objects and associates them with the invoker.
 */


interface Command {
    fun execute()
}

class OrderAddCommand(private val id: Long): Command {
    override fun execute() {
        println("Adding order with id $id")
    }
}

class OrderPayCommand(private val id: Long): Command {
    override fun execute() {
        println("Paying for order with id $id")
    }
}

class CommandProcessor {
    private val queue = ArrayList<Command>()
    
    fun addToQueue(command: Command): CommandProcessor = apply { queue.add(command) }
    
    fun processCommands(): CommandProcessor = apply {
        queue.forEach { it.execute() }
        queue.clear()
    }
}

class CommandTest {
    @Test
    fun testCommand() {
        CommandProcessor()
            .addToQueue(OrderAddCommand(1L))
            .addToQueue(OrderAddCommand(2L))
            .addToQueue(OrderPayCommand(2L))
            .addToQueue(OrderPayCommand(1L))
            .processCommands()
    }
}
