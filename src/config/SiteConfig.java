package config;

/**
 * 前台服务配置文件
 * @author hello
 *
 */
public class SiteConfig 
{
	private static final int RECORDS_PER_PAGE= 10;
	private static final int DIGEST_LENGTH= 300; //摘要长度，仅当高亮结果为空时使用
	
	public static int getRecordsPerPage() {
		return RECORDS_PER_PAGE;
	}

	public static int getDigestLength() {
		return DIGEST_LENGTH;
	}
	
}
