package com.bluejean.modules.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.collections.map.HashedMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangrl on 2016/8/10.
 */
public class QRCode {
    // 图片宽度的一半
    private static final int IMAGE_WIDTH = 100;
    private static final int IMAGE_HEIGHT = 100;
    private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
    private static final int FRAME_WIDTH = 2;
    // 二维码写码器
    private static MultiFormatWriter mutiWriter = new MultiFormatWriter();

    public static void main(String[] args) {
//        依次为内容(不支持中文),宽,长,中间图标路径,储存路径
        QRCode.encode("http://www.lanniuzai.com/",512, 512, "D:/logo.png", "D:/2013-01.jpg");
    }
    public static void encode(String content, int width, int height, String logoImagePath, String destImagePath) {
        try {
            ImageIO.write(genBarcode(content, width, height, logoImagePath), "jpg", new File(destImagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage genBarcode(String content, int width, int height, String logoImagePath) throws WriterException, IOException {
        //读取源图像
        BufferedImage scaleImage = scale(logoImagePath, IMAGE_WIDTH, IMAGE_HEIGHT, true);
        int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
        for (int i = 0; i < scaleImage.getWidth(); i++) {
            for (int j = 0; j < scaleImage.getHeight(); j++) {
                srcPixels[i][j] = scaleImage.getRGB(i, j);
            }
        }

        Map<EncodeHintType, Object> hint = new HashMap<>();
        hint.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        //生成二维码
        BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hint);

        //二维矩阵转为一维像素数组
        int halfW = matrix.getWidth() / 2;
        int halfH = matrix.getHeight() / 2;
        int[] pixels = new int[width * height];

        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                //左上角颜色，根据自己需要调整颜色范围和颜色
                if (x > 0 && x < 170 && y > 0 && y < 170) {
                    Color color = new Color(231, 144, 56);
                    int colorInt = color.getRGB();
                    pixels[y * width + x] = matrix.get(x, y) ? colorInt : 16777215;
                } else if (x > halfW - IMAGE_HALF_WIDTH && x < halfW + IMAGE_HALF_WIDTH && y > halfH - IMAGE_HALF_WIDTH && y < halfH + IMAGE_HALF_WIDTH){
                    //读取图片
                    pixels[y * width + x] = srcPixels[x - halfW + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
                } else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH) || (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH) || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH - IMAGE_HALF_WIDTH + FRAME_WIDTH) || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH && y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
                    // 在图片四周形成边框
                    pixels[y * width + x] = 0xfffffff;
                } else {
                    //二维码颜色
                    int num1=(int)(50-(50.0-13.0)/matrix.getHeight()*(y+1));
                    int num2=(int)(165-(165.0-72.0)/matrix.getHeight()*(y+1));
                    int num3=(int)(162-(162.0-107.0)/matrix.getHeight()*(y+1));
                    Color color= new Color(num1,num2,num3);
                    int colorInt=color.getRGB();
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
                    pixels[y * width + x] = matrix.get(x, y) ? colorInt: 16777215;
                    //0x000000:0xffffff
                }
            }
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.getRaster().setDataElements(0, 0, width, height, pixels);

        return image;
    }

    /**
     * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标
     * @param srcImagePath 源文件地址
     * @param width 目标宽度
     * @param height 目标高度
     * @param hasFiller 比例不对时是否需要补白：true为补白; false为不补白
     * @return
     */
    private static BufferedImage scale(String srcImagePath, int width, int height, boolean hasFiller) throws IOException {
        //缩放比例
        double ratio = 0.0;
        BufferedImage srcImage = ImageIO.read(new File(srcImagePath));
        Image destImage = srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);

        //计算比例
        if (srcImage.getHeight() > height || srcImage.getWidth() > width) {
            if (srcImage.getHeight() > srcImage.getWidth()) {
                ratio = Integer.valueOf(height).doubleValue() / srcImage.getHeight();
            } else {
                ratio = Integer.valueOf(width).doubleValue() / srcImage.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
            destImage = op.filter(srcImage, null);
        }

        if (hasFiller) {
            //补白
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.white);
            graphics.fillRect(0, 0, width, height);
            if (width == destImage.getWidth(null)) {
                graphics.drawImage(destImage, 0, (height - destImage.getHeight(null)) / 2, destImage.getWidth(null), destImage.getHeight(null), Color.white, null);
            } else {
                graphics.drawImage(destImage, (width - destImage.getWidth(null) / 2), 0, destImage.getWidth(null), destImage.getHeight(null), Color.white, null);
            }
            graphics.dispose();
            destImage = image;
        }
        return (BufferedImage) destImage;
    }
}
