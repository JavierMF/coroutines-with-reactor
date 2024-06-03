package org.javierm.coroutinesreactor

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.ReactorContext
import kotlinx.coroutines.reactor.asCoroutineContext
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import reactor.util.context.Context

class CoroutineFromReactorTest {

    @Test
    fun shouldConvertFluxNumbers() = runTest {
        val numbersEmited = reactorApi.fluxNumbers().asFlow().count()
        numbersEmited shouldBe 10
    }

    @Test
    fun shouldConvertFluxEmpty() = runTest {
        val numbersEmited = reactorApi.fluxEmpty().asFlow().count()
        numbersEmited shouldBe 0
    }

    @Test
    fun shouldManageFluxEmpty() = runTest {
        val numbersEmited = mutableListOf<Int>()
        reactorApi.fluxEmpty().asFlow()
            .onEmpty { emit(100) }
            .toList(numbersEmited)

        numbersEmited shouldBe listOf(100)
    }

    @Test
    fun shouldConvertFluxError() = runTest {
        shouldThrow<RuntimeException> {
            reactorApi.fluxError().asFlow().count()
        }
    }

    @Test
    fun shouldManageFluxError() = runTest {
        val numbersEmited = mutableListOf<Int>()
        reactorApi.fluxError().asFlow()
            .catch { emit(99) }
            .toList(numbersEmited)

        numbersEmited shouldBe listOf(99)
    }

    @Test
    fun shouldConvertFluxContext() = runTest {
        reactorApi.fluxNumbers().asFlow()
            .onEach { _ ->
                currentCoroutineContext()[ReactorContext]?.context?.get("transaction") as String shouldBe "999"
            }.asFlux().contextWrite { ctx -> ctx.put("transaction", "999") }
    }

    @Test
    fun shouldConvertMonoNumber() = runTest {
        val numberEmited = reactorApi.monoNumber().awaitFirstOrNull()
        numberEmited shouldBe 42
    }

    @Test
    fun shouldConvertMonoEmpty() = runTest {
        val numberEmited = reactorApi.monoEmpty().awaitFirstOrNull()
        numberEmited shouldBe null
    }

    @Test
    fun shouldManageMonoError() = runTest {
        shouldThrow<RuntimeException> {
            reactorApi.monoError().awaitFirstOrNull()
        }
    }

    @Test
    fun shouldConvertMonoContext() = runTest {
        val monoDoWithContext = reactorApi.monoNumber()
            .transformDeferredContextual { mono, context ->
                context.get<String>("transaction") shouldBe "999"
                mono
            }

        withContext(Context.of("transaction", "999").asCoroutineContext()) {
            val numberEmited = monoDoWithContext.awaitFirstOrNull()
            numberEmited shouldBe 42
        }
    }

    object reactorApi {
        fun fluxNumbers(): Flux<Int> = (1..10).toFlux()
        fun fluxEmpty(): Flux<Int> = Flux.empty()
        fun fluxError(): Flux<Int> = Flux.error { RuntimeException("Some error") }

        fun monoNumber(): Mono<Int> = 42.toMono()
        fun monoEmpty(): Mono<Int> = Mono.empty()
        fun monoError(): Mono<Int> = Mono.error { RuntimeException("Some error") }
    }
}
