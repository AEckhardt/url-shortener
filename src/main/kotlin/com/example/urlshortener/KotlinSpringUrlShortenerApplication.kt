package com.example.urlshortener

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(
    info = Info(title = "Url Shortener"),
)
class KotlinSpringUrlShortenerApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringUrlShortenerApplication>(*args)
}
