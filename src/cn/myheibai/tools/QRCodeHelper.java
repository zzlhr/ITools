package cn.myheibai.tools;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by lhr on 2017/4/13.
 *
 * 该类用于生成和解析二维码，基于Google的Zxing
 *
 */
public class QRCodeHelper {
    /**
     * 生成二维码
     * @param path 生成文件存放路径
     * @param text 需要生成二维码的内容
     * @return     生成文件路径
     */
    public static String createQrcode(String path, String text){
        String qrcodeFilePath = "";
        try {
            int qrcodeWidth = 300;
            int qrcodeHeight = 300;
            String qrcodeFormat = "png";
            HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, qrcodeWidth, qrcodeHeight, hints);

            BufferedImage image = new BufferedImage(qrcodeWidth, qrcodeHeight, BufferedImage.TYPE_INT_RGB);
            Random random = new Random();
            File QrcodeFile = new File(path + random.nextInt() + "." + qrcodeFormat);
            ImageIO.write(image, qrcodeFormat, QrcodeFile);
            MatrixToImageWriter.writeToFile(bitMatrix, qrcodeFormat, QrcodeFile);
            qrcodeFilePath = QrcodeFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qrcodeFilePath;
    }


    /**
     * 解析二维码
     * @param s     需要解析二维码的路径
     * @return      解析结果
     */
    public static String deQrcode(String s) {
        String filePath = s;
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
            return result+"";
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
//    public static void main(String[] args){
////        System.out.println(createQrcode("/users/apple/Pictures/test/", "{url : http://www.baidu.com}"));
//        String filepath = "/users/apple/Pictures/test/917271818.png";
//        System.out.println(deQrcode(filepath));
//    }
}
