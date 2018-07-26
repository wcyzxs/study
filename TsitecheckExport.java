package org.wxjs.les.modules.check.export;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.Encodes;
import org.wxjs.les.common.utils.FileUtils;
import org.wxjs.les.common.utils.IdGen;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.modules.base.utils.PathUtils;
import org.wxjs.les.modules.check.entity.Tsitecheck;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class TsitecheckExport {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private final static int tableWidth = 90;
	private final static float borderWidth = 0.5f;
	final static float[] widths = new float[]{0.25f, 0.12f, 0.16f ,0.24f ,0.23f};
	final static float[] widthsGap = new float[]{0.25f, 0.12f, 0.16f ,0.47f};
	
	private Tsitecheck tsitecheck;
	
	public TsitecheckExport(Tsitecheck tsitecheck){
		this.tsitecheck = tsitecheck;
	}
	
	public void generate(OutputStream os) throws DocumentException, IOException {
		Document document = null;
		PdfWriter writer = null;
		PdfPTable table = null;
        Phrase phrase = null;
        Paragraph paragraph = null;
    	phrase = new Phrase("");
        PdfPCell cell_pending = new PdfPCell(phrase);
        cell_pending.setBorderWidth(0);
        document=new Document(PageSize.A4);
        writer = PdfWriter.getInstance(document, os);
        document.open();
        PdfPCell cell = new PdfPCell() ;
        //添加标题
        paragraph=new Paragraph("现场踏勘情况", PdfUtil.getTitle1Font(true));
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        document.add(PdfUtil.generateTable4Padding());
        
        String[] items;
        
        
        //建设单位
        items=new String[] {"建设单位",tsitecheck.getDevelopOrg(),"联系人姓名",tsitecheck.getDevelopContact(),"联系人电话",tsitecheck.getDevelopPhone()};
        table=PdfUtil.generateTableRow(items, PdfUtil.getTextFont(true), new float[]{0.15f, 0.25f, 0.15f, 0.15f, 0.15f, 0.15f}, tableWidth, Element.ALIGN_LEFT, borderWidth,0);
        document.add(table);
        //施工单位
        items=new String[] {"施工单位",tsitecheck.getConstructionOrg(),"联系人姓名",tsitecheck.getConstructionContact(),"联系人电话",tsitecheck.getConstructionPhone()};
        table=PdfUtil.generateTableRow(items, PdfUtil.getTextFont(true), new float[]{0.15f, 0.25f, 0.15f, 0.15f, 0.15f, 0.15f}, tableWidth, Element.ALIGN_LEFT, borderWidth,0);
        document.add(table);
        
        //工程名称
        items=new String[] {"工程名称",tsitecheck.getProjectName()};
        table=PdfUtil.generateTableRow(items, PdfUtil.getTextFont(true), new float[] {0.15f,0.85f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 0);        
        document.add(table);
        
        //工程地址
        items=new String[] {"工程地址",tsitecheck.getProjectAddress()};
        table=PdfUtil.generateTableRow(items, PdfUtil.getTextFont(true), new float[] {0.15f,0.85f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 0);        
        document.add(table);
        
        //现场检查情况
        items=new String[] {"现场检查情况",tsitecheck.getSiteSituation()};
        table=PdfUtil.generateTableRow(items, PdfUtil.getTextFont(true), new float[] {0.15f,0.85f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 0);        
        document.add(table);
        
        //现场踏勘示意图
        /**
         * |/les/userfiles/1/images/test/test/link.jpg|/les/userfiles/1/images/test/test/%E6%B5%8B%E8%AF%95.png
         */

	      String str=tsitecheck.getSitePicture();
	      if(str.length()>1){
            table=checkPicture(str,phrase,cell);
            document.add(table);        	
	      }
     
        //现场踏勘情况
        
        items=new String[] {"现场踏勘情况",tsitecheck.getSiteCheckResult()};
        table=PdfUtil.generateTableRow(items, PdfUtil.getTextFont(true), new float[] {0.15f,0.85f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 0);        
        document.add(table);

       //签名
        
//       Image checkerSig=null;
//     
//       try {
//    	   String filename=this.base64StringToImage(this.tsitecheck.getCheckerSig().getSignature());
//    	   checkerSig=Image.getInstance(filename);
//    	   FileUtils.deleteFile(filename);
//       } catch (MalformedURLException e) {
//    	   logger.error("checkerSig error",e);
//       } catch (IOException e) {
//    	   logger.error("checkerSig error",e);
//       }
//     
//       Image partySig=null;
//       
//       try {
//    	   String filename=this.base64StringToImage(this.tsitecheck.getPartySig().getSignature());
//    	   partySig=Image.getInstance(filename);
//    	   FileUtils.deleteFile(filename);
//       } catch (MalformedURLException e) {
//    	   logger.error("partySig error",e);
//       } catch (IOException e) {
//    	   logger.error("partySig error",e);
//       }
//       
//       table=new PdfPTable(4);
//       table.setWidths(new float[] {0.2f,0.3f,0.2f,0.3f});
//       table.setWidthPercentage(tableWidth);
//            
//       phrase=new Phrase("勘查人（签名）",PdfUtil.getTextFont(true));
//       cell=new PdfPCell(phrase);
//       cell.setBorderWidth(borderWidth);
//       cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//       
//       table.addCell(cell);
//       table.addCell(checkerSig);
//      
//       phrase=new Phrase("当事人（签名）",PdfUtil.getTextFont(true));
//       cell=new PdfPCell(phrase);
//       cell.setBorderWidth(borderWidth);
//       cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//       
//       table.addCell(cell);
//       table.addCell(partySig);
//       document.add(table);
//  
       
       if(document!=null) {
    	   document.close();
       }
     }
	
	//图片比例
//	private void style(Image image) {
//		float heigth=image.getHeight();
//		float width=image.getWidth();
//		int percent=getPercent(heigth, width);
//		image.scalePercent(percent);
//	}
//	
//	public int getPercent(float h,float w)
//	{
//		int p=0;
//		float p2=0.0f;
//		if(h>w){
//			p2=250/h*100;
//		}
//		else{
//			p2=170/w*100;
//		}
//		p=Math.round(p2);
//		return p;
//	}
	
	

	private PdfPTable checkPicture(String str,Phrase phrase,PdfPCell cell) throws MalformedURLException, IOException, DocumentException  {
		PdfPTable table=null;
		String[] items=str.substring(1).split("\\|");
   		
		if(items.length==1){
				table=new PdfPTable(2);
				table.setWidthPercentage(tableWidth);
				phrase=new Phrase("现场探勘示意图",PdfUtil.getTextFont(true));
				cell=new PdfPCell(phrase);
				cell.setBorderWidth(borderWidth);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.setWidths(new float[] {0.15f,0.85f});
				
				String sitePath=str;
				String imagePath=PathUtils.getRealPath(sitePath);
				Image image=Image.getInstance(imagePath);
				image.scaleToFit(100, 120);
				//style(image);
				
				image.setAlignment(Image.ALIGN_CENTER);
				table.addCell(cell);
				table.addCell(image);	
		}else{
				table=new PdfPTable(3);
				table.setWidthPercentage(tableWidth);
				phrase=new Phrase("现场探勘示意图",PdfUtil.getTextFont(true));
				cell=new PdfPCell(phrase);
				cell.setBorderWidth(borderWidth);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				table.setWidths(new float[] {0.14f,0.43f, 0.43f});
				for(String sitePath : items){
					String imagePath=PathUtils.getRealPath(sitePath);
					Image image=Image.getInstance(imagePath);
				//	style(image);
					image.setAlignment(Image.ALIGN_CENTER);
					image.scaleToFit(120, 200);
					table.addCell(image);
				}
		}
		return table;
	}


	
	private PdfPCell getContentCell(String content,int align) {
		Phrase phrase=new Phrase(content,PdfUtil.getTextFont(false));
		PdfPCell cell = new PdfPCell(phrase);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(align);
		return cell;
	}
	
	
	
	/**
	 * 输出到客户端
	 * @param response
	 * @param fileName 输出文件名
	 * @return
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public TsitecheckExport write(HttpServletResponse response,String fileName) throws DocumentException, IOException {
		response.reset();
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="+Encodes.urlEncode(fileName));
		this.generate(response.getOutputStream());
		return this;
		
	}
	
	/**
	 * 输出到文件
	 * @param filename 输出文件名
	 * @throws DocumentException
	 * @throws IOException 
	 */
    public TsitecheckExport writeFile(String name) throws DocumentException, IOException {
 	   	FileOutputStream os = new FileOutputStream(name);
		this.generate(os);
		
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