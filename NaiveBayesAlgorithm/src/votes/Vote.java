package votes;

public class Vote {
	private String[] attributes;
	private String className;
	
	public Vote(String[] attributes, String className) {
		super();
		this.attributes = attributes;
		this.className = className;
	}
	
	
	public String[] getAttributes() {
		return attributes;
	}
	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
