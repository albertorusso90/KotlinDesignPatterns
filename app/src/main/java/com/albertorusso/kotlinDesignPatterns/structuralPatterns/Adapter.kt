package com.albertorusso.kotlinDesignPatterns.structuralPatterns

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * The Adapter pattern is a structural design pattern that allows the interface of an existing class to be used as another interface. It is often
 * used to make existing classes work with others without modifying their source code.
 *
 * The Adapter pattern involves a single class called the Adapter, which is responsible for joining functionalities of independent or incompatible
 * interfaces. The Adapter acts as a bridge between two incompatible interfaces, allowing them to work together.
 *
 * Here are the key components of the Adapter pattern:
 *
 * Target: This is the interface that the client code expects. It defines the specific methods or functionality that the client code uses.
 *
 * Adapter: This is a class that implements the target interface and wraps an instance of the Adaptee. It translates the calls made to the target
 * interface into calls to the Adaptee.
 *
 * Adaptee: This is the class that has the functionality that needs to be adapted. It has an interface that is incompatible with the target interface.
 *
 */

// 3rd party functionality

data class DisplayDataType(val index: Float, val data: String)

class DataDisplay {
    fun displayData(data: DisplayDataType) {
        println("Data is displayed ${data.index} - ${data.data}")
    }
}

// ----------------
// Out code

data class DatabaseData(val position: Int, val amount: Int)

class DatabaseDataGenerator {
    fun generateData(): List<DatabaseData> {
        val list = arrayListOf<DatabaseData>()
        list.add(DatabaseData(2, 2))
        list.add(DatabaseData(3, 7))
        list.add(DatabaseData(4, 23))
        return list
    }
}

interface DatabaseDataConverter {
    fun convertData(data: List<DatabaseData>): List<DisplayDataType>
}

class DataDisplayAdapter(val display: DataDisplay): DatabaseDataConverter {
    override fun convertData(data: List<DatabaseData>): List<DisplayDataType> {
        val returnList = arrayListOf<DisplayDataType>()
        
        for (datum in data) {
            val ddt = DisplayDataType(datum.position.toFloat(), datum.amount.toString())
            display.displayData(ddt)
            returnList.add(ddt)
        }
        
        return returnList
    }
}

class AdapterTest {
    @Test
    fun adapterTest() {
        val generator = DatabaseDataGenerator()
        val generatedData = generator.generateData()
        val adapter = DataDisplayAdapter(DataDisplay())
        val convertData = adapter.convertData(generatedData)
        
        Assertions.assertThat(convertData.size).isEqualTo(3)
        Assertions.assertThat(convertData[1].index).isEqualTo(3f)
        Assertions.assertThat(convertData[1].data).isEqualTo("7")
    }
}
