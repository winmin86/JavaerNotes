package xyz.up123.test.dataMasking;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.jackson
 * @ClassName: SensitiveType
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/2/28 16:44
 * @Version: 1.0
 */
public enum SensitiveType{
    //通用的
    SENSITIVE,
    /**
     * 中文名
     */
    CHINESE_NAME,

    /**
     * 身份证号
     */
    ID_CARD,
    /**
     * 座机号
     */
    FIXED_PHONE,
    /**
     * 手机号
     */
    MOBILE_PHONE,
    /**
     * 地址
     */
    ADDRESS,
    /**
     * 电子邮件
     */
    EMAIL,
    /**
     * 银行卡
     */
    BANK_CARD,
    /**
     * 公司开户银行联号
     */
    CNAPS_CODE,

    ARRAY
}
