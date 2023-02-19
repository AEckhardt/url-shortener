package com.example.urlshortener.service

import com.example.urlshortener.controller.CreateShortUrlRequest
import com.example.urlshortener.repository.ShortUrl

interface UrlShortener {
    fun createShortUrl(createShortUrlRequest: CreateShortUrlRequest): ShortUrl
}
