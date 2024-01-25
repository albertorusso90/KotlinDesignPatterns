package com.albertorusso.kotlinDesignPatterns.creationalPatterns

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * The Prototype pattern is a creational design pattern that allows you to create new objects by copying an existing object, known as the prototype.
 * Instead of creating a new object through a constructor, the pattern involves creating new instances by copying an existing instance, which serves
 * as a prototype.
 *
 * The key idea behind the Prototype pattern is to hide the complexities of making new instances from the client and enable the creation of new
 * objects by copying an existing object. This can be useful when the cost of creating a new object is more expensive than copying an existing
 * one, or when the new objects need to be customized based on an existing prototype.
 *
 * Here are the key components of the Prototype pattern:
 *
 * Prototype: This is an interface or an abstract class that declares the methods for cloning itself. The prototype defines a common interface
 * for all concrete prototypes.
 *
 * ConcretePrototype: These are the concrete implementations of the prototype interface. Each concrete prototype implements the cloning method to
 * create a new instance by copying itself.
 *
 * Client: This is the class that creates new objects by copying the existing prototype. The client interacts with the prototype interface
 * without being aware of the specific concrete prototype class.
 */

abstract class Shape: Cloneable {
    var id: String? = null
    var type: String? = null
    
    abstract fun draw()
    
    public override fun clone(): Any {
        var clone: Any? = null
        try {
            clone = super.clone()
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        
        return clone!!
    }
}

class Rectangle: Shape() {
    override fun draw() {
        println("Inside Rectangle::draw() method.")
    }
    
    init {
        type = "Rectangle"
    }
}

class Square: Shape() {
    override fun draw() {
        println("Inside Square::draw() method.")
    }
    
    init {
        type = "Square"
    }
}

class Circle: Shape() {
    override fun draw() {
        println("Inside Circle::draw() method.")
    }
    
    init {
        type = "Circle"
    }
}

object ShapeCache {
    private val shapeMap = hashMapOf<String?, Shape>()
    
    fun loadCache() {
        val circle = Circle()
        val square = Square()
        val rectangle = Rectangle()
    
        shapeMap["1"] = circle
        shapeMap["2"] = square
        shapeMap["3"] = rectangle
    }
    
    fun getShape(shapeId: String): Shape {
        val cachedShape = shapeMap[shapeId]
        return cachedShape?.clone() as Shape
    }
}

class PrototypeTest {
    @Test
    fun testPrototype() {
        ShapeCache.loadCache()
        val cloneShape1 = ShapeCache.getShape("1")
        val cloneShape2 = ShapeCache.getShape("2")
        val cloneShape3 = ShapeCache.getShape("3")
        
        cloneShape1.draw()
        cloneShape2.draw()
        cloneShape2.draw()
        
        Assertions.assertThat(cloneShape1.type).isEqualTo("Circle")
        Assertions.assertThat(cloneShape2.type).isEqualTo("Square")
        Assertions.assertThat(cloneShape3.type).isEqualTo("Rectangle")
    }
}


