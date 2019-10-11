package pagerank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import config.EngineConfig;
import docParser.ParserBase;
import docParser.HtmlParser;

public class CreateGraph {
	
	static Map<String,String> map = new HashMap<String,String>();
	Map<String,String> mapforone = null;
	Map<String,HashSet> mapurltext = new HashMap<String,HashSet>();
	FileOutputStream outgraph=null;
	FileOutputStream outanchor=null;
	
	public void init(){
		File file=new File(EngineConfig.getLinkGraphFile());
		File fileanchor=new File(EngineConfig.getLinkAnchorFile());
		try {
			outgraph=new FileOutputStream(file);
			outanchor = new FileOutputStream(fileanchor);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writetofile(String n){
		String nodenum = n;
		if(mapforone.size()>0) 
			nodenum = ","+n;
		if(!mapforone.containsKey(n)){
			try {
				outgraph.write(nodenum.getBytes());
				mapforone.put(n, "");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void add2anchormap(String url, String text){
		if(mapurltext.containsKey(url)){
			Set<String> set = mapurltext.get(url);
			if(!set.contains(text)){
				set.add(text);
			}
			
		}else{
			HashSet oneset = new HashSet();
			oneset.add(text);
			mapurltext.put(url, oneset);
			
		}
	}
	public void writetoanchorfile(){
		for(Map.Entry<String, HashSet> entry:mapurltext.entrySet()){ 
			HashSet set = entry.getValue();
			String url = entry.getKey()+":";
			boolean first = true;
			try {
				outanchor.write(url.getBytes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Iterator<String> it = set.iterator();  
			
			while (it.hasNext()) { 
				String writenstr = null;
				String onetext = it.next();
				//System.out.println(s);
				//System.out.println(first);
				if(!first){
					writenstr= "-->"+onetext;  
				}else{
					writenstr = onetext;
					first = false;
				}
				try {
					outanchor.write(writenstr.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			
			String endl = ""+"\r\n";
			try {
				outanchor.write(endl.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		} 
		
	}
	
	public void create(String inRawDir){
		File raw= new File(inRawDir);
		HtmlParser hp = null;
		ParserBase hp2 = null;
		
		if (raw.isFile()){
		try{	
			try {
				hp2 = ParserBase.createParser(raw);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if(hp2 instanceof HtmlParser){
				hp = (HtmlParser) hp2;
			}
			
			if(!(null==hp)){
				Elements anchors = hp.getHref();
				if(anchors.size()>0){
					String abspath = raw.getAbsolutePath();
					int k = abspath.indexOf("raw");
					if(k>0){
						String path= abspath.substring(k+4).toLowerCase();
						path = path.replaceAll("\\\\","/"); 
						String thishref = "http://"+path;
						
						boolean head = true;
					//	System.out.println(h);
						if(map.containsKey(thishref)){
							int linkout = 0;
							mapforone =new HashMap<String,String>();
							
							String nodenum = null;
							String href = null;
							String text = null;
							// 本网页中的每个<a>标签处理
							for(Element ele:anchors){
								nodenum = null;
								href = null;
								text = null;
								
								href = ele.attr("href");
								text = ele.text();
								if(text.equals("")) continue;
								//
								if(href.contains("://")){
									linkout++;
									//System.out.println(href);
								}else if(href.startsWith("/")){
									 String subpath[] = path.split("/");
									 if(subpath[0] != null){
										 href = "http://"+subpath[0]+href;
										 linkout++;
									 }
									// System.out.println("haha"+subpath[0]);
								}else if((!href.contains("@")) && href.contains(".")){
									
									k = path.lastIndexOf("/");
									if(k>0){
										String tmppath = path.substring(0,k).toLowerCase();
										href = "http://"+tmppath+href;
										linkout++;
										//System.out.println(href);
									}
								}
								
								if(href!=null && href.startsWith("http")){
									if(head){
										String urlnum = map.get(thishref);
										urlnum=urlnum+":";
										try {
											outgraph.write(urlnum.getBytes());
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										head = false;
									}
									
									 add2anchormap(href,text);
									 if(map.containsKey(href)){
										nodenum = map.get(href);
										writetofile(nodenum);
									 }
								}
							}
							
							if((!head)&& linkout>0){
								String endl = ""+"\r\n";
								try {
									outgraph.write(endl.getBytes());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						}
					}// if(k>0)
				}// if(anchors.size()>0)
			} //if(null!=hp)
		} catch(Exception e){
			e.printStackTrace();
			System.out.println(raw.getAbsolutePath());
		}
		}// if(raw isfile)
		
		else{
			
			File[] subraws= raw.listFiles();
			for (File subraw : subraws){
				create(inRawDir+"/"+subraw.getName());
			}
		
		}
	}
	public static void main(String args[]) throws IOException
	{
		CreateGraph graph = new CreateGraph();
		graph.init();
		File2node fn = new File2node();
		String inRawDir= EngineConfig.getDatabaseRaw();
		fn.CreateFile2numMap(inRawDir);
		fn.WritetoFile();
		graph.map = fn.map;
		graph.create(inRawDir);
		graph.writetoanchorfile();
		
	}

}
