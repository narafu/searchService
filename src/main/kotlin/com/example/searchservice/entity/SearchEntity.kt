package com.example.searchservice.entity

import org.hibernate.annotations.Comment
import javax.persistence.*

@Entity(name = "search")
class SearchEntity(

    @Column(nullable = false, unique = true)
    @Comment(value = "검색어")
    var keyword: String

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    @Comment(value = "검색횟수")
    var searchCount: Long = 1

}