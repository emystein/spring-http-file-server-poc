package ar.com.flow.download.controller

import ar.com.flow.download.Memory
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class MemoryUsageLogger {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Pointcut("execution(* ar.com.flow.download.controller.*.*(..))")
    fun controllerPointcut() {
        // empty
    }

    @Before("controllerPointcut()")
    fun logMemoryUsedBeforeExecution() {
        logMemoryUsed("before")
    }

    @After("controllerPointcut()")
    fun logMemoryUsedAfterExecution() {
        logMemoryUsed("after")
    }

    private fun logMemoryUsed(relativeTime: String) {
        log.trace("JVM used memory $relativeTime execute action: ${Memory.usedInMB()} MB")
    }
}
