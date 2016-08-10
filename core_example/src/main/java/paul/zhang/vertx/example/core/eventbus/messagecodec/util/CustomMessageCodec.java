package paul.zhang.vertx.example.core.eventbus.messagecodec.util;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

/**
 * Created by PaulZhang on 2016/8/10.
 */
public class CustomMessageCodec implements MessageCodec<CustomMessage, CustomMessage> {
    @Override
    public void encodeToWire(Buffer buffer, CustomMessage customMessage) {
        JsonObject jsonToEncode = new JsonObject();
        jsonToEncode.put("statusCode", customMessage.getStatusCode());
        jsonToEncode.put("resultCode", customMessage.getResultCode());
        jsonToEncode.put("summary", customMessage.getSummary());

        // encode object to string
        String jsonStr = jsonToEncode.encode();

        // length of json
        int length = jsonStr.getBytes().length;

        // write to buffer
        buffer.appendInt(length).appendString(jsonStr);
    }

    @Override
    public CustomMessage decodeFromWire(int i, Buffer buffer) {
        int pos = i;

        // json length
        int length = buffer.getInt(pos);

        // get json from buffer
        String jsonStr = buffer.getString(pos += 4, pos += length);
        JsonObject jsonContent = new JsonObject(jsonStr);

        // Get fields
        int statusCode = jsonContent.getInteger("statusCode");
        String resultCode = jsonContent.getString("resultCode");
        String summary = jsonContent.getString("summary");
        return new CustomMessage(statusCode, resultCode, summary);
    }

    @Override
    public CustomMessage transform(CustomMessage customMessage) {
        return customMessage;
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
