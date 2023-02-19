# Simple Url Shortener

Kotlin SpringBoot Application with MongoDB that provides an API to create a short url and redirects to long urls given the short url.

### Run Locally
Start MongoDB
```
    docker-compose up
```
Run Application with local profile
```
 ./gradlew bootRun --args='--spring.profiles.active=local'
```
(OpenApi Docs available under /api-docs/swagger-ui.html)

### Improvement ideas
+ Determine the best way to generate unique short IDs
+ Reduce id collision with retries
+ Use Micrometer to measure collisions
+ Cache most requested urls
+ Add Test for id collision
