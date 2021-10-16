package ar.com.flow.download.controller

import ar.com.flow.download.Memory
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@Aspect
/**
 * Uses @Before and @After instead of @Around because @Around makes the controllers to return empty files.
 */
class MemoryUsageLogger {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Pointcut("execution(* ar.com.flow.download.controller.*.*(..))")
    fun controllerPointcut() {
        // empty
    }

    @Before("controllerPointcut()")
    fun logMemoryUsedBeforeExecution(joinPoint: JoinPoint) {
        logMemoryUsed("before", joinPoint.signature.name)
    }

    @After("controllerPointcut()")
    fun logMemoryUsedAfterExecution(joinPoint: JoinPoint) {
        logMemoryUsed("after", joinPoint.signature.name)
    }

    private fun logMemoryUsed(relativeTime: String, action: String) {
        log.trace("JVM used memory $relativeTime $action: ${Memory.usedInMB()} MB")
    }
}
