package engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

import org.json.JSONArray;
import org.json.JSONObject;

import config.EngineConfig;
import docParser.ParserBase;

/**
 * 鏂囦欢瑙ｆ瀽涓荤被锛岃礋璐ｅ皢鍘熷鏁版嵁鏂囦欢瑙ｆ瀽涓烘牸寮忓寲鐨刯son鏂囦欢
 * @author hello
 *
 */
public class ParseEngine 
{
	/**json鏂囦欢鏍圭洰褰�*/
	private String baseDirJson;
	
	/**鏍规嵁缃戦〉url瀵绘壘瀵瑰簲json鏂囦欢*/
	private File findJsonFile(String url)
	{
		int index = url.indexOf("://");
		if (index >= 0)
			url = url.substring(index + new String("://").length());
		if (url.endsWith("/"))
			url = url.substring(0, url.length()-1);
		
		String path = baseDirJson + "/" + url+".json";
		
		return new File(path);
	}
	
	/**鏍规嵁PageRank鍒嗘暟纭畾鏂囨。boost鍊�*/
	private float getBoost(float pageRankScore, int totalPages)
	{
		return pageRankScore*totalPages*10;
	}
	
	private int countLines(String infile) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader(infile));
		int count = 0;
		while (in.readLine()!=null)
			count ++;
		in.close();
		return count;
	}
	
	/**
	 * 
	 * @param baseDirJson  json鏂囦欢鏍圭洰褰�	 */
	public ParseEngine(String baseDirJson) 
	{
		super();
		this.baseDirJson = baseDirJson;
	}
	
	/**
	 * 鏍规嵁PageRank缁撴灉璁剧疆鍚勬枃妗oost鍊�	 * 娉ㄦ剰锛氬彧淇敼宸插瓨鍦ㄦ枃妗ｏ紝鏈煡url涓嶄簣澶勭悊
	 * @param inPageRankFile  PageRank缁撴灉鏂囦欢
	 * @throws IOException
	 */
	public void addPageRankResult(String inPageRankFile) throws IOException
	{
		int totalPages = countLines(inPageRankFile);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inPageRankFile)));
		String line;
		
		while ((line=in.readLine())!=null)
		{
			try {
				String parts[] = line.split("	");
				String url = parts[0];
				float score = Float.valueOf(parts[1]);
				float boost = getBoost(score, totalPages);
				
				File jsonFile = findJsonFile(url);
				if (jsonFile.exists())
				{
					String jsonStr = new String(Files.readAllBytes(jsonFile.toPath()));
					JSONObject jsonObj = new JSONObject(jsonStr);
					JSONObject addObj = jsonObj.getJSONObject("add");
					
					addObj.put("boost", boost);
					jsonObj.put("add", addObj);
					Files.write(jsonFile.toPath(), jsonObj.toString().getBytes());
				}
			}
			catch (Exception e)
			{
				System.out.println("dealing with: "+line);
				e.printStackTrace();
			}
		}
		
		in.close();
	}
	
	/**
	 * 灏嗛敋鏂囨湰娣诲姞鍒版枃妗�	 * 娉ㄦ剰锛氬彧淇敼宸插瓨鍦ㄦ枃妗ｏ紝鏈煡url涓嶄簣澶勭悊
	 * @param inAnchorsFile 閿氭枃鏈垎鏋愮粨鏋滄枃浠�	 * @throws IOException
	 */
	public void addAnchorTexts(String inAnchorsFile) throws IOException
	{
		//BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inAnchorsFile), "gb2312"));
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inAnchorsFile)));
		String line;
		while ((line=in.readLine())!=null)
		{
			try {
				int ptcIndex = line.indexOf("://");
				if (ptcIndex >= 0)
					line = line.substring(ptcIndex+new String("://").length());
				
				int sepIndex = line.indexOf(":");
				String url = line.substring(0, sepIndex);
				String[] texts = line.substring(sepIndex+1).split("-->");
				
				JSONArray anchorTexts = new JSONArray();
				for (String text : texts)
					anchorTexts.put(text);
				
				File jsonFile = findJsonFile(url);
				
				if (jsonFile.exists())
				{
					String jsonStr = new String(Files.readAllBytes(jsonFile.toPath()));
					JSONObject jsonObj = new JSONObject(jsonStr);
					JSONObject addObj = jsonObj.getJSONObject("add");
					JSONObject docObj = addObj.getJSONObject("doc");
					
					docObj.put("anchors", anchorTexts);
					addObj.put("doc", docObj);
					jsonObj.put("add", addObj);
					Files.write(jsonFile.toPath(), jsonObj.toString().getBytes());
				}
			}
			catch (Exception e)
			{
				System.out.println("dealing with: "+line);
				e.printStackTrace();
			}
		}
		
		in.close();
	}
	
	/**
	 * 瑙ｆ瀽鍘熷鏂囦欢鏂囨湰鍐呭锛堜笉鍖呮嫭閾炬帴缁撴瀯鍒嗘瀽锛夊苟鐢熸垚json鏂囦欢
	 * @param inRawDir  鍘熷鏂囦欢鐩綍
	 * @param outJsonDir 瀵瑰簲json鏂囦欢鐩綍
	 * @param uri  鍦ㄥ師濮嬫枃浠剁洰褰曞墠棰濆娣诲姞鐨剈rl鍓嶇紑
	 */
	public void addParseResult(String inRawDir,String outJsonDir, String uri)
	{
		File raw= new File(inRawDir);
		if (raw.isFile())
		{
			try 
			{
				ParserBase parser= ParserBase.createParser(raw);
				JSONObject docObj= parser.parseToJson();
				docObj.put("url", uri);
				
				JSONObject jsonObj = new JSONObject();
				JSONObject addObj = new JSONObject(); 
				addObj.put("doc", docObj);
				jsonObj.put("add", addObj);
				
				FileWriter fw= new FileWriter(outJsonDir+".json");
				BufferedWriter out= new BufferedWriter(fw);
				out.write(jsonObj.toString());
				out.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			new File(outJsonDir).mkdir();
			
			File[] subraws= raw.listFiles();
			for (File subraw : subraws)
				addParseResult(inRawDir+"/"+subraw.getName(), outJsonDir+"/"+subraw.getName(), uri.length()>0 ? uri+"/"+subraw.getName() : subraw.getName());
		}
	}
	
	public static void main(String args[]) throws IOException
	{
		ParseEngine parseEngine= new ParseEngine(EngineConfig.getDatabaseJson());
		//parseEngine.addParseResult(EngineConfig.getDatabaseRaw(), EngineConfig.getDatabaseJson(), "");
		//parseEngine.addAnchorTexts(EngineConfig.getLinkAnchorFile());
		parseEngine.addPageRankResult(EngineConfig.getPagerankResultFile());
	}
}
