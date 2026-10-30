package com.pratikshat.exicom.event;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class ExpenseHeaderFooterPageEvent extends PdfPageEventHelper {
	Font ffont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL,BaseColor.DARK_GRAY);
	
	public ExpenseHeaderFooterPageEvent() {
	}
	
    public void onStartPage(PdfWriter writer,Document document) {
    	try {
    	
    		Rectangle rect = new Rectangle(577, 825, 18, 15);
    		rect.setBorderColor(BaseColor.BLACK);
    		rect.setBorder(Rectangle.BOX);
    		rect.setBorderWidth(1);
    		document.add(rect);
    	}catch(Exception ex) {
    		new ExceptionConverter(ex);
    	}
    	
		
    }
    public void onEndPage(PdfWriter writer,Document document) {
        
    }

}
