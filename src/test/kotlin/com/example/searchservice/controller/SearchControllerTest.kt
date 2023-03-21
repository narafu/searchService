package com.example.searchservice.controller

import com.example.searchservice.common.enum.SearchSourceType
import com.example.searchservice.common.enum.SortType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
internal class SearchControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @Test
    fun blogList() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/search/blog")
                .param("searchSource", SearchSourceType.kakao.name)
                .param("query", "개발자")
                .param("sort", SortType.accuracy.name)
                .param("page", "1")
                .param("size", "10")
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun searchKakao() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/search/popular")
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}
