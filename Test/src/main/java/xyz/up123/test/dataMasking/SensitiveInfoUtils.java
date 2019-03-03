package xyz.up123.test.dataMasking;


import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class SensitiveInfoUtils {
    public static String sensitive(final String fullName, final String field) {
        DataMasking dataMasking = DataMaskingUtil.getInstance().get(field);
        return pad(fullName, dataMasking);
    }
    /**
     * <p>
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     */
    public static String chineseName(final String fullName, final String field) {
        DataMasking dataMasking = DataMaskingUtil.getInstance().get(field);
        return pad(fullName, dataMasking);
    }

    public static String pad(String value, DataMasking dm) {
        String result = value;
        if (StringUtils.isBlank(value) || dm == null) {
            return result;
        }

        if (dm.getType().equals(MaskingType.LEFT)) {
            final String leftStr = StringUtils.left(value, dm.getLeft());
            result = StringUtils.rightPad(leftStr, StringUtils.length(value), dm.getFiller());
        } else if (dm.getType().equals(MaskingType.RIGHT)) {
            if (dm.getRight() < value.length()) {
                final String rightStr = StringUtils.right(value, dm.getRight());
                result = StringUtils.leftPad(rightStr,StringUtils.length(value), dm.getFiller());
            }
        } else {
            Integer padLength = value.length() - dm.getLeft() - dm.getRight();//需要填充的长度
            if (padLength > 0) {
                final String leftStr = StringUtils.left(value, dm.getLeft());
                final String rightStr = StringUtils.right(value, dm.getRight());
                result = leftStr.concat(StringUtils.repeat(dm.getFiller(), padLength)).concat(rightStr);
            }
        }
        return result;
    }
    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     */
    public static String chineseName(final String familyName, final String givenName, final String field) {
        System.out.println(familyName);
        System.out.println(givenName);
        if (StringUtils.isBlank(familyName) || StringUtils.isBlank(givenName)) {
            return "";
        }
        return chineseName(familyName + givenName, field);
    }

    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位。<例子：*************5762>
     */
    public static String idCardNum(final String id) {
        if (StringUtils.isBlank(id)) {
            return "";
        }

        return StringUtils.left(id, 3).concat(StringUtils
                .removeStart(StringUtils.leftPad(StringUtils.right(id, 3), StringUtils.length(id), "*"),
                        "***"));
    }

    /**
     * [固定电话] 后四位，其他隐藏<例子：****1234>
     */
    public static String fixedPhone(final String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*");
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
     */
    public static String mobilePhone(final String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.left(num, 2).concat(StringUtils
                .removeStart(StringUtils.leftPad(StringUtils.right(num, 2), StringUtils.length(num), "*"),
                        "***"));

    }

    /**
     * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：北京市海淀区****>
     *
     * @param sensitiveSize 敏感信息长度
     */
    public static String address(final String address, final int sensitiveSize) {
        if (StringUtils.isBlank(address)) {
            return "";
        }
        final int length = StringUtils.length(address);
        return StringUtils.rightPad(StringUtils.left(address, length - sensitiveSize), length, "*");
    }

    /**
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
     */
    public static String email(final String email) {
        if (StringUtils.isBlank(email)) {
            return "";
        }
        final int index = StringUtils.indexOf(email, "@");
        if (index <= 1) {
            return email;
        } else {
            return StringUtils.rightPad(StringUtils.left(email, 1), index, "*")
                    .concat(StringUtils.mid(email, index, StringUtils.length(email)));
        }
    }

    /**
     * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:6222600**********1234>
     */
    public static String bankCard(final String cardNum) {
        if (StringUtils.isBlank(cardNum)) {
            return "";
        }
        return StringUtils.left(cardNum, 6).concat(StringUtils.removeStart(
                StringUtils.leftPad(StringUtils.right(cardNum, 4), StringUtils.length(cardNum), "*"),
                "******"));
    }

    /**
     * [公司开户银行联号] 公司开户银行联行号,显示前两位，其他用星号隐藏，每位1个星号<例子:12********>
     */
    public static String cnapsCode(final String code) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        return StringUtils.rightPad(StringUtils.left(code, 2), StringUtils.length(code), "*");
    }


}








