package xyz.up123.test.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.jackson
 * @ClassName: Test
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/2/28 10:43
 * @Version: 1.0
 */
public class Test {

    public static void main(String[] args) throws JsonProcessingException{
        TestSerializerBean bean = new TestSerializerBean();

        bean.setAge(0);
        //bean.setNumber(0);
        bean.setMoney(new BigDecimal(20));

        /*ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(bean);
        System.out.println(str);*/

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator gen, SerializerProvider serializers) throws IOException,
                    JsonProcessingException {
                //gen.writeNumber(0);
                gen.writeString("");
            }
        });
        String s = objectMapper.writeValueAsString(bean);
        System.out.println(s);
    }


}
