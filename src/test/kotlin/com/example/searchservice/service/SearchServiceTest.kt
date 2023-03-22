package com.example.searchservice.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class SearchServiceTest(
    @Autowired private val searchService: SearchService
) {

    @Test
    fun popularList() {

        for (i in 1..10) {
            for (j in 1..i) {
                searchService.upsertKeywordData(query = i.toString())
            }
        }

        val popularList = searchService.popularList()

        for (i in 0..9) {
            Assertions.assertTrue(popularList[i].keyword == (10 - i).toString())
            Assertions.assertTrue(popularList[i].searchCount == (10 - i).toLong())
        }
    }
}