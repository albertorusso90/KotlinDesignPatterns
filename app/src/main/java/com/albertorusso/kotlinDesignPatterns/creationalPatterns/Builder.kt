package com.albertorusso.kotlinDesignPatterns.creationalPatterns

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * The Builder pattern is a creational design pattern that provides a way to construct a complex object step by step. It allows you to produce
 * different types and representations of an object using the same construction process. The key idea behind the Builder pattern is to separate the
 * construction of a complex object from its representation.
 *
 * Here are the key components of the Builder pattern:
 *
 * Director: This is responsible for orchestrating the construction process using a builder. The director is not aware of the specifics of the
 * concrete products but knows the steps involved in their construction.
 *
 * Builder: This is an interface or an abstract class that declares the construction steps for building a product. Each concrete builder provides
 * its implementation for constructing a specific type of product.
 *
 * ConcreteBuilder: These are the classes that implement the builder interface and provide specific steps for constructing the product. Each concrete
 * builder is associated with a particular type of product.
 *
 * Product: This is the complex object being constructed. It often contains a variety of parts, and its representation can vary based on the
 * builder used.
 */

class Component private constructor(builder: Builder) {
    var param1: String? = null
    var param2: Int? = null
    var param3: Boolean? = null
    
    class Builder {
        private var param1: String? = null
        private var param2: Int? = null
        private var param3: Boolean? = null
        
        fun setParam1(param1: String) = apply { this.param1 = param1 }
        fun setParam2(param2: Int) = apply { this.param2 = param2 }
        fun setParam3(param3: Boolean) = apply { this.param3 = param3 }
        
        fun build() = Component(this)
        
        fun getParam1() = param1
        fun getParam2() = param2
        fun getParam3() = param3
    }
    
    init {
        param1 = builder.getParam1()
        param2 = builder.getParam2()
        param3 = builder.getParam3()
    }
}

class ComponentTest {
    @Test
    fun builderTest() {
        val component = Component.Builder()
            .setParam1("Some value")
            .setParam3(true)
            .build()
        
        println(component)
        println(component.param1)
        println(component.param3)
        
        Assertions.assertThat(component.param1).isEqualTo("Some value")
        Assertions.assertThat(component.param3).isEqualTo(true)
        Assertions.assertThat(component.param2).isEqualTo(null)
    }
}
