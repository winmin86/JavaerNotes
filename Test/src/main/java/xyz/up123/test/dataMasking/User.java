package xyz.up123.test.dataMasking;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Arrays;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.jackson
 * @ClassName: User
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/2/28 16:39
 * @Version: 1.0
 */
//Include.Include.ALWAYS 默认 
//Include.NON_DEFAULT 属性为默认值不序列化 
//Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化 
//Include.NON_NULL 属性为NULL 不序列化 

@JsonSerialize(include= JsonSerialize.Inclusion.NON_DEFAULT)
public class User {

    private Long useId;

    @SensitiveInfo(type = SensitiveType.SENSITIVE)
    private String useNo;

    @SensitiveInfo(type = SensitiveType.CHINESE_NAME)
    private String useName;

    @SensitiveInfo(type = SensitiveType.MOBILE_PHONE)
    private String mobile;

    private String sex;

    private Integer age;

    private String nativePlace;

    @SensitiveInfo(type = SensitiveType.ID_CARD)
    private String idCard;

    private String borrowingLevel;

    @SensitiveInfo(type = SensitiveType.ARRAY)
    private String[] classmate;

    public Long getUseId() {
        return useId;
    }

    public void setUseId(Long useId) {
        this.useId = useId;
    }

    public String getUseNo() {
        return useNo;
    }

    public void setUseNo(String useNo) {
        this.useNo = useNo;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBorrowingLevel() {
        return borrowingLevel;
    }

    public void setBorrowingLevel(String borrowingLevel) {
        this.borrowingLevel = borrowingLevel;
    }

    public String[] getClassmate() {
        return classmate;
    }

    public void setClassmate(String[] classmate) {
        this.classmate = classmate;
    }

    @Override
    public String toString() {
        return "User{" +
                "useId=" + useId +
                ", useNo='" + useNo + '\'' +
                ", useName='" + useName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", nativePlace='" + nativePlace + '\'' +
                ", idCard='" + idCard + '\'' +
                ", borrowingLevel='" + borrowingLevel + '\'' +
                ", classmate=" + Arrays.toString(classmate) +
                '}';
    }
}

