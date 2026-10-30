package com.pratikshat.exicom.event;

import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.pratikshat.exicom.entity.QuotePdfHeaderAndFooter;

public class QuoteHeaderFooterPageEvent extends PdfPageEventHelper {
	Font ffont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL,BaseColor.DARK_GRAY);
	
	QuotePdfHeaderAndFooter headerAndFooter = null;
	
	public QuoteHeaderFooterPageEvent(QuotePdfHeaderAndFooter headerAndFooter) {
		this.headerAndFooter = headerAndFooter;
	}
	
    public void onStartPage(PdfWriter writer,Document document) {
    	
    }
    public void onEndPage(PdfWriter writer,Document document) {
    	
    	PdfContentByte cb = writer.getDirectContent();
    	ArrayList<Phrase> footerList = new ArrayList<>();
    	
    	if(headerAndFooter.getFooterline5()!=null) {
    		Phrase footerLine5 = new Phrase(headerAndFooter.getFooterline5(), ffont);
    		footerList.add(footerLine5);
        }

    	if(headerAndFooter.getFooterline4()!=null) {
    		Phrase footerLine4 = new Phrase(headerAndFooter.getFooterline4(), ffont);
    		footerList.add(footerLine4);
        }
    	
    	if(headerAndFooter.getFooterline3()!=null) {
    		Phrase footerLine3 = new Phrase(headerAndFooter.getFooterline3(), ffont);
    		footerList.add(footerLine3);
        }
    	
    	if(headerAndFooter.getFooterline2()!=null) {
    		Phrase footerLine2 = new Phrase(headerAndFooter.getFooterline2(), ffont);
    		footerList.add(footerLine2);
    	}
    	
    	if(headerAndFooter.getFooterline1()!=null) {
    		Phrase footerLine1 = new Phrase(headerAndFooter.getFooterline1(), ffont);
    		footerList.add(footerLine1);
    	}
        
    	
        for(int i=0; i<footerList.size(); i++) {
        	ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
        			footerList.get(i),
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() + (5+10*i), 0);
        }
        
    }

}
