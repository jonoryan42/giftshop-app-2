package persistence

import java.lang.Exception

interface Serializer {
    /**
     * Allows User to Save objects.
     */
    @Throws(Exception::class)
    fun write(obj: Any?)

    /**
     * Allows User to load objects.
     */
    @Throws(Exception::class)
    fun read(): Any?
}