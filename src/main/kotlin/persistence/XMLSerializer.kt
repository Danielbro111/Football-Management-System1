package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import ie.setu.models.Player
import ie.setu.models.Team
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Reads and deserializes an object from the XML file.
 *
 * @return The deserialized object as Any.
 * @throws Exception if there's an error during the reading process.
 */
class XMLSerializer(private val file: File) : Serializer {

    /**
     * Reads and deserializes an object from the XML file.
     *
     * @return The deserialized object as Any.
     * @throws Exception if there's an error during the reading process.
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Team::class.java, Player::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Writes and serializes an object to the XML file.
     *
     * @param obj The object to be serialized and written.
     * @throws Exception if there's an error during the writing process.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
