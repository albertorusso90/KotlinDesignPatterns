package com.albertorusso.kotlinDesignPatterns.structuralPatterns

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 *
 * The Composite pattern is a structural design pattern that lets you compose objects into tree structures to represent part-whole hierarchies.
 * It allows clients to treat individual objects and compositions of objects uniformly.
 *
 * In the Composite pattern, you have the following key components:
 *
 * #1 Component: This is the interface or abstract class that declares the common interface for all concrete classes, both leaf and composite.
 * #2 Leaf: This is the concrete class that implements the Component interface. It represents the leaf nodes in the tree structure. Leafs have no
 * children.
 * #3 Composite: This is the concrete class that implements the Component interface as well. However, unlike leaf nodes, composites can have child
 * components (both leafs and other composites). Composites forward the request to their child components.
 *
 * The main idea behind the Composite pattern is that clients can treat individual objects and compositions of objects uniformly. This simplifies the
 * client code, as it doesn't need to distinguish between leaf and composite objects.
 */

open class Equipment(
    open val price: Int,
    val name: String
)

open class Composite(name: String): Equipment(0, name) {
    private val equipments = ArrayList<Equipment>()
    
    override val price: Int
        get() = equipments.sumOf { it.price }
    
    fun add(equipment: Equipment) = apply { equipments.add(equipment) }
}

class Computer: Composite("PC")
class Processor: Equipment(1000, "Processor")
class HardDrive: Equipment(250, "Hard Drive")
class Memory: Composite("Memory")
class ROM: Equipment(100, "Read Only Memory")
class RAM: Equipment(75, "Random Access Memory")

class CompositeTest {
    @Test
    fun testComposite() {
        val memory = Memory()
            .add(ROM())
            .add(RAM())
        
        val pc = Computer()
            .add(memory)
            .add(Processor())
            .add(HardDrive())
        
        println("PC price: ${pc.price}")
        
        Assertions.assertThat(pc.name).isEqualTo("PC")
        Assertions.assertThat(pc.price).isEqualTo(1425)
    }
}
