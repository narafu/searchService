package com.example.searchservice.service

import com.example.searchservice.common.enum.SearchSourceType
import com.example.searchservice.common.enum.SortType
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class SearchServiceTest(
    private val searchService: SearchService
) {

    @Test
    fun blogList() {

        val searchSource = SearchSourceType.kakao
        val query = "카카오"
        val sort = SortType.accuracy
        val page = 1
        val size = 10

        searchService.blogList(searchSource, query, sort, page, size)
    }

    @Test
    fun upsertKeywordData() {

        val query = "카카오"
        searchService.upsertKeywordData(query)
    }

    @Test
    fun popularList() {

        searchService.popularList()
    }
}