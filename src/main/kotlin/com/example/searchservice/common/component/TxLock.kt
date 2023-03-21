package com.example.searchservice.common.component

import org.springframework.transaction.annotation.Isolation
import java.lang.annotation.Inherited

@Inherited
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class TxLock(
    val tableName: String,
    val isoLevel: Isolation = Isolation.DEFAULT
)
