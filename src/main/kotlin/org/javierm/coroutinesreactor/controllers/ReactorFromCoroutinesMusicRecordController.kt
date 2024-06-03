package org.javierm.coroutinesreactor.controllers

import org.javierm.coroutinesreactor.MusicRecord
import org.javierm.coroutinesreactor.services.ReactorFromCoroutinesMusicRecordsService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/reFromCo")
class ReactorFromCoroutinesMusicRecordController(
    private val reactorFromCoroutinesMusicRecordsService: ReactorFromCoroutinesMusicRecordsService
) {

   @GetMapping("records")
   fun getAllRecords(): Flux<MusicRecord> = reactorFromCoroutinesMusicRecordsService.getAllRecords()

   @GetMapping("records/oldestFromYear/{year}")
   fun getOldestFromYear(@PathVariable year: Int): Mono<MusicRecord> = reactorFromCoroutinesMusicRecordsService.getOldestFromYear(year)

}
