package lib.kalu.core.http.body;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * description: 请求体的body为Json
 * created by kalu on 2017/5/12 13:57
 */
public class JsonRequestBody extends RequestBody {

    private static JsonRequestBody instance;

    public static JsonRequestBody getInstance() {
        if (instance == null) {
            instance = new JsonRequestBody();
        }
        return instance;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("Content-Type:application/json");
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
    }

    public RequestBody convertJsonContent(String content) {
        return RequestBody.create(this.contentType(), content);
    }
}