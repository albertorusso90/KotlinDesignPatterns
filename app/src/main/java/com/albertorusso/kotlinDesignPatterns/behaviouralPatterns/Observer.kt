package com.albertorusso.kotlinDesignPatterns.behaviouralPatterns

import org.junit.jupiter.api.Test
import java.io.File

/**
 * The Observer pattern is a behavioral design pattern where an object, known as the subject, maintains a list of dependents, known as observers,
 * that are notified of any changes in the subject's state. This pattern defines a one-to-many dependency between objects so that when one object
 * changes state, all its dependents are notified and updated automatically.
 *
 * Here are the key participants in the Observer pattern:
 *
 * - Subject: This is the object that is being observed. It maintains a list of observers and provides methods to attach, detach, and notify observers.
 * - Observer: This is the interface or abstract class that defines the updating interface for objects that should be notified of changes in the
 * subject's state.
 * - ConcreteSubject: This is the concrete implementation of the subject. It keeps track of its observers and notifies them of any state changes.
 * - ConcreteObserver: This is the concrete implementation of the observer. It implements the updating interface to receive and respond to
 *  notifications from the subject.
 */

interface EventListener {
    fun update(eventType: String?, file: File?)
}

class EventManager(vararg operations: String) {
    private var listeners = hashMapOf<String, ArrayList<EventListener>>()
    
    init {
        for (operation in operations) {
            listeners[operation] = ArrayList()
        }
    }
    
    fun subscribe(eventType: String?, listener: EventListener) {
        val users = listeners[eventType]
        users?.add(listener)
    }
    
    fun unsubscribe(eventType: String?, listener: EventListener) {
        val users = listeners[eventType]
        users?.remove(listener)
    }
    
    fun notify(eventType: String?, file: File?) {
        val users = listeners[eventType]
        users?.let {
            for (listener in it) {
                listener.update(eventType, file)
            }
        }
    }
}

class Editor {
    var events: EventManager = EventManager("open", "save")
    
    private var file: File? = null
    
    fun openFile(filePath: String) {
        file = File(filePath)
        events.notify("open", file)
    }
    
    fun saveFile() {
        file?.let {
            events.notify("save", file)
        }
    }
}

class EmailNotificationListener(private val email: String): EventListener {
    override fun update(eventType: String?, file: File?) {
        println("Email to $email: Someone has performed $eventType operation with the file ${file?.name}")
    }
}

class LogOpenListener(private var filename: String): EventListener {
    override fun update(eventType: String?, file: File?) {
        println("Save to log $filename: Someone has performed $eventType operation with the file ${file?.name}")
    }
}

class ObserverTest {
    @Test
    fun testObserver() {
        val editor = Editor()
        
        val logListener = LogOpenListener("path/to/log/file.txt")
        val emailListener = EmailNotificationListener("test@test.com")
        
        editor.events.subscribe("open", logListener)
        editor.events.subscribe("open", emailListener)
        editor.events.subscribe("save", emailListener)
        
        editor.openFile("test.txt")
        editor.saveFile()
    }
}
