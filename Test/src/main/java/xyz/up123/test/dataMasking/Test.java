package xyz.up123.test.dataMasking;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.dataMasking
 * @ClassName: Test
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/2/28 18:01
 * @Version: 1.0
 */
public class Test {
    public static void main(String[] args) {

        DataMaskingUtil.getInstance();

        User user = new User();
        user.setUseId(1L);
        user.setUseNo("123456201902281234");
        user.setPassword("111111");
        //user.setUseName("尼古拉斯·叶里肯不拉");
        user.setUseName("二师兄");
        user.setMobile("15812345678");
        user.setIdCard("123456201902281234");
        user.setAge(100);
        ObjectMapper mapper = new ObjectMapper();

        String s1 = null;
        try {
            //mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            s1 = mapper.writeValueAsString(user);
            System.out.println(s1);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String jsonInString = "[{\"useId\":1,\"useNo\":\"1234**********1234\",\"useName\":\"二*兄\",\"mobile\":\"15******78\",\"age\":100,\"idCard\":\"123************234\"},{\"useId\":1,\"useNo\":\"1234**********1234\",\"useName\":\"二*兄\",\"mobile\":\"15******78\",\"age\":100,\"idCard\":\"123************234\"}]";
        //CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, User.class);
        try {
            //List<User> personList = mapper.readValue(jsonInString, javaType);
            List<User> personList1 = mapper.readValue(jsonInString, new TypeReference<List<User>>(){});
            for (int i = 0; i < personList1.size(); i++) {
                System.out.println(personList1.get(i).toString());
                System.out.println();
            }
            System.out.println("==================");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //第二参数是 map 的 key 的类型，第三参数是 map 的 value 的类型
        /*MapType javaType1 = mapper.getTypeFactory().constructMapType(HashMap.class,String.class,User.class);
        try {
            Map<String, User> personMap = mapper.readValue(jsonInString,javaType1);
            Map<String, User> personMap2 = mapper.readValue(jsonInString, new TypeReference<Map<String, User>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        //PropertyAccessor 支持的类型有 ALL,CREATOR,FIELD,GETTER,IS_GETTER,NONE,SETTER
        //Visibility 支持的类型有 ANY,DEFAULT,NON_PRIVATE,NONE,PROTECTED_AND_PUBLIC,PUBLIC_ONLY



        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
