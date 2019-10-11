package engine;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import tool.HttpTool;
import config.EngineConfig;

/**
 * 引擎搜索类
 * @author hello
 *
 */
public class Searcher 
{
	/**
	 * 搜索
	 * @param query 查询字符串
	 * @param start 结果的起始编号
	 * @param rows  结果数量
	 * @return  solr服务器响应结果
	 */
	public String search(String query, int start, int rows)
	{
		try 
		{
			query= URLEncoder.encode(query, "utf-8");
			//String url= Config.getSolrHomeUrl()+"select?defType=dismax&qf=title^2.0+content^0.3+url^2.0&wt=json&q="+query; //
			
			Map<String, String> queryObj= new HashMap<String, String>();
		
			queryObj.put("defType", "dismax");
			queryObj.put("qf", "title^3.0+anchors^3.0+content^1.0+url^3.0+_text_^1.0");
			
			queryObj.put("start", ""+start);
			queryObj.put("rows", ""+rows);
			queryObj.put("q", query);
			
			queryObj.put("hl", "true");
			queryObj.put("hl.fl", "title,content");
			
			queryObj.put("mlt", "true");
			queryObj.put("mlt.fl", "title,content");
			
			String url= EngineConfig.getSolrHomeUrl()+"select?wt=json";
			Set<String> keys= queryObj.keySet();
			for (String key : keys)
				url += "&"+ key+"="+queryObj.get(key);
			
			String response= HttpTool.getResponse(url, HttpTool.METHOD_GET);
			return response;
		} catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 检索建议，用于搜索框输入时的提示补全
	 * @param query 输入字符串
	 * @return solr响应结果
	 */
	public String suggest(String query)
	{
		try 
		{
			query= URLEncoder.encode(query, "utf-8");
			String url = EngineConfig.getSolrHomeUrl()+"suggest?suggest=true&suggest.build=true&suggest.dictionary=mySuggester&wt=json&suggest.q="+query;
			String response= HttpTool.getResponse(url, HttpTool.METHOD_GET);
			return response;
		} catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args)
	{
		String query= "清华大学";
		//new Searcher().search(query, 2400, 10);
		new Searcher().suggest(query);
	}
}
