package com.example.searchservice.service

import com.example.searchservice.common.RestTemplateConfig
import com.example.searchservice.common.SourceInfo
import com.example.searchservice.common.enum.SearchSourceType
import com.example.searchservice.common.enum.SortType
import com.example.searchservice.entity.SearchEntity
import com.example.searchservice.repository.ServiceRepo
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.util.UriComponentsBuilder
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

@Service
@Transactional(readOnly = true)
class SearchService(
    private val restTemplateConfig: RestTemplateConfig,
    private val sourceInfo: SourceInfo,
    private val searchRepo: ServiceRepo
) {

    fun blogList(
        searchSource: SearchSourceType,
        query: String,
        sort: SortType,
        page: Int,
        size: Int
    ): String {

        val (url, requestHeader) = when (searchSource) {
            SearchSourceType.kakao -> searchKakao(
                query,
                sort,
                page,
                size
            )
            SearchSourceType.naver -> searchNaver(
                query,
                when (sort) {
                    SortType.accuracy -> "sim"
                    SortType.recency -> "date"
                },
                start = page,
                display = size
            )
        }

        return connect(url, HttpEntity<JSONObject>(requestHeader), HttpMethod.GET)
    }

    private fun searchKakao(
        query: String,
        sort: SortType,
        page: Int,
        size: Int
    ): Pair<String, HttpHeaders> {

        val url = getUrl(
            sourceInfo.kakao.host,
            "/v2/search/blog",
            "query=$query&sort=$sort&page=$page&size=$size"
        )

        val requestHeader = HttpHeaders().apply {
            this.contentType = MediaType.APPLICATION_JSON
            this[sourceInfo.kakao.restApi.key] = sourceInfo.kakao.restApi.value
        }

        return url to requestHeader
    }

    private fun searchNaver(
        query: String,
        sort: String,
        start: Int,
        display: Int
    ): Pair<String, HttpHeaders> {

        val encodingQuery = getEncodingQuery(query)

        val url = getUrl(
            sourceInfo.naver.host,
            "/v1/search/blog.json",
            "query=$encodingQuery&sort=$sort&start=$start&display=$display"
        )

        val requestHeader = HttpHeaders().apply {
            this.contentType = MediaType.APPLICATION_JSON
            this[sourceInfo.naver.clientId.key] = sourceInfo.naver.clientId.value
            this[sourceInfo.naver.clientSecret.key] = sourceInfo.naver.clientSecret.value
        }

        return url to requestHeader
    }

    private fun getEncodingQuery(query: String) = try {
        URLEncoder.encode(query, "UTF-8")
    } catch (e: UnsupportedEncodingException) {
        throw RuntimeException("검색어 인코딩 실패", e)
    }

    private fun getUrl(
        uri: String,
        path: String,
        params: String
    ) = UriComponentsBuilder.fromUriString(uri)
        .path("/")
        .path(path)
        .path("?")
        .path(params)
        .build()
        .toUriString()

    private fun connect(url: String, requestHttp: HttpEntity<JSONObject>, method: HttpMethod): String =
        restTemplateConfig.restTemplate().exchange(url, method, requestHttp, String::class.java).toString()

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