package config;

/**
 * 鎼滅储寮曟搸閰嶇疆鏂囦欢
 * @author hello
 *
 */
public class EngineConfig 
{
	private static final String DEFAULT_CHARSET= "utf-8";
	
	private static final String DATA_HOME= "F:/data";
	private static final String SOLR_HOME= "E:/dasan/search_engine/solr-5.1.0";
	private static final String PAGERANK_TOOL= "E:/CampusSearch/_extern_/pagerank";
	private static final String URL2BMP_TOOL= "E:/CampusSearch/url2bmp.exe";
	
	
	private static final String DATABASE_RAW= DATA_HOME+"/raw";
	private static final String DATABASE_JSON= DATA_HOME+"/json";
	
	private static final String LINK_NODE_MAP_FILE= DATA_HOME+"/linkAnalyze/nodes.txt";
	private static final String LINK_GRAPH_FILE= DATA_HOME+"/linkAnalyze/graph.txt";
	private static final String LINK_ANCHOR_FILE= DATA_HOME+"/linkAnalyze/anchors.txt";
	private static final String PAGERANK_RESULT_FILE= DATA_HOME+"/linkAnalyze/rankResult.txt";
	private static final String PAGERANK_WORKSPACE= DATA_HOME+"/linkAnalyze/workspace";
	
	private static final String DATABASE_SNAPSHOT=DATA_HOME+"/snapshot";
	
	
	private static final String POST_JAR= SOLR_HOME + "/example/exampledocs/post.jar";
	private static final String ENGINE_SERVER= SOLR_HOME + "/bin/solr.cmd";
	
	private static final String CORE= "mycore";
	private static final String HOST= "localhost";
	private static final int PORT= 8983;
	private static final String SOLR_HOME_URL= "http://"+HOST+":"+PORT+"/solr/"+CORE+"/";
	
	// uri '/data' 瀵瑰簲鐩綍锛�鍦╯erver.xml涓厤缃�	
	
	public static String getDefaultCharset() {
		return DEFAULT_CHARSET;
	}
	public static String getDatabaseRaw() {
		return DATABASE_RAW;
	}
	public static String getDatabaseJson() {
		return DATABASE_JSON;
	}
	public static String getSolrHome() {
		return SOLR_HOME;
	}
	public static String getPostJar() {
		return POST_JAR;
	}
	public static String getCore() {
		return CORE;
	}
	public static String getHost() {
		return HOST;
	}
	public static int getPort() {
		return PORT;
	}
	public static String getSolrHomeUrl() {
		return SOLR_HOME_URL;
	}
	public static String getLinkGraphFile() {
		return LINK_GRAPH_FILE;
	}
	public static String getLinkAnchorFile() {
		return LINK_ANCHOR_FILE;
	}
	public static String getLinkNodeMapFile() {
		return LINK_NODE_MAP_FILE;
	}
	public static String getPagerankResultFile() {
		return PAGERANK_RESULT_FILE;
	}
	public static String getPagerankTool() {
		return PAGERANK_TOOL;
	}
	public static String getPagerankWorkspace() {
		return PAGERANK_WORKSPACE;
	}
	public static String getEngineServer() {
		return ENGINE_SERVER;
	}
	public static String getUrl2bmpTool() {
		return URL2BMP_TOOL;
	}
	public static String getDatabaseSnapshot() {
		return DATABASE_SNAPSHOT;
	}
}
