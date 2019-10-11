package additional;

import java.io.File;
import tool.CmdTool;
import config.EngineConfig;

/**
 * http://www.pixel-technology.com/freeware/url2bmp/english/
 * http://www.pixel-technology.com/freeware/url2bmp/english/cl.html
 * @author hello
 *
 */
public class Url2Bmp 
{
	String format;
	int wx, wy;
	
	public Url2Bmp()
	{
		format = "png";
		wx = 1024;
		wy = 1500;
	}
	
	public Url2Bmp(String format, int wx, int wy) {
		super();
		this.format = format;
		this.wx = wx;
		this.wy = wy;
	}

	public void url2bmp(String url, String imgfile)
	{
		String command = "cmd /c start /min "+EngineConfig.getUrl2bmpTool()+" -url " +url+" -format "+format+" -file "+imgfile+" -wx "+ wx+" -wy "+wy+" -maximize -wait 2 -notinteractive";
		CmdTool.executeCmd(command);
	}
	
	public void url2bmp(String inUrlDir, String outImgDir, String uri)
	{
		File raw= new File(inUrlDir);
		if (raw.isFile())
		{
			if (uri.indexOf("://")<0)
				uri = "http://"+uri;
			outImgDir += "."+format;
			url2bmp(uri, outImgDir);
		}
		else
		{
			new File(outImgDir).mkdir();
			
			File[] subraws= raw.listFiles();
			for (File subraw : subraws)
				url2bmp(inUrlDir+"/"+subraw.getName(), outImgDir+"/"+subraw.getName(), uri.length()>0 ? uri+"/"+subraw.getName() : subraw.getName());
		}
	}
	
	public static void main(String[] args)
	{
		new Url2Bmp().url2bmp(EngineConfig.getDatabaseRaw(), EngineConfig.getDatabaseSnapshot(),"");
	}
}
