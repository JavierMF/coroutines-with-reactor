package org.javierm.coroutinesreactor.controllers

import org.javierm.coroutinesreactor.services.CoroutinesMusicRecordsService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/coroutines")
class CoroutinesMusicRecordController(
    private val coroutinesMusicRecordsService: CoroutinesMusicRecordsService
) {

   @GetMapping("records")
   suspend fun getAllRecords() = coroutinesMusicRecordsService.getAllRecords()

   @GetMapping("records/oldestFromYear/{year}")
   suspend fun getOldestFromYear(@PathVariable year: Int) = coroutinesMusicRecordsService.getOldestFromYear(year)
}
