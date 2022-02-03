package net.roz.http.Handlers

import net.roz.http.RequestHandlerInterface

class HelloWorldHandler: RequestHandlerInterface {
    override fun handle(): String {
        return "Hello World!"
    }
}