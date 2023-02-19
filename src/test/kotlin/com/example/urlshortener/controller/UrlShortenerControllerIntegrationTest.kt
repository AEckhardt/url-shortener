package com.example.urlshortener.controller

import com.example.urlshortener.createMongoDBTestContainer
import com.example.urlshortener.repository.ShortUrlRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class UrlShortenerControllerIntegrationTest {
    companion object {
        @Container
        private val mongoDBContainer = createMongoDBTestContainer()

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl)
        }
    }

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var shortUrlRepository: ShortUrlRepository

    @AfterEach
    fun setUp() {
        shortUrlRepository.deleteAll()
    }

    @Test
    fun createShortUrl() {
        val result = mvc.perform(
            post("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"url": "https://hsm.stackexchange.com/questions/15170/is-it-true-that-albert-einstein-was-kicked-out-of-high-school-due-to-his-peacef"}
                    """,
                ),
        ).andExpect(status().isCreated).andReturn()

        val generatedShortUrlId =
            objectMapper.readTree(result.response.contentAsString).get("shortUrl").asText().split("/").last()

        val savedEntity = shortUrlRepository.findById(generatedShortUrlId)
        assertThat(savedEntity).isNotNull
        assertThat(savedEntity?.url).isEqualTo("https://hsm.stackexchange.com/questions/15170/is-it-true-that-albert-einstein-was-kicked-out-of-high-school-due-to-his-peacef")
    }
}
