package edu.zstu.exhibit.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by aning on 16/6/8.
 */
public class CompressUtil {
    public static InputStream compressImg(MultipartFile img) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(img.getInputStream());
        int outputWidth = 800;
        int outputHeight = 800;
        Image image = bufferedImage.getScaledInstance(outputWidth, outputHeight, Image.SCALE_DEFAULT);
        double rate1 = ((double) image.getWidth(null)) / (double) outputWidth + 0.1;
        double rate2 = ((double) image.getHeight(null)) / (double) outputHeight + 0.1;
        // 根据缩放比率大的进行缩放控制
        double rate = rate1 > rate2 ? rate1 : rate2;
        int newWidth = (int) (((double) image.getWidth(null)) / rate);
        int newHeight = (int) (((double) image.getHeight(null)) / rate);

        BufferedImage desc = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);  //缩放图像
        Graphics g = desc.getGraphics();
        g.drawImage(image, 0, 0, null); // 绘制缩小后的图
        g.dispose();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(desc, "JPEG", bos);

        InputStream is = new ByteArrayInputStream(bos.toByteArray());
        return is;
    }
}
