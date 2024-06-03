package org.javierm.coroutinesreactor.services

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.javierm.coroutinesreactor.MusicRecord
import org.springframework.stereotype.Service

@Service
class CoroutinesFromReactorMusicRecordsService(
    val reactorMusicRecordsService: ReactorMusicRecordsService
) {

    suspend fun getAllRecords(): Flow<MusicRecord> = reactorMusicRecordsService.getAllRecords().asFlow()

    suspend fun getOldestFromYear(year: Int): MusicRecord? =
        reactorMusicRecordsService.getOldestFromYear(year).awaitFirstOrNull()

}
