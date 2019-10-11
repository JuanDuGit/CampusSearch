package site.util;

/**
 * 搜索结果封装类
 * @author hello
 *
 */
public class Record 
{
	private String url;
	private String title;
	private String content;
	private String snapshotSrc;
	
	public Record()
	{
		super();
		url= "";
		title= "";
		content= "";
		snapshotSrc= "";
	}
	
	public Record(String url, String title, String content, String snapshotSrc) {
		super();
		this.url = url;
		this.title = title;
		this.content = content;
		this.snapshotSrc = snapshotSrc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSnapshotSrc() {
		return snapshotSrc;
	}

	public void setSnapshotSrc(String snapshotSrc) {
		this.snapshotSrc = snapshotSrc;
	}
	
}
