package engine;

import tool.CmdTool;
import config.EngineConfig;

public class Server 
{
	private int port;
	
	public Server()
	{
		this.port = 8983;
	}
	
	public Server(int port)
	{
		this.port = port;
	}
	
	public void start()
	{
		String command = EngineConfig.getEngineServer()+" start -p "+port;
		CmdTool.executeCmd(command);
	}
	
	public void stop()
	{
		String command = EngineConfig.getEngineServer()+" stop -p "+port;
		CmdTool.executeCmd(command);
	}
}
