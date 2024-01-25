package com.albertorusso.kotlinDesignPatterns.behaviouralPatterns

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * The Chain of Responsibility pattern is a behavioral design pattern that allows an object to pass a request along a chain of handlers. Each handler
 * in the chain decides either to process the request or to pass it along to the next handler in the chain. This pattern decouples the sender and
 * receiver of a request and allows multiple objects to handle the request without the sender needing to know which object will ultimately process it.
 *
 * Here are the key components of the Chain of Responsibility pattern:
 *
 * #1 Handler: This is an interface or abstract class that declares a method for handling requests. It may also define a reference to the next
 * handler in the chain.
 *
 * #2 ConcreteHandler: This is the concrete implementation of the handler interface. It processes the request and may pass it along to the next
 * handler in the chain.
 *
 * #3 Client: This is the object that initiates the request and starts it along the chain.
 */

interface HandlerChain {
    fun addHeader(inputHeader: String): String
}

class AuthenticationHeader(private val token: String?, var next: HandlerChain? = null): HandlerChain {
    override fun addHeader(inputHeader: String) = "$inputHeader\nAuthorization: $token"
        .let { next?.addHeader(it) ?: it }
}

class ContentTypeHeader(private val contentType: String, var next: HandlerChain? = null): HandlerChain {
    override fun addHeader(inputHeader: String) = "$inputHeader\nContentType: $contentType"
        .let { next?.addHeader(it) ?: it }
}

class BodyPayloadHeader(private val body: String, var next: HandlerChain? = null): HandlerChain {
    override fun addHeader(inputHeader: String) = "$inputHeader\n$body"
        .let { next?.addHeader(it) ?: it}
}


class ChainOfResponsibilityTest {
    @Test
    fun testChainOfResponsibility() {
        
        val authenticationHeader = AuthenticationHeader("123456")
        val contentTypeHeader = ContentTypeHeader("json")
        val bodyPayloadHeader = BodyPayloadHeader("Body:{\"username\" = \"john\"}")
        
        authenticationHeader.next = contentTypeHeader
        contentTypeHeader.next = bodyPayloadHeader
        
        val messageWithAuthentication = authenticationHeader.addHeader("Headers with authentication")
        println(messageWithAuthentication)
        
        println("-------------------")
        
        val messageWithoutAuthentication = contentTypeHeader.addHeader("Headers without authentication")
        println(messageWithoutAuthentication)
        
        Assertions.assertThat(messageWithAuthentication).isEqualTo(
            """
                Headers with authentication
                Authorization: 123456
                ContentType: json
                Body:{"username" = "john"}
            """.trimIndent()
        )
    
        Assertions.assertThat(messageWithoutAuthentication).isEqualTo(
            """
                Headers without authentication
                ContentType: json
                Body:{"username" = "john"}
            """.trimIndent()
        )
    }
}
