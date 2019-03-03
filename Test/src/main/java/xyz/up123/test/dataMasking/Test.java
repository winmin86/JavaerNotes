package xyz.up123.test.dataMasking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
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


        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
