package ar.com.flow.download

object Memory {
    fun usedInMB(): Long {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1014)
    }
}