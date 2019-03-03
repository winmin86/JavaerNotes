package xyz.up123.test.dataMasking;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.jackson
 * @ClassName: SensitiveInfo
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/2/28 16:43
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveInfoSerialize.class)
public @interface SensitiveInfo {

    public SensitiveType type();

}

