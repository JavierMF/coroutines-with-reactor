# Reactor + Kotlin Coroutines integration

Just some samples about **integrating Reactor APIs and
Kotlin Coroutines APIs**. I found that how to mix these two APIs in
a project was non-trivial. Although it is something not desired/recommended, sometimes it
is necessary when migrating from one API to the other one 
or when APIs for one of them are not available for a specific library.

Check the tests about:
* [Converting to Coroutines API from Reactor API](./src/test/kotlin/org/javierm/coroutinesreactor/CoroutineFromReactorTest.kt)
* [Converting to Reactor API from Coroutines API](./src/test/kotlin/org/javierm/coroutinesreactor/ReactorFromCoroutineTest.kt)

and the services in a trivial reactive webapp which:
* [Convert to Coroutines API from Reactor API](./src/main/kotlin/org/javierm/coroutinesreactor/services/CoroutinesFromReactorMusicRecordsService.kt)
* [Convert to Reactor API from Coroutines API](./src/main/kotlin/org/javierm/coroutinesreactor/services/ReactorFromCoroutinesMusicRecordsService.kt)

