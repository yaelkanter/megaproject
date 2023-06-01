package common;


import java.io.Serializable;


@SuppressWarnings("serial")
public class Test implements Serializable{
	
	private String select;
	private String id;
	private String subjectText;
	private String Course;
	private String authorText;
	private String status;
	private Integer Duration;
	private String TestCode;
	
	public Test(){
	}
	
	public Test(String select,String id, String subjectText, String courseName, String authorText,Integer Duration,String TestCode) {
		super();
		this.select=select;
		this.id = id;
		this.subjectText = subjectText;
		this.Course = courseName;
		this.authorText = authorText;
		this.Duration = Duration;
		this.TestCode = TestCode;
	}
	
	public String select() {
		return select;
	}

	public void setselect(String select) {
		this.select = select;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subjectText;
	}

	public void setSubject(String subject) {
		this.subjectText = subjectText;
	}

	public String getCourseName() {
		return Course;
	}

	public void setCourseName(String courseName) {
		Course = courseName;
	}

	public String getAuthor() {
		return authorText;
	}

	public void setAuthor(String authorText) {
		this.authorText = authorText;
	}

	public String getstatus() {
		return status;
	}

	public void setstatus(String status) {
		this.status = status;
	}
	public Integer getDuration() {
		return Duration;
	}

	public void setDturation(Integer Duration) {
		this.Duration = Duration;
	}
	
	public String getTestCode() {
		return TestCode;
	}

	public void setTestCode(String TestCode) {
		this.TestCode = TestCode;
	}
	public String toString() { return
			 id+" "+subjectText+" "+Course+" "+subjectText+" "+status+" "+status+" "+TestCode+" "; }
	
	
	
	
}