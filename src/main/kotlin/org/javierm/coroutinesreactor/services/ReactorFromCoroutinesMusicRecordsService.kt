package org.javierm.coroutinesreactor.services

import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.reactor.mono
import org.javierm.coroutinesreactor.MusicRecord
import org.javierm.coroutinesreactor.repos.CoroutinesMusicRecordRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ReactorFromCoroutinesMusicRecordsService(
    val coroutinesMusicRecordRepository: CoroutinesMusicRecordRepository
) {

    fun getAllRecords(): Flux<MusicRecord> =
       flow { emitAll(coroutinesMusicRecordRepository.findAll()) }.asFlux()

    fun getOldestFromYear(year: Int): Mono<MusicRecord> =
       mono {
           return@mono coroutinesMusicRecordRepository.getRecordsFromYear(year)
               .reduce { accumulator, value -> if (value.releaseYear < accumulator.releaseYear) value else accumulator }
       }

}
