package main;

import java.io.File;
import java.io.IOException;

import pagerank.CreateGraph;
import pagerank.PageRank;
import config.EngineConfig;
import engine.ParseEngine;

public class Main 
{
	public static void main(String[] args) throws IOException
	{
		String database_raw = EngineConfig.getDatabaseRaw();
		String database_json = EngineConfig.getDatabaseJson();
		String uri = "";
		
		// 抓取原始网页文件至 database_raw目录
		//...
		
		// 链接结构分析
		/*System.out.println("analyzing link structure");
		System.out.println("	createGraph");
		CreateGraph.main(null);  //分析源文件，生成锚文本文件，网页链接-网页编号映射文件 和 源网页编号-各被链接网页编号关系文件
		System.out.println("	pageRank");
		PageRank.main(null);     //根据上述文件计算PageRank，并生成结果文件
		
		// 解析文本内容并结合链接分析结果生成json文件
		System.out.println("parsing files");
		ParseEngine parseEngine = new ParseEngine(database_json);
		System.out.println("	dealing with raw file contents");
		parseEngine.addParseResult(database_raw, database_json, uri);  //解析原始网页，提取各域信息
		System.out.println("	adding anchor texts");
		parseEngine.addAnchorTexts(EngineConfig.getLinkAnchorFile());  //加入链接至各页的锚文本信息
		System.out.println("	adding pagerank results");
		parseEngine.addPageRankResult(EngineConfig.getPagerankResultFile()); //加入各页PageRank结果信息
		
		// 启动引擎服务器（本实现采用solr）
		//System.out.println("starting engine server");
		//engine.Server engineServer = new engine.Server();*/
		//engineServer.start();
		
		// 根据json文件建立索引
		System.out.println("indexing");
		new engine.Indexer().index(new File(database_json));
		
		//启动网站服务器（本实现采用tomcat）
		//...
		
		System.out.println("all prepared, ready to go");
		//查询、交互...
		
		//关闭网站服务器
		//...
		
		//关闭引擎
		//engineServer.stop();
		
	}
}
