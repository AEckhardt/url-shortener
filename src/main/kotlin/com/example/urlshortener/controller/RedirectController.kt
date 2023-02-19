package com.example.urlshortener.controller

import com.example.urlshortener.repository.ShortUrl
import com.example.urlshortener.repository.ShortUrlRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.view.RedirectView

@RestController
class RedirectController(
    private val shortUrlRepository: ShortUrlRepository,
) {
    @Operation(summary = "Redirects to url linked to short url")
    @ApiResponse(responseCode = "302", description = "Redirect to linked url")
    @ApiResponse(responseCode = "404", description = "Short url not found")
    @GetMapping("/{id}")
    fun redirectToLongUrl(@PathVariable id: String): RedirectView {
        return shortUrlRepository.findById(id)?.toRedirectView() ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Unknown short url",
        )
    }

    private fun ShortUrl.toRedirectView() = RedirectView().apply {
        url = this@toRedirectView.url
    }
}
