package docParser;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import config.EngineConfig;

/**
 * pdf文件解析类，负责解析pdf文件
 * @author hello
 *
 */
public class PdfParser extends ParserBase
{
	PDDocument pdf;
	String filename;
	
	PdfParser(File pdfFile) throws IOException
	{
		pdf = PDDocument.load(pdfFile);
		filename = pdfFile.getName();
	}
		
	@Override
	String getTitle() 
	{
		return filename;
		//return pdf.getDocumentInformation().getTitle();
	}

	@Override
	String getContent() 
	{
		try 
		{
			return new PDFTextStripper().getText(pdf);
		} catch (IOException e) 
		{
			e.printStackTrace();
			return "";
		}
	}
	
	public static void main(String args[]) throws IOException
	{
		File file= new File(EngineConfig.getDatabaseRaw()+"/"+"test_ch.pdf");
		PdfParser pdfp= new PdfParser(file);
		
		System.out.println("title: "+ pdfp.getTitle());
		System.out.println("content: "+ pdfp.getContent());
	}
}
