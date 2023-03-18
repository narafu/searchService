package com.example.searchservice.repository

import com.example.searchservice.entity.SearchEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ServiceRepo : JpaRepository<SearchEntity, Long> {

    fun findByKeyword(query: String): SearchEntity?

    fun findTop10ByOrderBySearchCountDesc(): List<SearchEntity>
}