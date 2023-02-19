package com.example.urlshortener.repository

import org.springframework.data.annotation.Id

class ShortUrl(
    @Id
    val id: String,
    val url: String,
)
