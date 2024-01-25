package com.albertorusso.kotlinDesignPatterns.behaviouralPatterns

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * The Visitor pattern is a behavioral design pattern that allows you to define new operations on the elements of an object structure without altering
 * the structures themselves. It is used to separate the algorithm from the object structure on which it operates.
 *
 * In the Visitor pattern, you define a set of visitor classes, each representing a different operation, and then apply these visitors to elements
 * in an object structure. This way, the algorithm can vary independently of the elements it operates on.
 *
 * Here are the key components of the Visitor pattern:
 *
 * Visitor: This is an interface or abstract class that declares a set of visit methods, each corresponding to a different type of element in the
 * object structure. These methods define the operations to be performed on the elements.
 *
 * ConcreteVisitor: These are the concrete implementations of the visitor interface. Each concrete visitor class provides a specific implementation
 * for the visit methods, representing a different operation.
 *
 * Element: This is an interface or abstract class that declares an accept method. This method accepts a visitor as an argument, allowing the visitor
 * to perform its operation on the element.
 *
 * ConcreteElement: These are the concrete implementations of the element interface. Each concrete element class implements the accept method,
 * allowing it to be visited by different types of visitors.
 *
 * ObjectStructure: This is a collection or structure of elements. It provides a way to iterate over its elements and accept visitors. It may or may
 * not expose its internal details to visitors.
 */

interface ReportElement {
    fun <R> accept(visitor: ReportVisitor<R>): R
}

class FixedPriceContract(val costPerYear: Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class TimeAndMaterialsContract(val costPerHour: Long, val hours: Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class SupportContract(val costPerMonth: Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

interface ReportVisitor<out R> {
    fun visit(contract: FixedPriceContract): R
    fun visit(contract: TimeAndMaterialsContract): R
    fun visit(contract: SupportContract): R
}

class MonthlyCostReportVisitor: ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear / 12
    
    override fun visit(contract: TimeAndMaterialsContract): Long = contract.costPerHour * contract.hours
    
    override fun visit(contract: SupportContract): Long = contract.costPerMonth
}

class YearlyReportVisitor: ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear
    
    override fun visit(contract: TimeAndMaterialsContract): Long = contract.costPerHour * contract.hours
    
    override fun visit(contract: SupportContract): Long = contract.costPerMonth * 12
}

class VisitorTest {
    @Test
    fun testVisitor() {
        val projectAlpha = FixedPriceContract(10_000)
        val projectBeta = SupportContract(500)
        val projectGamma = TimeAndMaterialsContract(150, 10)
        val projectKappa = TimeAndMaterialsContract(50, 50)
        
        val project = arrayListOf(projectAlpha, projectBeta, projectGamma, projectKappa)
        
        val monthlyCostVisitor = MonthlyCostReportVisitor()
        val monthlyCost = project.sumOf { it.accept(monthlyCostVisitor) }
        println("Monthly cost: $monthlyCost")
        Assertions.assertThat(monthlyCost).isEqualTo(5333)
        
        val yearlyCostVisitor = YearlyReportVisitor()
        val yearlyCost = project.sumOf { it.accept(yearlyCostVisitor) }
        println("Yearly cost: $yearlyCost")
        Assertions.assertThat(yearlyCost).isEqualTo(20_000)
    }
}
