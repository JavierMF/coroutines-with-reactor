package org.javierm.coroutinesreactor

import io.kotest.matchers.shouldBe
import org.javierm.coroutinesreactor.repos.ReactorMusicRecordRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AllControllersE2EMusicRecordTest(
    @Autowired
    private val webClient: WebTestClient,
    @Autowired
    private val reactorMusicRecordRepository: ReactorMusicRecordRepository
) {

    @BeforeEach
    fun setup() {
        reactorMusicRecordRepository.deleteAll().block()
        reactorMusicRecordRepository.saveAll(RECORDS).blockLast()
    }

    @ParameterizedTest
    @MethodSource("controllerPrefixes")
    fun testGetAllRecords(basePath: String) {
        val result = webClient.get().uri("/$basePath/records")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(MusicRecord::class.java)
            .hasSize(4)
            .returnResult()

        result.responseBody?.let {
            it.first().artist shouldBe "Rosalia"
            it.first().releaseYear shouldBe 2018
        } ?: AssertionError("No response body")
    }

    @ParameterizedTest
    @MethodSource("controllerPrefixes")
    fun testOldestRecord(basePath: String) {
        val result = webClient.get().uri("/$basePath/records/oldestFromYear/2020")
            .exchange()
            .expectStatus().isOk()
            .expectBody(MusicRecord::class.java)
            .returnResult()

        result.responseBody?.let {
            it.artist shouldBe "Idles"
            it.releaseYear shouldBe 2020
        } ?: AssertionError("No response body")
    }

    companion object {
        @JvmStatic
        fun controllerPrefixes()= listOf(
            Arguments.of("coroutines"),
            Arguments.of("reactive"),
            Arguments.of("coFromRe"),
            Arguments.of("reFromCo"),
          )


        val RECORDS = listOf(
            MusicRecord(
                null,
                "Rosalia",
                "El Mal Querer",
                2018
            ),
            MusicRecord(
                null,
                "Alcalá Norte",
                "Alcalá Norte",
                2024
            ),
            MusicRecord(
                null,
                "Vetusta Morla",
                "Un Día en el Mundo",
                2008
            ),
            MusicRecord(
                null,
                "Idles",
                "Ultra Mono",
                2020
            ),
        )
    }
}
