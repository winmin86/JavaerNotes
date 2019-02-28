package xyz.up123.test.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.jackson
 * @ClassName: NullToZeroSerializer
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/2/28 10:57
 * @Version: 1.0
 */
@JacksonStdImpl
public class NullToZeroSerializer extends JsonSerializer implements ContextualSerializer {

    private Class<?> type;

    public NullToZeroSerializer() {
    }

    public NullToZeroSerializer(final JavaType type) {
        this.type = type == null ? Object.class : type.getRawClass();
    }

    /*@Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException,JsonProcessingException {
        jsonGenerator.writeNumber("");
    }*/

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        if (o instanceof Short) {
            if (((Short) o).compareTo((short)0) == 0) {
                jsonGenerator.writeNumber(0);
            } else {
                jsonGenerator.writeNumber(((Short) o).shortValue());
            }
        }
        if (o instanceof Integer) {
            if (((Integer) o).intValue() == 0) {
                jsonGenerator.writeNumber(0);
            } else {
                jsonGenerator.writeNumber(((Integer) o).intValue());
            }
        }
        if (o instanceof Float) {
            if (((Float) o).compareTo(0f) == 0) {
                jsonGenerator.writeNumber(0);
            } else {
                jsonGenerator.writeNumber(((Float) o).floatValue());
            }
        }

        if (o instanceof Double) {
            if (((Double) o).compareTo(0D) == 0) {
                jsonGenerator.writeNumber(0);
            } else {
                jsonGenerator.writeNumber(((Double) o).doubleValue());
            }
        }

        if (o instanceof Long) {
            if (((Long) o).compareTo(0L) == 0) {
                jsonGenerator.writeNumber(0);
            } else {
                jsonGenerator.writeNumber(((Long) o).longValue());
            }
        }
        if (o instanceof BigDecimal) {
            if (((BigDecimal) o).compareTo(BigDecimal.ZERO) == 0) {
                jsonGenerator.writeNumber(0);
            }else {
                jsonGenerator.writeNumber((BigDecimal) o);
            }
        }

    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        return new NullToZeroSerializer(property.getType());
    }
}
