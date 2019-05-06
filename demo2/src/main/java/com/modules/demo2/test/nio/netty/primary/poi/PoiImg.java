package com.modules.demo2.test.nio.netty.primary.poi;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;

public class PoiImg {

    private OutputStream qr(String content){
        final int width = 300;
        final int height = 300;
        final String format = "png";


        //定义二维码的参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //生成二维码
        try{
            //生成文件形式
            //OutputStream stream = new FileOutputStream("/Users/bqj/Desktop/_xx_.png");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, format, baos);
        }catch(Exception e){

        }
        return baos;
    }

    private void export() throws IOException {
        final String content = "http://fdfs.banquanjia.com.cn/group2/M00/1C/8E/CgoKC1zGwwCAMOX6AAm-aUs29lg084.png";
        final String content2 = "https://mp.weixin.qq.com/s?src=11&timestamp=1556529881&ver=1576&signature=WU8zynKu17fmKl1htiN09zKNe4jk0oJS6wUUa6LuByHIuBY585ES8z3fD7VhPzM6bgdEbTiFvfNegn*N8htV0OStycewqqJp7CO83ZNYNb-I4HhrfAB48VpeiL2LIPHl&new=1";
        //文件形式读流
//        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//        BufferedImage bufferedImage = ImageIO.read(new File("/Users/bqj/Desktop/_xx_.png"));
//        ImageIO.write(bufferedImage, "png", byteArrayOut);
        //直接流形式
        ByteArrayOutputStream byteArrayOut = (ByteArrayOutputStream) qr(content);
        ByteArrayOutputStream byteArrayOut2 = (ByteArrayOutputStream) qr(content2);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet1 = workbook.createSheet("sheet1");
        HSSFSheet sheet = workbook.createSheet("sheet2");

        sheet.setColumnWidth(0, 1024*4);
        HSSFRow oneRow = sheet.createRow(0);
        oneRow.setHeight((short) (256*8));
        HSSFCell cell0 = oneRow.createCell(0);
        cell0.setCellStyle(getStyle(workbook));



        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255,(short) 0, 0, (short) 0, 0);
        patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
        HSSFRow twoRow = sheet.createRow(1);
        twoRow.setHeight((short) (256*8));
        HSSFCell cell1 = oneRow.createCell(0);
        cell1.setCellStyle(getStyle(workbook));
        anchor = new HSSFClientAnchor(0, 0, 0, 0,(short) 0, 1, (short) 1, 2);
        patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut2.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
        workbook.write(new FileOutputStream("/Users/bqj/Desktop/pub_43.xls"));
    }

    private HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;
    }

    public static void main(String[] args) throws IOException {
        new PoiImg().export();
    }
}

final class MatrixToImageWriter {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private MatrixToImageWriter() {
    }
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
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
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format "
                    + format);
        }
    }
}