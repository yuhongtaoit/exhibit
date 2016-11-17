package edu.zstu.exhibit.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import edu.zstu.exhibit.service.ProductService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by aning on 16/6/2.
 */
public class QrcodeUtil {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private String[] barcodes;

    public QrcodeUtil(String[] barcodes) {
        this.barcodes = barcodes;
    }

    public InputStream creat() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(composeImg(barcodes), "png", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return is;
    }

    public InputStream[] creats(int cout) throws Exception {
        InputStream[] iss = new InputStream[cout];
        BufferedImage[] bufferedImages = composeImgs(barcodes, cout);
        for (int i = 0; i < bufferedImages.length; i++) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImages[i], "png", os);
            iss[i] = new ByteArrayInputStream(os.toByteArray());
        }
        return iss;
    }

    public void creatToLocal() throws Exception {
        int barcodeLen = barcodes.length;
        if (barcodeLen / 6 >= 1 && barcodeLen != 6) {
            int cout = 1;
            if (barcodeLen % 6 == 0)
                cout = barcodeLen / 6;
            else
                cout = barcodeLen / 6 + 1;
            BufferedImage[] bufferedImages = composeImgs(barcodes, cout);
            for (int i = 0; i < bufferedImages.length; i++) {
                File file = new File(System.getProperty("exhibit.root") + "/tmp");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File outFile = new File(System.getProperty("exhibit.root") + "/tmp/out" + i + ".png");
                ImageIO.write(bufferedImages[i], "png", outFile);
            }

        } else {
            File outFile = new File(System.getProperty("exhibit.root") + "/tmp/out.png");
            System.out.print(outFile.getAbsolutePath());
            ImageIO.write(composeImg(barcodes), "png", outFile);//写图片
        }
    }

    /**
     * 获取每个productBarcode的BufferedImage
     * 不大于6张图片
     */
    public BufferedImage composeImg(String[] barcodes) throws Exception {
        BufferedImage[] bufImg = new BufferedImage[barcodes.length];
        int i = 0;
        for (i = 0; i < barcodes.length; i++) {
            if (barcodes[i] == null)
                break;
            bufImg[i] = getQrcode(barcodes[i]);
        }
        if (i < 6) {
            BufferedImage[] bufImgLess = new BufferedImage[i];
            for (int j = 0; j < i; j++) {
                bufImgLess[j] = bufImg[j];
            }
            return compose(bufImgLess);
        }
        return compose(bufImg);
    }

    /**
     * 6张以上图片
     *
     * @param barcodes
     * @param cout
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public BufferedImage[] composeImgs(String[] barcodes, int cout) throws Exception {
        BufferedImage[] bufferedImages = new BufferedImage[cout];
        String barcodess[][] = new String[cout][6];
        int k = 0;
        for (int i = 0; i < cout; i++) {
            for (int j = 0; j < 6 && k < barcodes.length; k++, j++) {
                barcodess[i][j] = barcodes[k];
            }
        }

        for (int i = 0; i < cout; i++)
            bufferedImages[i] = composeImg(barcodess[i]);

        return bufferedImages;
    }

    /**
     * 将所有二维码合成到一张图片,每张图片最多六个
     *
     * @param bufImg
     * @return
     * @throws IOException
     */
    public BufferedImage compose(BufferedImage[] bufImg) throws Exception {
        BufferedImage imageNew = new BufferedImage(595, 842, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) imageNew.createGraphics();
        graphics.fillRect(0, 0, 595, 842);
        graphics.setColor(Color.WHITE);
        graphics.dispose();
        int j = 0, k = 0;
        for (int i = 0; i < bufImg.length; i++) {
            BufferedImage temp = bufImg[i];
            int width = temp.getWidth();//图片宽度
            int height = temp.getHeight();//图片高度
            //从图片中读取RGB
            int imageArray[] = new int[width * height];
            imageArray = temp.getRGB(0, 0, width, height, imageArray, 0, width);
            if (i % 2 == 0) {
                imageNew.setRGB(0, j * 200 + j * 40, width, height, imageArray, 0, width);
                j++;
            } else {
                k++;
                imageNew.setRGB(300, (k - 1) * 200 + (k - 1) * 40, width, height, imageArray, 0, width);
            }
        }

        return imageNew;
    }

    public BufferedImage getQrcode(String barcode) throws Exception {
        ProductService productService = new ProductService();
        String server_ip = getServerIp();
        String port = getPort();
        String content = "http://" + server_ip + ":" + port + "/exhibit/product/scan?productBarcode=" + barcode;
        System.out.println(content);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200, hints);
        BufferedImage imageNew = toBufferedImage(bitMatrix);
        int qrWidth = imageNew.getWidth();
        int qrHeight = imageNew.getHeight();
        int[] qrImageArray = new int[qrWidth * qrHeight];
        qrImageArray = imageNew.getRGB(0, 0, qrWidth, qrHeight, qrImageArray, 0, qrWidth);

        //为barcode构造图片
        int textWidth = 200;// 图片的宽度
        int textHeight = 20;// 图片的高度
        BufferedImage textImage = new BufferedImage(textWidth, textHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = textImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, textWidth, textHeight);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("宋体", Font.BOLD, 10));
        graphics.drawString(barcode + "  " + productService.getProductCodeByProductBarcodes(barcode), 40, 10);
        graphics.dispose();
        int[] textImageArray = new int[textWidth * textHeight];
        textImageArray = textImage.getRGB(0, 0, textWidth, textHeight, textImageArray, 0, textWidth);

        //生成新图片
        BufferedImage bufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        bufferedImage.setRGB(0, 0, qrWidth, qrHeight, qrImageArray, 0, qrWidth);
        bufferedImage.setRGB(0, qrHeight - 20, textWidth, textHeight, textImageArray, 0, textWidth);


        return bufferedImage;

    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }


    public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }


    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    /**
     * 创建一个含有指定颜色字符串的图片
     */
    public BufferedImage drawString(String message) {
        BufferedImage image = new BufferedImage(200, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        Font f = new Font("宋体", Font.BOLD, 10);
        g.setFont(f);
        int len = message.length();
        g.drawString(message, (200 - 10 * len) / 2,
                (20 + (int) (10 / 1.5)) / 2);
        g.dispose();
        return image;
    }

    public String getServerIp() {
        Properties properties = new Properties();
        InputStream ins = getClass().getResourceAsStream("/host.properties");
        String ip = "";
        try {
            properties.load(ins);
            ip = properties.getProperty("server_ip");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ip;
    }

    public String getPort() {
        Properties properties = new Properties();
        InputStream ins = getClass().getResourceAsStream("/host.properties");
        String port = "";
        try {
            properties.load(ins);
            port = properties.getProperty("port");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return port;
    }

}
