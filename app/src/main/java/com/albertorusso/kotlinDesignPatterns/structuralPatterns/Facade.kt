package com.albertorusso.kotlinDesignPatterns.structuralPatterns

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * In software design, the "Facade" is a structural design pattern that provides a simplified interface to a set of interfaces in a subsystem.
 * It defines a higher-level interface that makes the subsystem easier to use, hiding the complexities of the individual components within
 * the subsystem.
 *
 * The main purpose of the Facade pattern is to provide a unified and simplified interface to a set of interfaces in a subsystem, making it easier
 * for clients to interact with the subsystem without needing to understand its internal details. This can improve the readability, usability, and
 * maintainability of a system by encapsulating the complexities and intricacies of the subsystem behind a single, well-defined interface.
 *
 * Key components of the Facade pattern include:
 *
 * Facade: This is the class or interface that provides a simplified, high-level interface to the client. It delegates client requests to the
 * appropriate objects within the subsystem.
 *
 * Subsystems: These are the individual components or classes that make up the subsystem. The Facade does not implement the functionality itself but
 * delegates calls to the appropriate subsystem components.
 */


class ComplexSystemStore(private val filePath: String) {
    private val cache: HashMap<String, String>
    
    init {
        println("Reading data from the file: $filePath")
        cache = HashMap()
        // Read properties from file and put to cache
    }
    
    fun store(key: String, value: String) {
        cache[key] = value
    }
    
    fun read(key: String) = cache[key] ?: ""
    
    fun commit() = println("Storing cached data to file $filePath")
}

data class User(val login: String)

//Facade
class UserRepository {
    private val systemPreferences = ComplexSystemStore("/data/default.prefs")
    
    fun save(user: User) {
        systemPreferences.store("USER_KEY", user.login)
        systemPreferences.commit()
    }
    
    fun findFirst(): User = User(systemPreferences.read("USER_KEY"))
}

class FacadeTest {
    @Test
    fun testFacade() {
        val userRepo = UserRepository()
        val user = User("john")
        userRepo.save(user)
        
        val retrievedUser = userRepo.findFirst()
        
        Assertions.assertThat(retrievedUser.login).isEqualTo("john")
    }
}
