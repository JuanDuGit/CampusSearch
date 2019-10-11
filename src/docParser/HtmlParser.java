package docParser;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import config.EngineConfig;

/**
 * 网页文件解析类，负责解析各种网页文件: html、jsp、php...
 * @author hello
 *
 */
public class HtmlParser extends ParserBase
{
	Document html;
	
	
	public HtmlParser(File htmlFile) throws IOException
	{
		try
		{
			html= Jsoup.parse(htmlFile, getCharset(htmlFile));	
		}
		catch (java.nio.charset.IllegalCharsetNameException e)
		{
			System.out.println("file:"+htmlFile.getAbsolutePath());
			e.printStackTrace();
		}
	}
	
	static String getCharset (File htmlFile) throws IOException 
	{
		Document doc= Jsoup.parse(htmlFile, EngineConfig.getDefaultCharset());
		
		Elements eles = doc.select("meta[http-equiv=Content-Type]");
		
		if (eles.size() > 0)
		{
			String charsetMeta= eles.get(0).attr("content").toLowerCase();
			int index= charsetMeta.lastIndexOf("charset=") + new String("charset=").length();
			String charset= charsetMeta.substring(index);
			
			return charset.trim();
		}
		
		return EngineConfig.getDefaultCharset();
	}
	
	@Override
	public String getTitle()
	{
		return html.title();
	}
	
	@Override
	public String getContent()
	{
		//return html.text();
		return html.body().text(); 
	}
	
	public Elements getHref()
	{
		return html.getElementsByTag("a");
	}
	
	public static void main(String args[]) throws IOException
	{
		File file= new File("C:/Users/hello/Desktop/CampusSearch/data/raw/news.tsinghua.edu.cn/publish/news/newscenter/xczl.htm");
		//File file= new File(Config.DATABASE_RAW+"/"+"gbk.htm");
		HtmlParser hp= new HtmlParser(file);
		
		System.out.println("title:"+hp.getTitle());
		System.out.println("content:"+hp.getContent());
	}
}
