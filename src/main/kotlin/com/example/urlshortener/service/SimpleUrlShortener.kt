package com.example.urlshortener.service

import com.example.urlshortener.controller.CreateShortUrlRequest
import com.example.urlshortener.repository.ShortUrl
import com.example.urlshortener.repository.ShortUrlRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SimpleUrlShortener(
    private val shortUrlRepository: ShortUrlRepository,
) : UrlShortener {

    override fun createShortUrl(createShortUrlRequest: CreateShortUrlRequest): ShortUrl {
        val id = generateId()
        val shortUrl = ShortUrl(
            id = id,
            url = createShortUrlRequest.url.toString(),
        )
        return shortUrlRepository.save(shortUrl)
    }

    private fun generateId() =
        UUID.randomUUID().toString().substring(startIndex = 0, endIndex = 8)
}
