package org.javierm.coroutinesreactor

import org.springframework.data.annotation.Id

data class MusicRecord(
    @Id
    val id: Long? = null,
    val artist: String,
    val recordName: String,
    val releaseYear: Int
)
