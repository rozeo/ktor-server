package net.roz.http.Handlers

import net.roz.core.http.RequestHandlerInterface

class SecondHandler: RequestHandlerInterface {
    override fun handle(): String {
        return "second!"
    }
}