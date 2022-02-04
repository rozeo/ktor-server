package net.roz.application.http.handlers

import net.roz.core.http.RequestHandlerInterface
import net.roz.core.http.RequestInterface

class SecondHandler: RequestHandlerInterface {
    override fun handle(request: RequestInterface): String {
        return "second!"
    }
}