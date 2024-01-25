package com.albertorusso.kotlinDesignPatterns.behaviouralPatterns

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * The State pattern is a behavioral design pattern that allows an object to alter its behavior when its internal state changes. The object appears
 * to change its class, as if it were an instance of a different class. This pattern is useful when the behavior of an object depends on its state,
 * and the object must be able to change its behavior dynamically as its state changes.
 *
 * Here are the key components of the State pattern:
 *
 * #1 Context: This is the class that has a state and whose behavior may change as the state changes. The context maintains a reference to the
 * current state object.
 *
 * #2 State: This is an interface or abstract class that defines a common interface for concrete state classes. Each concrete state class represents
 * a different state of the context.
 *
 * #3 ConcreteState: These are the concrete implementations of the state interface. Each concrete state class implements the behavior associated with
 * a specific state of the context.
 */

sealed class AuthorizationState

object Unauthorized: AuthorizationState()
class Authorized(val username: String): AuthorizationState()

class AuthorizationPresenter {
    private var state: AuthorizationState = Unauthorized
    
    val isAuthorized: Boolean
        get() = when (state) {
            is Authorized -> true
            is Unauthorized -> false
        }
    
    val username: String
        get() {
            return when (val state = this.state) {
                is Authorized -> state.username
                is Unauthorized -> "Unknown"
            }
        }
    
    fun loginUser(username: String) {
        state = Authorized(username)
    }
    
    fun logoutUser() {
        state = Unauthorized
    }
    
    override fun toString() = "User $username is logged in: $isAuthorized"
}

class StateTest {
    @Test
    fun testState() {
        val authorizationPresenter = AuthorizationPresenter()
        authorizationPresenter.loginUser("admin")
        println(authorizationPresenter)
        
        Assertions.assertThat(authorizationPresenter.isAuthorized).isEqualTo(true)
        Assertions.assertThat(authorizationPresenter.username).isEqualTo("admin")
        
        authorizationPresenter.logoutUser()
        println(authorizationPresenter)
        Assertions.assertThat(authorizationPresenter.isAuthorized).isEqualTo(false)
        Assertions.assertThat(authorizationPresenter.username).isEqualTo("Unknown")
    }
}
