package com.modules.prime.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;

public class QrUtil {

    private static final transient Logger logger = LoggerFactory.getLogger(QrUtil.class);

    //生成qr outputStream
    public static ByteArrayOutputStream getQrStream(String content){
        final int width = 300;
        final int height = 300;
        final String format = "png";

        //定义二维码的参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        ByteArrayOutputStream imgBaos = new ByteArrayOutputStream();
        //生成二维码
        try{
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, format, imgBaos);
        }catch(Exception e){
            System.out.println(e);
            logger.error(e);
        }
        return imgBaos;
    }
}


class MatrixToImageWriter {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private MatrixToImageWriter() {
    }
    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    private static BufferedImage LogoMatrix(BufferedImage matrixImage) throws IOException {
        /**
         * 读取二维码图片，并构建绘图对象
         */
        Graphics2D g2 = matrixImage.createGraphics();

        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();

        /**
         * 读取Logo图片
         */

        InputStream is = QrUtil.class.getResourceAsStream("/img/logo.png");

        //String path = Qrcode.class.getClassLoader().getResource("/img/logo.jpg").getPath();
        //BufferedImage logo = ImageIO.read(new File(path));
//        byte[] bytes = null;
//        byte[] buff = new byte[4096];
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int cursor;
//        try {
//            while ((cursor = is.read(buff, 0, buff.length)) != -1){
//                baos.write(buff, 0, cursor);
//            }
//            baos.flush();
//            bytes = baos.toByteArray();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                baos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if(is != null){
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        Image logo = ImageIO.read(is);

        //开始绘制图片
        g2.drawImage(logo,matrixWidth/5*2,matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5, null);//绘制
        BasicStroke stroke = new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);// 设置笔画对象
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth/5*2, matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5,20,20);
        g2.setColor(Color.white);
        g2.draw(round);// 绘制圆弧矩形

        //设置logo 有一道灰色边框
        BasicStroke stroke2 = new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke2);// 设置笔画对象
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth/5*2+2, matrixHeigh/5*2+2, matrixWidth/5-4, matrixHeigh/5-4,20,20);
        g2.setColor(new Color(128,128,128));
        g2.draw(round2);// 绘制圆弧矩形

        g2.dispose();
        matrixImage.flush() ;
        return matrixImage ;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format "
                    + format + " to " + file);
        }
    }
    public static void writeToStream(BitMatrix matrix, String format,
                                     OutputStream stream) throws IOException {
        BufferedImage image = LogoMatrix(toBufferedImage(matrix));
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format "
                    + format);
        }
    }
}