package persistence
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.cbor.CBORFactory
import java.io.File
import java.lang.Exception
import kotlin.jvm.Throws

class CborSerializer(private val file: File): Serializer {

    @Throws(Exception::class)
    override fun read(): Any {
        val cbor = ObjectMapper(CBORFactory())
        return cbor.readTree(file)
    }

    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val cbor = ObjectMapper(CBORFactory())
        val cborData = cbor.writeValueAsBytes(obj)
        file.writeBytes(cborData)
    }

}