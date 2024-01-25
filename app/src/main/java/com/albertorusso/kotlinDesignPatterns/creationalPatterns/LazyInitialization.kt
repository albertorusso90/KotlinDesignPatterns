package com.albertorusso.kotlinDesignPatterns.creationalPatterns

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Lazy Initialization is a design pattern that defers the creation or initialization of an object until the point at which it is actually needed.
 * This pattern is particularly useful when the instantiation of an object is resource-intensive or when it's unnecessary to create the
 * object until it's required.
 *
 * The key idea behind lazy initialization is to postpone the instantiation of an object until the first time it is accessed. This can improve the
 * performance and efficiency of a system by avoiding unnecessary resource consumption for objects that might not be used.
 */

class AlertBox {
    var message: String? = null
    
    fun show() {
        println("AlertBox $this: $message")
    }
}

/**
 * You can do late initialize in kotlin by using lazy on "val"
 */
class Window {
    val box by lazy { AlertBox() }
    
    fun showMessage(message: String) {
        box.message = message
        box.show()
    }
}

/**
 * You can do late initialize in kotlin by using "lateinit var"
 */
class Window2 {
    lateinit var box: AlertBox
    
    fun showMessage(message: String) {
        box = AlertBox()
        box.message = message
        box.show()
    }
}


class WindowTest {
    @Test
    fun windowTest() {
        val window = Window()
        window.showMessage("Hello window")
        Assertions.assertThat(window.box).isNotNull
        
        val window2 = Window2()
//        println(window2.box)
        window2.showMessage("Second window")
        Assertions.assertThat(window2.box).isNotNull
    }
}
