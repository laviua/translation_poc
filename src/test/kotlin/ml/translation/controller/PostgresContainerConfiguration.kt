package ml.translation.controller

import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import javax.sql.DataSource

@TestConfiguration
@Testcontainers
open class PostgresContainerConfiguration {

    init {
        postgreSQLContainer.start()
    }

    @LiquibaseDataSource
    @Bean
    open fun datasource(): DataSource {
        return DataSourceBuilder
            .create()
            .username(postgreSQLContainer.username)
            .password(postgreSQLContainer.password)
            .url(postgreSQLContainer.jdbcUrl)
            .build()
    }

    companion object {
        @Container
        private val postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:10.6")

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)
        }
    }

}
