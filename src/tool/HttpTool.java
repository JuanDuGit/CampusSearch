package tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http工具类
 * @author hello
 *
 */
public class HttpTool 
{
	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	
	/**
	 * 发送Http请求并获取回复
	 * @param url 请求网址
	 * @param method 请求方式(METHOD_GET/METHOD_POST)
	 * @return 响应内容
	 * @throws IOException
	 */
	public static String getResponse(String url, String method) throws IOException 
	{
		HttpURLConnection connect = (HttpURLConnection) new URL(url).openConnection();
		connect.setRequestMethod(method);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream(), "utf8"));
		
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);
	
		in.close();
 
		return response.toString().trim();
	}
}
