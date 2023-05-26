package entities;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Question implements Serializable{
	
	private String id;
	private String subject;
	private String CourseName;
	private String questionText;
	private String questionNumber;
	private String lecturer;
	
	public Question() {
		
	}
	
	public Question(String id, String subject, String courseName, String questionText, String questionNumber, String lecturer) {
		super();
		this.id = id;
		this.subject = subject;
		this.CourseName = courseName;
		this.questionText = questionText;
		this.questionNumber = questionNumber;
		this.lecturer = lecturer;
	}
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCourseName() {
		return CourseName;
	}

	public void setCourseName(String courseName) {
		CourseName = courseName;
	}

	public String getText() {
		return questionText;
	}

	public void setText(String text) {
		this.questionText = text;
	}

	public String getNumber() {
		return questionNumber;
	}

	public void setNumber(String questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionNumber;
	}
	public String getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(String questionNumber) {
		this.questionNumber = questionNumber;
	}
	
	public String toString() { return
			 id+" "+subject+" "+CourseName+" "+questionText+" "+questionNumber+" "
			 +lecturer; }
	
	
	
	
}