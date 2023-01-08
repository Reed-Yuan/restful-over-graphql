# An Exercise of REST Over GraphQL

This is a simple exercise to demonstrate the ability to design and implement a REST API over a GraphQL API.

### Backing GraphQL API
    https://countries.trevorblades.com/graphql

### Resulting REST API
- `GET /api/v1/continent`, list **all** continents
- `GET /api/v1/continent/{continentCode}`, list information of a **single** continent specified by continentCode
- `GET /api/v1/continent?country-code={countryCode1, countryCode2, ...}`, list information of **continents** containing the country specified by countryCode

### Run the app

#### Prerequisites:
- JDK 17
- Docker (If you want to run the app in a container)

Pull or download source code from the repository and run the app either with Gradle or Docker.

Gradle:
```sh
    ./gradlew bootRun
```
Docker:
```sh
    ./gradlew bootBuildImage --imageName=sample/restful-over-graphql
    docker run -p 8080:8080 sample/restful-over-graphql
```

### Swagger
    http://localhost:8080/api/swagger-ui.html

### Tool stack

- JDK 17
- [Spring Boot](https://spring.io/projects/spring-boot) 3.0.1
- [Manifold GraphQL](http://manifold.systems/docs.html#graphql) 2022.1.32
- [Springdoc OpenAPI](https://springdoc.org/v2/) 2.0.2
- [Bucket4j](https://bucket4j.com/) 7.6.0

### Rate Limiting
For demonstration purpose, only endpoint `GET /api/v1/continent?country-code={countryCode1, countryCode2, ...}` is rate limited:

- 5 requests per minute for unauthenticated users
- 20 requests per minute for authenticated users

Authentication is simplified by passing a header `X-api-key` with a non-empty token. Sample curl command:
```sh
  curl -X 'GET' \
  'http://localhost:8080/api/v1/continent?country-code=cn%2Cca' \
  -H 'accept: application/json' \
  -H 'X-api-key: test'
```

### Logging
- Logging level is "`WARN`"
- When running in Docker, log file is located at "`/logs/restful-over-graphql.log`"

### Sample data
- **Continent code**: `EU`(Europe), `NA`(North America), `SA`(South America), `AF`(Africa), `AS`(Asia), `OC`(Oceania), `AN`(Antarctica)
- **Country code**: `CN`(China), `CA`(Canada), `US`(United States), `BR`(Brazil), `RU`(Russia), `AU`(Australia)
