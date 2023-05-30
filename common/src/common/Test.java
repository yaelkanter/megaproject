package common;


import java.io.Serializable;


@SuppressWarnings("serial")
public class Test implements Serializable{
	
	private String select;
	private String id;
	private String subjectText;
	private String CourseName;
	private String authorText;
	private String status;
	private Integer Duration;
	
	public Test(){
	}
	
	public Test(String select,String id, String subjectText, String courseName, String authorText, String status,Integer Duration) {
		super();
		this.select=select;
		this.id = id;
		this.subjectText = subjectText;
		this.CourseName = courseName;
		this.authorText = authorText;
		this.status = status;
		this.Duration = Duration;
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
		return CourseName;
	}

	public void setCourseName(String courseName) {
		CourseName = courseName;
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
	
	
	public String toString() { return
			 id+" "+subjectText+" "+CourseName+" "+subjectText+" "+status+" "+status+" "; }
	
	
	
	
}