package pub_sub;

public class Message implements Comparable<Message>{
	private String header;
	private String content;
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public int compareTo(Message o) {
		// TODO Auto-generated method stub
		if(content.hashCode()>o.getContent().hashCode()) return 1;
		if(content.hashCode()<o.getContent().hashCode()) return -1;
		return 0;
	}

}
