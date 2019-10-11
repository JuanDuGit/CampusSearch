package pagerank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import config.EngineConfig;
import docParser.HtmlParser;
import docParser.OfficeParser;
import docParser.ParserBase;
import docParser.PdfParser;

public class File2node {
	int total = 0;
	int tmp = 0;
	Map<String,String> map = new HashMap<String,String>();
	void CreateFile2numMap(String inRawDir) throws IOException{
		
		File raw= new File(inRawDir);
		HtmlParser hp = null;
		
		if (raw.isFile()){
			//System.out.println(raw.getAbsolutePath());
			String abspath = raw.getAbsolutePath();
			int k = abspath.indexOf("news.tsinghua"); //---------------------------
			if(k>0){
			String path= abspath.substring(k).toLowerCase();
			path = path.replaceAll("\\\\", "/");
			String h = "http://"+path;
			//System.out.println(h);
			if(!map.containsKey(h)){
				String num = ""+total;
				map.put(h, num);
				total++;
			}
			}
		}else{
			
			File[] subraws= raw.listFiles();
			for (File subraw : subraws){
				CreateFile2numMap(inRawDir+"/"+subraw.getName());
			}
		
		}
		
		
	}
	public void WritetoFile(){
		//System.out.println(total);
		//System.out.println(tmp);
		try {
			File file=new File(EngineConfig.getLinkNodeMapFile());
			FileOutputStream out=new FileOutputStream(file);
			for(Map.Entry<String, String> entry:map.entrySet()){  
				//System.out.println(entry.toString());
				
				String str= entry.getKey().toString()+"-->"+entry.getValue().toString()+"\n";
			     //out.write(entry.getKey().toString()+"--->"+entry.getValue().toString());
				try {
					out.write(str.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}   
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //���׷�ӷ�ʽ��true        
        
	}


	public static void main(String args[]) throws IOException
	{
		String inRawDir= "E:/dasan/search_engine/data/raw/news.tsinghua.edu.cn/publish/news";
		File2node fn = new File2node();
		fn.CreateFile2numMap(inRawDir);
		//System.out.println(fn.total);
		//System.out.println(fn.map.toString());
		fn.WritetoFile();
		
		
		
		 
	}
}
