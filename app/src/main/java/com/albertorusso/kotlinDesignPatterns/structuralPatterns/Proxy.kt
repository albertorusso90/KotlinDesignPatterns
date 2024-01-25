package com.albertorusso.kotlinDesignPatterns.structuralPatterns

import org.junit.jupiter.api.Test

/**
 * The Proxy pattern is a structural design pattern that provides a surrogate or placeholder for another object to control access to it. In other
 * words, a proxy acts as an intermediary, controlling access to an object, and can be used for various purposes such as lazy loading,
 * access control, logging, monitoring, etc.
 *
 * There are different types of proxies, and their roles can vary based on the requirements of the system. Here are some common types of proxies:
 *
 * #1 Virtual Proxy: A virtual proxy is used for lazy loading. It postpones the creation and initialization of a resource-intensive object until it
 * is actually needed.
 * #2 Protection Proxy: A protection proxy controls access to a sensitive object. It acts as a gatekeeper to decide whether the real object can be
 * accessed by the client.
 * #3 Cache Proxy: A cache proxy stores the results of expensive operations and returns the cached result when the same operation is requested again,
 * instead of redoing the entire operation.
 * #4 Remote Proxy: A remote proxy represents an object that is in a different address space (e.g., on a remote server). It acts as a local
 * representative or surrogate for the remote object.
 */

interface Image {
    fun display()
}

class RealImage(private val filename: String): Image {
    override fun display() {
        println("RealImage: Displaying $filename")
    }
    
    private fun loadFromDisk(filename: String) {
        println("RealImage: Loading $filename")
    }
    
    init {
        loadFromDisk(filename)
    }
}

class ProxyImage(private val filename: String): Image {
    private var realImage: RealImage? = null
    
    override fun display() {
        println("ProxyImage: Displaying $filename")
        if(realImage == null) {
            realImage = RealImage(filename)
        }
        
        realImage!!.display()
    }
}

class ProxyTest {
    @Test
    fun testProxy() {
        val image = ProxyImage("test.jpg")
        
        // load image from disk
        image.display()
        println("------------------")
        
        // load image from "cache"
        image.display()
    }
}

