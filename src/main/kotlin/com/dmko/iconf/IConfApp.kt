package com.dmko.iconf

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IConfApp

fun main(args: Array<String>) {
    runApplication<IConfApp>(*args)
}
