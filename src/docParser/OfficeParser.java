package docParser;

import java.io.File;
import java.io.IOException;

import org.apache.poi.extractor.ExtractorFactory;

import config.EngineConfig;

/**
 * office文件解析类，负责解析各种office文件: doc/docx、xls/xlsx、ppt/pptx...
 * @author hello
 *
 */
public class OfficeParser extends ParserBase
{
	File officeFile;
	
	OfficeParser(File officeFile)
	{
		this.officeFile = officeFile;
	}
	
	@Override
	String getTitle() 
	{
		return officeFile.getName();
	}

	@Override
	String getContent() 
	{
		try 
		{
			return ExtractorFactory.createExtractor(officeFile).getText();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}  
        
		return "";
	}

	public static void main(String args[]) throws IOException
	{
		//File file= new File(Config.DATABASE_RAW+"/"+"关于2015年6月16日-17日调课通知.docx");
		File file= new File(EngineConfig.getDatabaseRaw()+"/"+"8171431070639552.xlsx");
		//File file= new File(Config.DATABASE_RAW+"/"+"46981428475739574.ppt");
		OfficeParser office= new OfficeParser(file);
		
		System.out.println("title:"+office.getTitle());
		System.out.println("content:"+office.getContent());
	}
}
