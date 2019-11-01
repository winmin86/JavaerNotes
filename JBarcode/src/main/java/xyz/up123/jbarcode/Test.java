package xyz.up123.jbarcode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/1 13:33
 * @Description：TODO
 * @Version: V1.0.0
 */
public class Test {
    public static void main(String[] args) {

        BufferedImage bufferedImage = ZxingTool.encodeBarcode("123456789456", 400, 300);
        BufferedImage encodeQRcode = ZxingTool.encodeQRcode("123456789456", 400, 300);

        //ZxingTool.createImg(bufferedImage, "imgs/sdfsdf.png");
        ZxingTool.createImg(encodeQRcode, "imgs/werwerw.png");



    }


}
