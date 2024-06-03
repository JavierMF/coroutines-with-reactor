package org.javierm.coroutinesreactor

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import reactor.kotlin.test.expectError
import reactor.kotlin.test.test

class ReactorFromCoroutineTest {

    @Test
    fun shouldConvertFlowNumbers() {
        val fluxNumbers = flow { emitAll(coroutineApi.flowNumbers()) }.asFlux()

        fluxNumbers.test()
            .expectNextCount(10)
            .verifyComplete()
    }

    @Test
    fun shouldConvertFlowEmpty() {
        val fluxNumbers = flow { emitAll(coroutineApi.flowEmpty()) }.asFlux()

        fluxNumbers.test()
            .verifyComplete()
    }

    @Test
    fun shouldManageFlowEmpty() {
        val fluxNumbers = flow { emitAll(coroutineApi.flowEmpty()) }.asFlux()
            .defaultIfEmpty(100)

        fluxNumbers.test()
            .assertNext { it shouldBe 100 }
            .verifyComplete()
    }

    @Test
    fun shouldConvertFlowError() {
        val fluxNumbers = flow { emitAll(coroutineApi.flowError()) }.asFlux()

        fluxNumbers.test()
            .expectError(RuntimeException::class)
    }

    @Test
    fun shouldManageFlowError() {
        val fluxNumbers = flow { emitAll(coroutineApi.flowError()) }.asFlux()
            .onErrorReturn(99)

        fluxNumbers.test()
            .assertNext { it shouldBe 99 }
            .verifyComplete()
    }

    @Test
    fun shouldConvertMonoNumber() {
        val monoNumber = mono { coroutineApi.number() }

        monoNumber.test()
            .assertNext { it shouldBe 42 }
            .verifyComplete()
    }

    @Test
    fun shouldConvertMonoEmpty() {
        val monoNumber = mono { coroutineApi.numberEmpty() }

        monoNumber.test()
            .verifyComplete()
    }

    @Test
    fun shouldManageMonoError() {
        val monoNumber = mono { coroutineApi.numberError() }

        monoNumber.test()
            .expectError(RuntimeException::class)
    }

    object coroutineApi {
        suspend fun flowNumbers(): Flow<Int> = runBlocking {
            (1..10).map { delay(10); it }
        }.asFlow()
        suspend fun flowEmpty(): Flow<Int> = runBlocking { delay(10); emptyFlow() }
        suspend fun flowError(): Flow<Int>  = runBlocking { delay(10); throw RuntimeException("Some error") }

        suspend fun number(): Int? = runBlocking { delay(10); 42 }
        suspend fun numberEmpty(): Int? = runBlocking { delay(10); null }
        suspend fun numberError(): Int? = runBlocking { delay(10); throw RuntimeException("Some error") }
    }
}
