package com.example.urlshortener.service

import com.example.urlshortener.controller.CreateShortUrlRequest
import com.example.urlshortener.repository.ShortUrl
import com.example.urlshortener.repository.ShortUrlRepository
import io.klogging.NoCoLogging
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SimpleUrlShortener(
    private val shortUrlRepository: ShortUrlRepository,
) : UrlShortener, NoCoLogging {

    override fun createShortUrl(createShortUrlRequest: CreateShortUrlRequest): ShortUrl {
        val id = generateId()
        val shortUrl = ShortUrl(
            id = id,
            url = createShortUrlRequest.url.toString(),
        )
        if (shortUrlRepository.existsById(id)) {
            logger.warn("Id collision occurred")
            throw IdCollisionException()
        }
        return shortUrlRepository.save(shortUrl)
    }

    private fun generateId() =
        UUID.randomUUID().toString().substring(startIndex = 0, endIndex = 8)
}
