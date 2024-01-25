package com.albertorusso.kotlinDesignPatterns.behaviouralPatterns

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * The Memento pattern is a behavioral design pattern that provides a mechanism to capture and externalize an object's internal state so that the
 * object can be restored to this state later. It allows you to take a snapshot of an object's state, and then restore the object to that state when
 * needed, without exposing its internal details.
 *
 * Here are the key components of the Memento pattern:
 *
 * Originator: This is the object whose state needs to be saved and restored. The originator creates a memento object containing a snapshot of its
 * internal state and can use the memento to restore its state.
 *
 * Memento: This is an object that stores the state of the originator. It has two interfaces: a narrow one that only allows the originator to access
 * the state and a wide one that allows other objects to access the state.
 *
 * CareTaker: This is an object that keeps track of multiple mementos. It doesn't modify or even look inside the mementos but is responsible for
 * storing and retrieving them.
 */

data class Memento(val state: String)

class Originator(var state: String) {
    fun createMemento() = Memento(state)
    
    fun restoreMemento(memento: Memento) {
        state = memento.state
    }
}

class CareTaker {
    private val mementoList = arrayListOf<Memento>()
    
    fun saveState(state: Memento) {
        mementoList.add(state)
    }
    
    fun restore(index: Int): Memento {
        return mementoList[index]
    }
}

class MementoTest {
    @Test
    fun testMemento() {
        val originator = Originator(state = "initial state")
        val careTaker = CareTaker()
        careTaker.saveState(originator.createMemento())
        println("Current state is ${originator.state}")
        
        originator.state = "State 1"
        careTaker.saveState(originator.createMemento())
        println("Current state is ${originator.state}")
        
        originator.state = "State 2"
        careTaker.saveState(originator.createMemento())
        println("Current state is ${originator.state}")
        
        Assertions.assertThat(originator.state).isEqualTo("State 2")
        
        originator.restoreMemento(careTaker.restore(1))
        println("Current state is ${originator.state}")
        Assertions.assertThat(originator.state).isEqualTo("State 1")
        
        originator.restoreMemento(careTaker.restore(0))
        println("Current state is ${originator.state}")
        Assertions.assertThat(originator.state).isEqualTo("initial state")
        
        originator.restoreMemento(careTaker.restore(2))
        println("Current state is ${originator.state}")
        Assertions.assertThat(originator.state).isEqualTo("State 2")
    }
}
