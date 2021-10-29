package com.samples.report.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author cdrcool
 */
public class Demo {

    public static void main(String[] args) throws IOException, DocumentException {
        ZipOutputStream zip = new ZipOutputStream(new FileOutputStream("D:\\xxx.zip"));
        ZipEntry entry = new ZipEntry("hello.pdf");
        zip.putNextEntry(entry);

        Document document = new Document(PageSize.A4);

        PdfWriter writer = PdfWriter.getInstance(document, zip);
        writer.setCloseStream(false);

        document.open();

        BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font titleFont = new Font(baseFont, 22, Font.BOLD, BaseColor.BLACK);
        Font cellFont = new Font(baseFont, 12, Font.NORMAL, BaseColor.BLACK);

        Paragraph paragraph = new Paragraph("内部商城商品签收单", titleFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingBefore(30f);
        paragraph.setSpacingAfter(30f);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(9);
        table.setWidths(new int[]{1, 2, 2, 2, 2, 1, 1, 1, 2});
        PdfPCell cell1 = new PdfPCell(new Phrase("供货单名称", cellFont));
        cell1.setColspan(2);
        table.addCell(cell1);
        PdfPCell cell2 = new PdfPCell(new Phrase("工作流重构测试001-勿动222第二批", cellFont));
        cell2.setColspan(2);
        table.addCell(cell2);
        PdfPCell cell3 = new PdfPCell(new Phrase("供货单编号", cellFont));
        cell3.setColspan(2);
        table.addCell(cell3);
        PdfPCell cell4 = new PdfPCell(new Phrase("SUP20210721000002", cellFont));
        cell4.setColspan(4);
        table.addCell(cell4);

        PdfPCell cell5 = new PdfPCell(new Phrase("订单名称", cellFont));
        cell5.setColspan(2);
        table.addCell(cell5);
        PdfPCell cell6 = new PdfPCell(new Phrase("工作流重构测试001-勿动222", cellFont));
        cell6.setColspan(2);
        table.addCell(cell6);
        PdfPCell cell7 = new PdfPCell(new Phrase("订单编号", cellFont));
        cell7.setColspan(2);
        table.addCell(cell7);
        PdfPCell cell8 = new PdfPCell(new Phrase("ORD20210720000007", cellFont));
        cell8.setColspan(4);
        table.addCell(cell8);

        PdfPCell cell9 = new PdfPCell(new Phrase("供方内部订单号", cellFont));
        cell9.setColspan(2);
        table.addCell(cell9);
        PdfPCell cell10 = new PdfPCell(new Phrase("345356356345235245", cellFont));
        cell10.setColspan(2);
        table.addCell(cell10);
        PdfPCell cell11 = new PdfPCell(new Phrase("供方内部子单号", cellFont));
        cell11.setColspan(2);
        table.addCell(cell11);
        PdfPCell cell12 = new PdfPCell(new Phrase("345356356345235245", cellFont));
        cell12.setColspan(3);
        table.addCell(cell12);

        PdfPCell cell13 = new PdfPCell(new Phrase("下单公司", cellFont));
        cell13.setColspan(2);
        table.addCell(cell13);
        PdfPCell cell14 = new PdfPCell(new Phrase("武汉公司", cellFont));
        cell14.setColspan(2);
        table.addCell(cell14);
        PdfPCell cell15 = new PdfPCell(new Phrase("下单部门", cellFont));
        cell15.setColspan(2);
        table.addCell(cell15);
        PdfPCell cell16 = new PdfPCell(new Phrase("服务中心A-工程部", cellFont));
        cell16.setColspan(3);
        table.addCell(cell16);

        PdfPCell cell17 = new PdfPCell(new Phrase("序号", cellFont));
        table.addCell(cell17);
        PdfPCell cell18 = new PdfPCell(new Phrase("商品简图", cellFont));
        table.addCell(cell18);
        PdfPCell cell19 = new PdfPCell(new Phrase("商品名称", cellFont));
        table.addCell(cell19);
        PdfPCell cell20 = new PdfPCell(new Phrase("商品编号", cellFont));
        table.addCell(cell20);
        PdfPCell cell21 = new PdfPCell(new Phrase("品牌", cellFont));
        table.addCell(cell21);
        PdfPCell cell22 = new PdfPCell(new Phrase("单位", cellFont));
        table.addCell(cell22);
        PdfPCell cell23 = new PdfPCell(new Phrase("商品数量", cellFont));
        table.addCell(cell23);
        PdfPCell cell24 = new PdfPCell(new Phrase("验收数量", cellFont));
        table.addCell(cell24);
        PdfPCell cell25 = new PdfPCell(new Phrase("备注", cellFont));
        table.addCell(cell25);

        int[] index = {0};
        java.util.List<GoodsInfo> list = new ArrayList<>();

        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setMainImg("https://img13.360buyimg.com/n12/jfs/t1/181355/14/1093/58280/6086652bE45a89dde/7c9eec29c18a42c4.jpg?x-oss-process=image/resize,h_500,w_500,90_q,limit_1,value_0");
        goodsInfo.setMaterialName("最生活（a-life）新疆长绒棉毛巾 纯棉洗脸巾 强吸水棉柔巾 成人家用全棉加厚 3条装 白/紫/橘 34*76cm");
        goodsInfo.setSerialNumber("4365489");
        goodsInfo.setBand("最生活（a-life）");
        goodsInfo.setUnit("条");
        goodsInfo.setNum("2");
        goodsInfo.setCheckNum("2");
        goodsInfo.setRemark("");
        list.add(goodsInfo);
        list.stream()
                .peek(item -> index[0]++)
                .forEach(item -> {
                    PdfPCell cellA = new PdfPCell(new Phrase(String.valueOf(index[0]), cellFont));
                    table.addCell(cellA);

                    Image img = null;
                    try {
                        img = Image.getInstance(item.getMainImg());
                        img.scaleToFit(64, 48);
                    } catch (BadElementException | IOException e) {
                        e.printStackTrace();
                    }
                    PdfPCell cellB = new PdfPCell(img);
                    cellB.setColspan(1);
                    cellB.setUseAscender(true);
                    cellB.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellB.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellB);

                    PdfPCell cellC = new PdfPCell(new Phrase(item.getMaterialName(), cellFont));
                    table.addCell(cellC);
                    PdfPCell cellD = new PdfPCell(new Phrase(item.getSerialNumber(), cellFont));
                    table.addCell(cellD);
                    PdfPCell cellE = new PdfPCell(new Phrase(item.getBand(), cellFont));
                    table.addCell(cellE);
                    PdfPCell cellF = new PdfPCell(new Phrase(item.getUnit(), cellFont));
                    table.addCell(cellF);
                    PdfPCell cellG = new PdfPCell(new Phrase(item.getNum(), cellFont));
                    table.addCell(cellG);
                    PdfPCell cellH = new PdfPCell(new Phrase(item.getCheckNum(), cellFont));
                    table.addCell(cellH);
                    PdfPCell cellI = new PdfPCell(new Phrase(item.getRemark(), cellFont));
                    table.addCell(cellI);
                });

        PdfPCell cell26 = new PdfPCell(new Phrase("收货人", cellFont));
        cell26.setColspan(2);
        table.addCell(cell26);
        PdfPCell cell27 = new PdfPCell(new Phrase("吴亚东", cellFont));
        cell27.setColspan(2);
        table.addCell(cell27);
        PdfPCell cell28 = new PdfPCell(new Phrase("联系电话", cellFont));
        cell28.setColspan(2);
        table.addCell(cell28);
        PdfPCell cell29 = new PdfPCell(new Phrase("13147104605", cellFont));
        cell29.setColspan(3);
        table.addCell(cell29);

        PdfPCell cell30 = new PdfPCell(new Phrase("收货地址", cellFont));
        cell30.setColspan(2);
        table.addCell(cell30);
        PdfPCell cell31 = new PdfPCell(new Phrase("湖北省武汉市洪山区花山街道软件新城二期B3", cellFont));
        cell31.setColspan(7);
        table.addCell(cell31);

        PdfPCell cell34 = new PdfPCell(new Phrase("收货人（签名）：", cellFont));
        cell34.setColspan(2);
        table.addCell(cell34);
        PdfPCell cell35 = new PdfPCell(new Phrase("", cellFont));
        cell35.setColspan(2);
        table.addCell(cell35);
        PdfPCell cell36 = new PdfPCell(new Phrase("签收日期：", cellFont));
        cell36.setColspan(2);
        table.addCell(cell36);
        PdfPCell cell37 = new PdfPCell(new Phrase("", cellFont));
        cell37.setColspan(3);
        table.addCell(cell37);

        document.add(table);


        document.close();


        zip.closeEntry();

        zip.close();
    }
}
