package org.javierm.coroutinesreactor.services

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.reduce
import org.javierm.coroutinesreactor.MusicRecord
import org.javierm.coroutinesreactor.repos.CoroutinesMusicRecordRepository
import org.springframework.stereotype.Service

@Service
class CoroutinesMusicRecordsService(
    val coroutinesMusicRecordRepository: CoroutinesMusicRecordRepository
) {

    suspend fun getAllRecords(): Flow<MusicRecord> = coroutinesMusicRecordRepository.findAll()

    suspend fun getOldestFromYear(year: Int): MusicRecord =
        coroutinesMusicRecordRepository.getRecordsFromYear(year)
            .reduce { accumulator, value -> if (value.releaseYear < accumulator.releaseYear) value else accumulator }

}
