package org.javierm.coroutinesreactor.controllers

import org.javierm.coroutinesreactor.services.ReactorMusicRecordsService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reactive")
class ReactorMusicRecordController(
    private val reactorMusicRecordsService: ReactorMusicRecordsService
) {

   @GetMapping("records")
   fun getAllRecords() = reactorMusicRecordsService.getAllRecords()

   @GetMapping("records/oldestFromYear/{year}")
   fun getOldestFromYear(@PathVariable year: Int) = reactorMusicRecordsService.getOldestFromYear(year)

}
