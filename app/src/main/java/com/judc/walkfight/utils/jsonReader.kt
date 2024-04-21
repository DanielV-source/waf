import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

object JsonFileReader {

    fun readJsonObject(context: Context, resourceId: Int): JSONObject? {
        var jsonObject: JSONObject? = null
        try {
            val inputStream = context.resources.openRawResource(resourceId)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)
            jsonObject = JSONObject(jsonString)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return jsonObject
    }
}
