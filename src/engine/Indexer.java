package engine;

import java.io.File;
import java.io.IOException;

import org.json.JSONObject;

import tool.CmdTool;
import config.EngineConfig;

/**
 * 引擎索引类，负责根据json文件建立索引
 * @author hello
 *
 */
public class Indexer 
{
	/**
	 * 索引文件
	 * @param inJsonFile 输入文件，json格式
	 * @throws IOException
	 */
	private void indexFile(String inJsonFile) throws IOException
	{
		String command= "java" + " -Dc="+EngineConfig.getCore()+" -Dtype=application/json" +
						" -Dhost="+EngineConfig.getHost()+" -Dport="+EngineConfig.getPort()+" "+"-jar "+EngineConfig.getPostJar() + " "+inJsonFile;
		
		CmdTool.executeCmd(command);
	}
	
	/**
	 * 索引单个文件或整个目录
	 * @param inFileOrDir 文件或目录
	 */
	public void index(File inFileOrDir)
	{
		if (inFileOrDir.isFile())
		{
			try 
			{
				indexFile(inFileOrDir.getAbsolutePath());
			} catch (Exception e) {
				System.out.println("file: "+inFileOrDir.getAbsolutePath());
				e.printStackTrace();
			}
		}
		else
		{
			File[] subs= inFileOrDir.listFiles();
			for (File sub : subs)
				index(sub);
		}
	}
	
	/**替换*/
	public static final String ACTION_SET="set";
	/**追加*/
	public static final String ACTION_ADD="add";
	/**移除*/
	public static final String ACTION_REMOVE="remove";
	/**正则匹配式移除*/
	public static final String ACTION_REMOVEREGEX="removeregex";
	/**增加计数*/
	public static final String ACTION_INC="inc";
	/**
	 * 局部更新索引
	 * @param url	待更新文档标识
	 * @param field 需更新的域
	 * @param value 更新的值
	 * @param action 更新方式
	 */
	public void update(String url, String field, String value, String action)
	{
		try 
		{
			JSONObject actionObj = new JSONObject();
			actionObj.put(action, value);
			
			JSONObject json = new JSONObject();
			json.put("url", url);
			json.put(field, actionObj);
			
			//String command= "java" + " -Dc="+EngineConfig.getCore()+" -Dtype=application/json" +
			//		" -Dhost="+EngineConfig.getHost()+" -Dport="+EngineConfig.getPort()+" -Ddata=args"+" -jar "+EngineConfig.getPostJar() +" '["+json.toString()+"]'";
			String command = "curl -X POST -H Content-Type:application/json "+EngineConfig.getSolrHomeUrl()+"update"+" --data-binary '["+json.toString()+"]'";
			System.out.println("command:\n"+command);
			String output= CmdTool.executeCmd(command);
			System.out.println("output:\n"+output);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws IOException
	{
		//String jsonDir= EngineConfig.getDatabaseJson();
		//new Indexer().index(new File(jsonDir));
		
		new Indexer().update("test_ch.pdf", "anchors", "haha", ACTION_ADD);
	}
}
