package site.search;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import config.SiteConfig;
import engine.Searcher;
import site.util.MetaInfo;
import site.util.Record;

/**
 * 前台搜索类
 * @author hello
 *
 */
public class SiteSearcher 
{

	private String getSnapshotSrc(String url)
	{
		int ptc = url.indexOf("://");
		if (ptc >= 0)
			url = url.substring(ptc+new String("://").length());
		
		String src = "/data/snapshot/"+url+".png";
		return src;
	}
	/**
	 * 搜索
	 * @param query  查询字符串
	 * @param page   页号
	 * @param getResults 搜索结果
	 * @param getInfo  附加信息（总条数、总页数、是否出错...）
	 */
	public void search(String query, int page, List<Record> getResults, MetaInfo getInfo)
	{
		if (query == null)
		{
			System.out.println("warning in SiteSearcher.search: null query");
			return ;
		}
		
		int rows = SiteConfig.getRecordsPerPage();
		int start = (page-1)*rows;
		
		try 
		{
			String jsonResponse = new Searcher().search(query, start, rows);
			JSONObject responseObj = new JSONObject(jsonResponse); 
			JSONObject resultObj = responseObj.getJSONObject("response");
			JSONObject highlightObj = responseObj.getJSONObject("highlighting");
			
			int totalFound = resultObj.getInt("numFound");
			JSONArray docs = resultObj.getJSONArray("docs");
			
			int totalPages = totalFound/rows + (totalFound%rows==0 ? 0 : 1);
			getInfo.setTotalFound(totalFound);
			getInfo.setTotalPages(totalPages);
			
			getResults.clear();
			int num = docs.length();
			
			for (int i=0; i<num; ++i)
			{
				JSONObject doc = docs.getJSONObject(i);
				try 
				{
					String url = doc.getString("url"); 
		
					String title= null, content= null;
					try
					{
						JSONObject hlDoc = highlightObj.getJSONObject(url);
						
						if (hlDoc.has("title"))
						{
							JSONArray titleHlArr = (JSONArray) hlDoc.get("title");  
							title = "";
							for (int k=0; k<titleHlArr.length(); ++k)
								title += titleHlArr.getString(k)+" ";
						}
						
						if (hlDoc.has("content"))
						{
							JSONArray contentHlArr = (JSONArray) hlDoc.get("content");
							content = "";
							for (int k=0; k<contentHlArr.length(); ++k)
								content += contentHlArr.getString(k)+" ";
						}
					}
					catch (JSONException e)
					{
						e.printStackTrace();
					}
					
					if (title==null && doc.has("title"))
						title = doc.getString("title");
					if (content==null && doc.has("content"))
					{
						content = doc.getString("content");
						if (content.length()>SiteConfig.getDigestLength())
							content = content.substring(0, SiteConfig.getDigestLength());
					}
					
					if (url.indexOf("://") < 0)
						url = "http://"+url;
					getResults.add(new Record(url, title, content, getSnapshotSrc(url))); 
					
				}catch(JSONException e)
				{
					e.printStackTrace();
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			getInfo.setTotalFound(-1);
			getResults.clear();
		}
	}

	public void suggest(String query, List<String> getSuggestions)
	{
		String response = new Searcher().suggest(query);
		try 
		{
			JSONArray suggestions = new JSONObject(response).getJSONObject("suggest").getJSONObject("mySuggester").getJSONObject(query).getJSONArray("suggestions");
			
			int len = suggestions.length();
			for (int i=0; i<len; ++i)
				getSuggestions.add(suggestions.getJSONObject(i).getString("term"));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
