package com.example.urlshortener

import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

fun createMongoDBTestContainer() = MongoDBContainer(DockerImageName.parse("mongo:4.4.18"))
