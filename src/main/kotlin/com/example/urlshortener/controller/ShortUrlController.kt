package com.example.urlshortener.controller

import com.example.urlshortener.repository.ShortUrl
import com.example.urlshortener.service.IdCollisionException
import com.example.urlshortener.service.UrlShortener
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.net.URL
import io.swagger.v3.oas.annotations.parameters.RequestBody as ApiRequestBody

@RestController
class ShortUrlController(
    private val urlShortener: UrlShortener,
    @Value("\${domain}") private val domain: String,
) {

    @Operation(summary = "Create a short url for a long url")
    @ApiRequestBody(
        content = [Content(schema = Schema(implementation = CreateShortUrlRequest::class))],
    )
    @ApiResponse(
        responseCode = "201",
        description = "Short url created",
        content = [Content(schema = Schema(implementation = ShortUrlCreatedResponse::class))],
    )
    @PostMapping(
        path = ["create"],
        consumes = ["application/json"],
        produces = ["application/json"],
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun createShortUrl(@RequestBody createShortUrlRequest: CreateShortUrlRequest): ShortUrlCreatedResponse {
        return try {
            urlShortener.createShortUrl(createShortUrlRequest).toCreatedResponse()
        } catch (ex: IdCollisionException) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred, try again")
        }
    }

    private fun ShortUrl.toCreatedResponse() =
        ShortUrlCreatedResponse(url = URL(this.url), shortUrl = URL("$domain${this.id}"))
}
