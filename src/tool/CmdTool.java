package tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 命令行工具类
 * @author hello
 *
 */
public class CmdTool 
{
	/**
	 * 调用命令行执行DOS指令
	 * @param command DOS指令
	 * @return 标准输出和错误流输出
	 */
	public static String executeCmd(String command)
	{
		String output= "";
		String errOut= "";
		try
		{
			Runtime rt= Runtime.getRuntime();
			Process cmdProcess= rt.exec(command);
			
			BufferedReader in= new BufferedReader(new InputStreamReader(cmdProcess.getInputStream()));
			
			String line;
			while ((line= in.readLine())!=null)
				output += line + "\r\n";
			
			BufferedReader errin= new BufferedReader(new InputStreamReader(cmdProcess.getErrorStream()));
			while ((line= errin.readLine())!=null)
				errOut += line + "\r\n";
			
			cmdProcess.destroy();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
		
		return "stdout:\r\n"+output+"\r\nerrOut:\r\n"+errOut;
	}
}
