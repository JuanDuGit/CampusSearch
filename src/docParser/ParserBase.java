package docParser;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import config.EngineConfig;

/**
 * 各文件解析类基类
 * @author hello
 *
 */
public abstract class ParserBase 
{
	abstract String getTitle();
	
	abstract String getContent();
	
	/**
	 * 根据文件类型返回相应文件解析类
	 * @param infile 原始数据输入文件
	 * @return 对应文件解析类
	 * @throws IOException
	 */
	public static ParserBase createParser(File infile) throws IOException
	{
		String filename= infile.getName();
		int index= filename.lastIndexOf(".");
		if (index >= 0)
		{
			String type= filename.substring(index).toLowerCase();
			switch (type)
			{
			case ".doc":
			case ".docx":
			case ".xls":
			case ".xlsx":
			case ".ppt":
			case ".pptx":
				return new OfficeParser(infile);
				
			case ".pdf":
				return new PdfParser(infile);
				
			case ".htm":
			case ".html":
			case ".shtml":
			case ".shtm":
			case ".asp":
			case ".jsp":
			case ".php":
			case ".cgi":
				return new HtmlParser(infile);
			}
		}
		
		return new HtmlParser(infile);
	}

	/**
	 * 解析原始文件返回标准json对象
	 * @return json对象
	 */
	public JSONObject parseToJson()
	{
		JSONObject obj= new JSONObject();
		
		try 
		{
			obj.put("title", getTitle());
			obj.put("content", getContent());
		} catch (JSONException e) 
		{
			e.printStackTrace();
		}
		
		return obj;
	}
	
	public static void main(String args[]) throws IOException
	{
		File file= new File(EngineConfig.getDatabaseRaw()+"/news.tsinghua.edu.cn/publish/news/4198/index.html");
	
		String json= ParserBase.createParser(file).parseToJson().toString();
		
		System.out.println("json:");
		System.out.println(json);
	}
}
