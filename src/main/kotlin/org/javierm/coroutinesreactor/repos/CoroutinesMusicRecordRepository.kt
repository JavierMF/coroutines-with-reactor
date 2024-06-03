package org.javierm.coroutinesreactor.repos

import kotlinx.coroutines.flow.Flow
import org.javierm.coroutinesreactor.MusicRecord
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CoroutinesMusicRecordRepository: CoroutineCrudRepository<MusicRecord, Long> {

    @Query("SELECT * FROM music_record WHERE release_year >= :year")
    suspend fun getRecordsFromYear(year: Int): Flow<MusicRecord>

}

