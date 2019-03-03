package xyz.up123.test.dataMasking;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.dataMasking
 * @ClassName: DataMasking
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/3/1 14:17
 * @Version: 1.0
 */
public class DataMasking {
    private String name;
    private MaskingType type = MaskingType.LEFT;
    private Integer left = 1;//左边长度
    private Integer right = 1; //右边长度
    private String filler = "*"; //填充物

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MaskingType getType() {
        return type;
    }

    public void setType(MaskingType type) {
        this.type = type;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }
}
