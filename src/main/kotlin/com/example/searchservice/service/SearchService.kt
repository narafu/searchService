package com.example.searchservice.service

import com.example.searchservice.common.enum.SearchSourceType
import com.example.searchservice.common.enum.SortType
import com.example.searchservice.entity.SearchEntity
import com.example.searchservice.repository.ServiceRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SearchService(
    private val searchRepo: ServiceRepo
) {

    fun blogList(
        searchSource: SearchSourceType,
        query: String,
        sort: SortType,
        page: Int,
        size: Int
    ) {

        val result = when (searchSource) {
            SearchSourceType.kakao -> searchKakao(query, sort, page, size)
            SearchSourceType.naver -> TODO()
            else -> TODO()
        }

        TODO("Not yet implemented")
    }

    private fun searchKakao(
        query: String,
        sort: SortType,
        page: Int,
        size: Int
    ): String {
        TODO()
    }

    @Transactional
    fun upsertKeywordData(query: String) {

        val keyword = query.trim()

        searchRepo.findByKeyword(keyword)
            ?.let { it.searchCount++; }
            ?: searchRepo.save(SearchEntity(keyword))
    }

    fun popularList(): List<SearchEntity> {
        return searchRepo.findTop10ByOrderBySearchCountDesc()
    }
}