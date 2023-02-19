package com.example.urlshortener.controller

import io.swagger.v3.oas.annotations.media.Schema
import java.net.URL

data class CreateShortUrlRequest(
    @field:Schema(description = "Long Url")
    val url: URL,
)
