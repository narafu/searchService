package com.example.searchservice.controller

import com.example.searchservice.common.enum.SearchSourceType
import com.example.searchservice.common.enum.SortType
import com.example.searchservice.service.SearchService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Size

@Tag(name = "검색")
@Validated
@RestController
@RequestMapping("/v1/search", produces = [MediaType.APPLICATION_JSON_VALUE])
class SearchController(
    private val searchService: SearchService
) {

    @Operation(
        summary = " 블로그 검색",
        description = "키워드를 통해 블로그를 검색할 수 있습니다."
    )
    @GetMapping("/blog")
    fun blogList(
        @Parameter(
            description = "검색 소스, kakao(카카오 API의 키워드로 블로그 검색), 기본 값 kakao"
        )
        @RequestParam(defaultValue = "kakao") searchSource: SearchSourceType,
        @Parameter(
            description = "검색을 원하는 질의어, 특정 블로그 글만 검색하고 싶은 경우, 블로그 url과 검색어를 공백(' ') 구분자로 넣을 수 있음"
        )
        @RequestParam query: String,
        @Parameter(description = "결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy")
        @RequestParam(defaultValue = "accuracy") sort: SortType,
        @Parameter(description = "결과 페이지 번호, 1~50 사이의 값, 기본 값 1")
        @RequestParam(defaultValue = "1") @Min(value = 1) @Max(value = 50) page: Int,
        @Parameter(description = "한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10")
        @RequestParam(defaultValue = "10") @Min(value = 1) @Max(value = 50) size: Int,
    ) {

//        val response = searchService.blogList(searchSource, query, sort, page, size)

        searchService.upsertKeywordData(query)
    }

    @GetMapping("/popular")
    fun popularList() {

    }

}
