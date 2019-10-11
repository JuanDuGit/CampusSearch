package pagerank;

import config.EngineConfig;
import tool.CmdTool;
public class PageRank {
	public void run(String inNodeFile, String inGraphFile, String outLinkScoreIndOutdFile, String outLinkScoreFile, 
			 		String tempIndFile, String tempOutdFile)
	{
		String command = EngineConfig.getPagerankTool()+" "+inNodeFile+" "+inGraphFile+" "+outLinkScoreFile
						 +" "+outLinkScoreIndOutdFile+" "+tempIndFile+" "+tempOutdFile;
		CmdTool.executeCmd(command);	
	}
	
	public static void main(String args[])
	{
		String workspace = EngineConfig.getPagerankWorkspace();
		String linkScoreFile = workspace + "/linkScore.txt";
		String indFile = workspace + "/ind.txt";
		String outdFile = workspace + "/outd.txt";
		
		PageRank pg = new PageRank();
		pg.run(EngineConfig.getLinkNodeMapFile(), EngineConfig.getLinkGraphFile(), EngineConfig.getPagerankResultFile(), linkScoreFile, indFile, outdFile);
	}
}
