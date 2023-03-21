package com.example.searchservice.common.component

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "src-api")
class SourceInfo(
    val kakao: KakaoProperties,
    val naver: NaverProperties
) {

    data class KakaoProperties(
        val host: String,
        val restApi: RestApi
    )

    data class RestApi(
        val key: String,
        val value: String
    )

    data class NaverProperties(
        val host: String,
        val clientId: ClientId,
        val clientSecret: ClientSecret
    )

    data class ClientId(
        val key: String,
        val value: String
    )

    data class ClientSecret(
        val key: String,
        val value: String
    )

}