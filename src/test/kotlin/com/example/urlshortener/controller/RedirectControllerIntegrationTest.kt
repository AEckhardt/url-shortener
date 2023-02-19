package com.example.urlshortener.controller

import com.example.urlshortener.createMongoDBTestContainer
import com.example.urlshortener.repository.ShortUrl
import com.example.urlshortener.repository.ShortUrlRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class RedirectControllerIntegrationTest {

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
    lateinit var shortUrlRepository: ShortUrlRepository

    @AfterEach
    fun setUp() {
        shortUrlRepository.deleteAll()
    }

    @Test
    fun `should redirect to long url`() {
        val shortUrl = ShortUrl(
            id = "12345",
            url = "https://hsm.stackexchange.com/questions/15170/is-it-true-that-albert-einstein-was-kicked-out-of-high-school-due-to-his-peacef",
        )
        shortUrlRepository.save(shortUrl)

        mvc.perform(
            MockMvcRequestBuilders.get("/${shortUrl.id}"),
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.redirectedUrl(shortUrl.url))
    }

    @Test
    fun `should return not found if id is unknown`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/asf")
                .accept(MediaType.APPLICATION_JSON),
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}
