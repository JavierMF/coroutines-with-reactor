package org.javierm.coroutinesreactor.repos

import org.javierm.coroutinesreactor.MusicRecord
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface ReactorMusicRecordRepository: ReactiveCrudRepository<MusicRecord, Long> {

    @Query("SELECT * FROM music_record WHERE release_year >= :year")
    fun getRecordsFromYear(year: Int): Flux<MusicRecord>
}
