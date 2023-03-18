package com.example.searchservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
class SearchServiceApplication

fun main(args: Array<String>) {
    runApplication<SearchServiceApplication>(*args)
}
