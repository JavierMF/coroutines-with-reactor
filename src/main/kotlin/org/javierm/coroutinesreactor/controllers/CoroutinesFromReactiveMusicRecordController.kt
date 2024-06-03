package org.javierm.coroutinesreactor.controllers

import org.javierm.coroutinesreactor.services.CoroutinesFromReactorMusicRecordsService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/coFromRe")
class CoroutinesFromReactiveMusicRecordController(
    private val coroutinesFromReactorMusicRecordsService: CoroutinesFromReactorMusicRecordsService
) {

   @GetMapping("records")
   suspend fun getAllRecords() = coroutinesFromReactorMusicRecordsService.getAllRecords()

   @GetMapping("records/oldestFromYear/{year}")
   suspend fun getOldestFromYear(@PathVariable year: Int) = coroutinesFromReactorMusicRecordsService.getOldestFromYear(year)
}
