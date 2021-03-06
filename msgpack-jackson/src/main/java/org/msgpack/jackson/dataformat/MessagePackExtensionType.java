package org.msgpack.jackson.dataformat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

@JsonSerialize(using = MessagePackExtensionType.Serializer.class)
public class MessagePackExtensionType
{
    private final byte type;
    private final byte[] data;

    public MessagePackExtensionType(byte type, byte[] data)
    {
        this.type = type;
        this.data = data;
    }

    public byte getType()
    {
        return type;
    }

    public byte[] getData()
    {
        return data;
    }

    public static class Serializer extends JsonSerializer<MessagePackExtensionType>
    {
        @Override
        public void serialize(MessagePackExtensionType value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException, JsonProcessingException
        {
            if (gen instanceof MessagePackGenerator) {
                MessagePackGenerator msgpackGenerator = (MessagePackGenerator) gen;
                msgpackGenerator.writeExtensionType(value);
            }
            else {
                throw new IllegalStateException("'gen' is expected to be MessagePackGenerator but it's " + gen.getClass());
            }
        }
    }
}
