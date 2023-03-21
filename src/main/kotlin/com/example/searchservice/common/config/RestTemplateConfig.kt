package com.example.searchservice.common.config

import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate(HttpComponentsClientHttpRequestFactory()
            .apply {
                this.setConnectTimeout(3000)
                this.setConnectionRequestTimeout(5000)
                this.setReadTimeout(5000)
                this.httpClient = HttpClientBuilder.create()
                    .setMaxConnTotal(500)
                    .setMaxConnPerRoute(100)
                    .build()
            })
    }

}