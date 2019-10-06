package com.personal.demo.bean;


import java.io.*;
import java.util.*;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.scene.paint.Color;


public class PrintPdf {
    // 模板文件路径
    static String templatePath = "C:/Users/mu/Desktop/mo.pdf";
    // 生成的文件路径
    static String targetPath = "C:/Users/mu/Desktop/test.pdf";
    // 图片签名
    static String fieldName = "img";
    // 表格签名
    static String tableName = "table";
    // 图片路径
    static String imagePath = "C:/Users/mu/Desktop/gif/005239dfa4wnzww4w4nfu2.jpg";

    public static void addImg() throws Exception {
//     读取模板文件
        InputStream input = new FileInputStream(new File(templatePath));
        PdfReader reader = new PdfReader(input);
        FileOutputStream out = new FileOutputStream(targetPath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfStamper stamper = new PdfStamper(reader, out);

        // 提取pdf中的表单
        AcroFields form = stamper.getAcroFields();
        //设置中文
        BaseFont baseFont = (BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
        Font font = new Font(baseFont);
        ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
        fontList.add(baseFont);
        form.setSubstitutionFonts(fontList);

        //添加图片
        // 通过域名获取所在页和坐标，左下角为起点
        int pageNo = form.getFieldPositions(fieldName).get(0).page;
        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();

        // 读图片
        Image image = Image.getInstance(imagePath);
        // 获取操作的页面
        PdfContentByte under = stamper.getOverContent(pageNo);
        // 根据域的大小缩放图片
        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
        // 添加图片
        image.setAbsolutePosition(x, y);
        under.addImage(image);

        /**
         *     * 填充数据源
         *     * 其中data存放的key值与pdf模板中的文本域值相对应
         *     *
         */

        Map<String, String> data = new HashMap<String, String>();
        data.put("f1", "一");
        data.put("f2", "2");
        data.put("f3", "3");
        data.put("f4", "4");
        data.put("f5", "5");
        data.put("f6", "六");
        data.put("f7", "7");
        data.put("f8", "8");
        data.put("f9", "9");

        data.put("f2_1", "fff");
        data.put("f2_2", "ggg");
        data.put("f2_3", "hhh");


        //写入文字
         /* 取出报表模板中的所有字段 */
        for (String key : data.keySet()) {
            String value = data.get(key);
            form.setField(key, value); // 为字段赋值,注意字段名称是区分大小写的  
        }

        List<String> list4 = new ArrayList<String>();
        list4.add("项目名称");
        list4.add("检查结果");
        list4.add("单位");
        list4.add("参考范围");
        list4.add("提示");


//        List<String> list2 = new ArrayList<String>();
//        list2.add("name");
//        list2.add("jieguo");
//        list2.add("danwei");
//        list2.add("fanwei");
//        list2.add("tishi");
//
//        List<String> list2_1 = new ArrayList<String>();
//        list2_1.add("name");
//        list2_1.add("jieguo");
//        list2_1.add("danwei");
//        list2_1.add("fanwei");
//        list2_1.add("tishi");

        List<List<String>> name = new ArrayList<List<String>>();
        for (int i = 0; i < 50; i++) {
            List<String> list = new ArrayList<String>();
            list.add("name" + i);
            list.add("jieguo");
            list.add("danwei");
            list.add("fanwei");
            list.add("tishi");
            name.add(i, list);
        }


        List<List<String>> list3 = new ArrayList<List<String>>();
        list3.add(list4);
        for (int i = 0; i < name.size(); i++) {
            list3.add(name.get(i));
        }
//        list3.add(list2);
//        list3.add(list2_1);


        Map<String, List<List<String>>> listMap2 = new HashMap<String, List<List<String>>>();
        listMap2.put("eventList", list3);

        Map<String, Object> o = new HashMap<String, Object>();
        o.put("list", listMap2);

        Map<String, List<List<String>>> listMap = (Map<String, List<List<String>>>) o.get("list");
        for (String key : listMap.keySet()) {

            int pageNoTable = form.getFieldPositions(tableName).get(0).page;
            Rectangle signRectTable = form.getFieldPositions(tableName).get(0).position;

            List<List<String>> lists = listMap.get(key);
            PdfContentByte pcb = stamper.getOverContent(pageNoTable);


            int column = lists.get(0).size();
            int row = lists.size();
            PdfPTable table = new PdfPTable(column);
            float tatalWidth = signRectTable.getRight() - signRectTable.getLeft() - 1;
            int size = lists.get(0).size();
            float width[] = new float[size];
            for (int i = 0; i < size; i++) {
                if (i == 0) {
                    width[i] = 60f;
                } else {
                    width[i] = (tatalWidth - 60) / (size - 1);
                }
            }
            table.setTotalWidth(width);
            table.setLockedWidth(true);
//            table.setKeepTogether(true);
//            table.setSplitLate(false);
//            table.setSplitRows(true);
            Font FontProve = new Font(font);
            for (int i = 0; i < row; i++) {
                List<String> list = lists.get(i);
                for (int j = 0; j < column; j++) {
                    Paragraph paragraph = new Paragraph(String.valueOf(list.get(j)), FontProve);
                    PdfPCell cell = new PdfPCell(paragraph);
                    cell.setBorderWidth(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setLeading(0, (float) 1.4);
                    table.addCell(cell);
                }
                table.writeSelectedRows(0, -1, signRect.getLeft(), signRect.getTop(), pcb);
            }
        }


        stamper.setFormFlattening(true);

        stamper.close();
        reader.close();
        input.close();
    }


    public static void main(String[] args) throws Exception {
//        addImg();
//            创建文档
        Document document = new Document();
        //创建写入器
//        PdfCopy pdfCopy=new PdfCopy(document,new FileOutputStream(targetPath));
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(targetPath));

        //设置中文字体
        BaseFont baseFont = (BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
        Font font = new Font(baseFont);
        Font fontChinese = new Font(baseFont, 12, Font.NORMAL);
        Font fontMoney = new Font(baseFont, 13, Font.BOLD, BaseColor.RED);
        Font blodFont = new Font(baseFont, 17, Font.BOLD, BaseColor.BLACK);
        Font titleFont = new Font(baseFont, 36, Font.BOLD, BaseColor.BLACK);

        document.open();

        Paragraph casNumber = new Paragraph("登记号:", blodFont);
        casNumber.setAlignment(Element.ALIGN_RIGHT);
        casNumber.setIndentationRight(150);

        Paragraph title = new Paragraph("体检报告", titleFont);
        title.setSpacingBefore(70);
        title.setAlignment(Element.ALIGN_CENTER);


        Image image = Image.getInstance(imagePath);
        image.setAlignment(Element.ALIGN_CENTER);
        image.scaleAbsolute(150, 150);

        document.add(casNumber);
        document.add(title);
        document.add(image);

        PdfPTable table = new PdfPTable(3);

        table.setSpacingBefore(30);

        table.setTotalWidth(230);

        table.setLockedWidth(true);

        PdfPCell cellCardId = new PdfPCell(new Paragraph("索引卡号:", fontChinese));

        PdfPCell cellCardIdNum = new PdfPCell(new Paragraph("这里输入索引卡号", fontChinese));

        cellCardIdNum.setUseAscender(true);
        cellCardIdNum.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中

        cellCardId.disableBorderSide(15);
        cellCardIdNum.disableBorderSide(13);


        cellCardIdNum.setColspan(2);
        table.addCell(cellCardId);
        table.addCell(cellCardIdNum);

        document.add(table);

        document.newPage();

//        ColumnText.showTextAligned(writer.getDirectContent(),
//                Element.ALIGN_LEFT, new Phrase("77777777777777", titleFont),
//                document.left(), document.top() + 0, 0);
//
//        ColumnText.showTextAligned(writer.getDirectContent(),
//                Element.ALIGN_LEFT, new Phrase("77777777777777", titleFont),
//                document.left(), document.bottom() + 0, 0);

        PdfPTable table2 = new PdfPTable(12);

        PdfPCell proName = new PdfPCell(new Paragraph("项目名称", fontChinese));

        proName.setColspan(4);

        PdfPCell jieguo = new PdfPCell(new Paragraph("检查结果", fontChinese));

        jieguo.setColspan(4);

        PdfPCell danwei = new PdfPCell(new Paragraph("单位", fontChinese));
        danwei.setColspan(1);

        PdfPCell fanwei = new PdfPCell(new Paragraph("参考范围", fontChinese));
        fanwei.setColspan(2);

        PdfPCell tishi = new PdfPCell(new Paragraph("提示", fontChinese));
        tishi.setColspan(1);

        cellCardIdNum.setUseAscender(true);
        cellCardIdNum.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中

        for (int i = 0; i < 60 ; i++) {
            table2.addCell(proName);
            table2.addCell(jieguo);
            table2.addCell(danwei);
            table2.addCell(fanwei);
            table2.addCell(tishi);
        }

        table.setSpacingBefore(20);
        document.add(table2);

        document.close();
    }

}