package org.javierm.coroutinesreactor.services

import org.javierm.coroutinesreactor.MusicRecord
import org.javierm.coroutinesreactor.repos.ReactorMusicRecordRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.extra.math.min

@Service
class ReactorMusicRecordsService(
    val reactorMusicRecordRepository: ReactorMusicRecordRepository
) {

    fun getAllRecords(): Flux<MusicRecord> = reactorMusicRecordRepository.findAll()

    fun getOldestFromYear(year: Int): Mono<MusicRecord> =
        reactorMusicRecordRepository.getRecordsFromYear(year)
            .min { record1, record2 -> record1.releaseYear.compareTo(record2.releaseYear)  }

}
