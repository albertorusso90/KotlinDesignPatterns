package com.albertorusso.kotlinDesignPatterns.creationalPatterns

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * The Abstract Factory pattern is a creational design pattern that provides an interface for creating families of related or dependent objects
 * without specifying their concrete classes. It allows a client to create objects without knowing their exact types, promoting flexibility and
 * avoiding the need to specify concrete classes in the client code.
 *
 * Here are the key components of the Abstract Factory pattern:
 *
 * Abstract Factory: This is an interface or an abstract class that declares the methods for creating abstract products. It defines a set of
 * related or dependent object creation methods, each returning an abstract product.
 *
 * Concrete Factory: These are the concrete implementations of the abstract factory interface. Each concrete factory is responsible for creating a
 * family of related products.
 *
 * Abstract Product: This is an interface or an abstract class that declares the common interface for a family of related products.
 *
 * Concrete Product: These are the concrete implementations of the abstract product interfaces. Each concrete product belongs to a specific family
 * and implements the common interface declared by the abstract product.
 *
 * Client: This is the class that interacts with the abstract factory and abstract products. The client code calls the abstract factory methods
 * to create products without specifying their concrete classes.
 */

interface DataSource

class DatabaseDataSource: DataSource

class NetworkDataSource: DataSource

abstract class DataSourceFactory {
    abstract fun makeDataSource(): DataSource
    
    companion object {
        inline fun <reified T: DataSource> createFactory(): DataSourceFactory =
            when(T::class) {
                DatabaseDataSource::class -> DatabaseFactory()
                NetworkDataSource::class -> NetworkFactory()
                else -> throw IllegalArgumentException()
            }
    }
}

class NetworkFactory: DataSourceFactory() {
    override fun makeDataSource(): DataSource = NetworkDataSource()
}

class DatabaseFactory: DataSourceFactory() {
    override fun makeDataSource(): DataSource = DatabaseDataSource()
}

class AbstractFactoryTest {
    @Test
    fun abstractFactoryTest() {
        val dataSourceFactory = DataSourceFactory.createFactory<DatabaseDataSource>()
        val dataSource = dataSourceFactory.makeDataSource()
        println("create data source $dataSource")
        
        Assertions.assertThat(dataSource).isInstanceOf(DatabaseDataSource::class.java)
    
        val networkDataSource = DataSourceFactory.createFactory<NetworkDataSource>()
        val networkSource = networkDataSource.makeDataSource()
        println("create network source $networkSource")
    
        Assertions.assertThat(networkSource).isInstanceOf(NetworkDataSource::class.java)
    }
}

