package com.example.searchservice.common.component

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Component
@Aspect
class TransactionLockAspect {

    @Pointcut("@annotation(com.example.searchservice.common.component.TxLock)")
    fun txLockPointcut() = Unit

    @Before("txLockPointcut()")
    fun txBeforeLogging(joinPoint: JoinPoint) {
        print("[+txLock]", joinPoint)
    }

    @After("txLockPointcut()")
    fun txAfterLogging(joinPoint: JoinPoint) {
        print("[-txLock]", joinPoint)
    }

    private fun print(prefix: String, joinPoint: JoinPoint) {
        val methodSignature = joinPoint.signature as MethodSignature
        val txLock = methodSignature.method.getAnnotation(TxLock::class.java)

        println("$prefix method(${methodSignature.name}), tableName(${txLock.tableName}), isoLevel(${txLock.isoLevel}))")
    }

}