package com.example.urlshortener.controller

import io.swagger.v3.oas.annotations.media.Schema
import java.net.URL

data class ShortUrlCreatedResponse(
    @field:Schema(description = "Long Url")
    val url: URL,

    @field:Schema(description = "Created Short Url")
    val shortUrl: URL,
)
