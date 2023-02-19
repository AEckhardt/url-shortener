package com.example.urlshortener.repository

import org.springframework.data.repository.Repository

interface ShortUrlRepository : Repository<ShortUrl?, String> {
    fun findById(id: String): ShortUrl?
    fun save(shortUrl: ShortUrl): ShortUrl

    fun existsById(id: String): Boolean

    fun deleteAll()
}
