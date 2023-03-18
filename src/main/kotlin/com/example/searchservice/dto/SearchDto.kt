package com.example.searchservice.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import java.net.URL
import java.time.LocalDateTime

class SearchDto {

    companion object {
        val converter: Converter = Mappers.getMapper(Converter::class.java)
    }

    @Mapper
    interface Converter {

    }

    @Schema(title = "검색 결과")
    data class searchResponse(

        @Schema(title = "blogname)")
        val blogname: String?,

        @Schema(title = "contents)")
        val contents: String?,

        @Schema(title = "datetime)")
        val datetime: LocalDateTime?,

        @Schema(title = "thumbnail)")
        val thumbnail: URL?,

        @Schema(title = "blogname)")
        val title: String?,

        @Schema(title = "blogname)")
        val url: URL?,
    )

}