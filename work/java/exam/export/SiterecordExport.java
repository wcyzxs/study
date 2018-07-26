package org.wxjs.les.modules.exam.export;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.Encodes;
import org.wxjs.les.common.utils.FileUtils;
import org.wxjs.les.common.utils.IdGen;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.modules.base.utils.PathUtils;
import org.wxjs.les.modules.check.entity.Tsitecheck;
import org.wxjs.les.modules.exam.entity.Siterecord;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SiterecordExport {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    // 表格宽、高
    private final static int tableWidth = 90;
    private final static float borderWidth = 0.5f;

    // 默认实体类对象
    private Siterecord siterecord;
    private static BaseFont bfChinese = null;
    private static Map<String, Font> fontMap = new HashMap<String, Font>();

    /**
     * 静态资源初始化
     */
    static {
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            // 设置字体样式
            fontMap.put("textFont", new Font(bfChinese, 12, Font.NORMAL)); // 正常
            fontMap.put("subjectFont", new Font(bfChinese, 22, Font.BOLD)); // 加粗
            fontMap.put("firstTitleFont", new Font(bfChinese, 18, Font.BOLD)); // 一级标题
            fontMap.put("secondTitleFont", new Font(bfChinese, 15, Font.BOLD)); // 二级标题
            fontMap.put("underlineFont", new Font(bfChinese, 11, Font.UNDERLINE)); // 下划线斜体
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建表格给标题
     * msg     表格内容
     * colSpan 合并列
     * rolSpan 合并行
     * borderWidth 内容宽度
     * borderHeight 内容高度
     */
    private PdfPCell createPDFCellToTitle(String msg, Font font, int rowSpan, int colSpan, float borderWidth, float borderHeight) {
        // 创建单元格对象，将内容及字体传入
        PdfPCell cell = new PdfPCell(new Paragraph(msg, font));
        // 设置单元格内容样式
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);   // 设置水平样式
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);     //设置垂直样式
        // 内容宽度、高度 
        cell.setBorderWidth(borderWidth);
        cell.setMinimumHeight(borderHeight);
        // 合并行
        if (rowSpan > 0) {
            cell.setRowspan(rowSpan);
        }
        // 合并列
        if (colSpan > 0) {
            cell.setColspan(colSpan);
        }
        return cell;
    }

    /**
     * 创建表格给内容
     */
    private PdfPCell createPDFCellToContent(String msg, Font font, int rowSpan, int colSpan, float borderWidth, float borderHeight) {
        // 创建单元格对象，将内容及字体传入
        PdfPCell cell = new PdfPCell(new Paragraph(msg, font));
        // 内容宽度、高度
        cell.setBorderWidth(borderWidth);
        cell.setMinimumHeight(borderHeight);
        // 合并行
        if (rowSpan > 0) {
            cell.setRowspan(rowSpan);
        }
        // 合并列
        if (colSpan > 0) {
            cell.setColspan(colSpan);
        }
        return cell;
    }

    /**
     * 根据字符串解析图片
     * @param images 图片字符串(多张)
     * @param cutWidth 剪切后的图片宽度
     * @param cutHeight 剪切后的图片高度
     * @return
     */
    private PdfPCell createPDFCellToImage(String[] images, int cutWidth, 
    		int cutHeight, int rowSpan, int colSpan, float borderWidth, float borderHeight) {
        try {
            PdfPCell cell = new PdfPCell();
            cell.setBorderWidth(borderWidth);
            cell.setMinimumHeight(borderHeight);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            if(!StringUtils.isEmpty(images)) {
                // 合并行
                if (rowSpan > 0) {
                    cell.setRowspan(rowSpan);
                }
                // 合并列
                if (colSpan > 0) {
                    cell.setColspan(colSpan);
                }
                Paragraph paragraph = new Paragraph();
                for(String imageStr : images) {
                	//数组的第一个元素是空，后面两个是路径，因为当不为空的时候，执行下面的内容
                	if(!"".equals(imageStr)) {
                    	String imagePath = PathUtils.getRealPath(imageStr);
                        Image image = Image.getInstance(imagePath);
                        image.scaleToFit(cutWidth, cutHeight);
                        image.setAlignment(Element.ALIGN_LEFT);
                        paragraph.add(new Chunk(image, 0, 0, true));     //使文字与图片处于同一行
                	}
                }
                cell.addElement(paragraph);
            }
            return cell;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("图片转换异常 {}", e.getMessage());
        }
        return null;
    }

    public SiterecordExport(Siterecord siterecord) {
        this.siterecord = siterecord;
    }

    /**
     * 生成PDF对象数据
     *
     * @param os OutputStream对象
     */
    public void generate(OutputStream os) {
        // 正常的表格内容高度(非PDF高度)
        final int normalBorderHeight = 32;
        // 表格内内容的高度(表中的大表格，非PDF高度)
        final int contentTableHeight = 80;
        // 超大号表内内容的高度
        final int contentBigTableHeight = 130;
        // 缺省的图片裁剪高宽
        final int defaultImageWidth = 150;
        final int defaultImageHeight = 400;

        // 生成上下文对象
        Document document = new Document(PageSize.A4);
        try {
        	 PdfWriter writer = PdfWriter.getInstance(document, os);
            document.open();
            // 创建table对象(五列)
            float[] widths = new float[]{8, 12, 43, 12, 25};
            PdfPTable table = new PdfPTable(widths.length);
            table.setWidths(widths);
            table.setWidthPercentage(tableWidth);
         
            // 添加标题
            Paragraph paragraph = new Paragraph("现场巡查情况", fontMap.get("subjectFont"));
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph);
            document.add(PdfUtil.generateTable4Padding());
            
            /* ============ 表格中的第一部分数据 ============ */
            table.addCell(createPDFCellToTitle("巡查人", fontMap.get("textFont"), 0,2, borderWidth, normalBorderHeight));
            table.addCell(createPDFCellToContent(siterecord.getChecker(), fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));

            table.addCell(createPDFCellToTitle("巡查时间", fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));
            Date date=siterecord.getCheckDate();
            SimpleDateFormat sdf = new SimpleDateFormat( " yyyy年MM月dd日 " );
            String str = sdf.format(date);
            table.addCell(createPDFCellToContent(str, fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));
            
            /* ============ 表格中的第二部分数据 ============ */
            table.addCell(createPDFCellToTitle("当事人情况", fontMap.get("textFont"), 4, 0, borderWidth, normalBorderHeight));

            table.addCell(createPDFCellToTitle("建设单位", fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));
            table.addCell(createPDFCellToContent(siterecord.getDevelopOrg(), fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));

            table.addCell(createPDFCellToTitle("联系人姓名、电话", fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));
            table.addCell(createPDFCellToContent(siterecord.getDevelopContact() + "  " + siterecord.getDevelopPhone(), fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));

			table.addCell(createPDFCellToTitle("施工单位", fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));
            table.addCell(createPDFCellToContent(siterecord.getConstructionOrg(), fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));

			table.addCell(createPDFCellToTitle("联系人姓名、电话", fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));
			table.addCell(createPDFCellToContent(siterecord.getConstructionContact() + "  " + siterecord.getConstructionPhone(), fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));

			table.addCell(createPDFCellToTitle("工程名称", fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));
			table.addCell(createPDFCellToContent(siterecord.getProjectName(), fontMap.get("textFont"), 0, 3, borderWidth, normalBorderHeight));

			table.addCell(createPDFCellToTitle("工程地址", fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));
			table.addCell(createPDFCellToContent(siterecord.getProjectAddress(), fontMap.get("textFont"), 0, 3, borderWidth, normalBorderHeight));

            /* ============ 表格中的第二部分数据 ============ */
            table.addCell(createPDFCellToTitle("现场工程检查情况", fontMap.get("textFont"), 0, 0, borderWidth, contentTableHeight));
            table.addCell(createPDFCellToContent(siterecord.getSiteSituation(), fontMap.get("textFont"), 0, 4, borderWidth, contentTableHeight));


            /* ============ 表格中的第四部分数据 ============ */
            table.addCell(createPDFCellToTitle("现场踏勘情况", fontMap.get("textFont"), 0, 0, borderWidth, contentTableHeight));
            table.addCell(createPDFCellToContent(siterecord.getSiteCheckResult(), fontMap.get("textFont"), 0, 4, borderWidth, contentTableHeight));
         
            /* ============ 表格中的第五部分数据 ============ */
            table.addCell(createPDFCellToTitle("现场踏勘示意图", fontMap.get("textFont"), 0, 0, borderWidth, contentBigTableHeight));
            table.addCell(createPDFCellToImage(siterecord.getSitePicture().split("\\|"), defaultImageWidth, defaultImageHeight, 0, 4, borderWidth, contentBigTableHeight));
            
            document.add(table);

        
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("生成PDF数据异常{}", e.getMessage());
        } finally {
            document.close();
        }
    }

	

	/**
     * 输出到文件
     *
     * @throws DocumentException
     * @throws IOException
     */
    public SiterecordExport writeFile(String name) throws DocumentException, IOException {
        FileOutputStream os = new FileOutputStream(name);
        this.generate(os);
        return this;
    }

    /**
     * 输出到客户端
     * @param response
     * @param fileName 输出文件名
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public SiterecordExport write(HttpServletResponse response, String fileName) throws DocumentException, IOException {
        response.reset();
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + Encodes.urlEncode(fileName));
        this.generate(response.getOutputStream());
        return this;
    }
    
    public String base64StringToImage(String base64String) {
    	String filename=Global.getConfig("userfiles.basedir")+"/"+IdGen.uuid()+".png";
    	try {
    		byte[] bytes1=Base64.decodeBase64(base64String);
        	ByteArrayInputStream bais=new ByteArrayInputStream(bytes1);
			BufferedImage bi=ImageIO.read(bais);
			File f1=new File(filename);
			ImageIO.write(bi, "png", f1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filename;
    	
    }
    
}