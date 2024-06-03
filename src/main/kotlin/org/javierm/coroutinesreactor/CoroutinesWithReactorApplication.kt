package org.javierm.coroutinesreactor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoroutinesWithReactorApplication

fun main(args: Array<String>) {
	runApplication<CoroutinesWithReactorApplication>(*args)
}
