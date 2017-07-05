package com.training.students.model;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement(name="student")
public class Student {
	
	 @ApiModelProperty(position = 1, required = true, value = "brief description of the property :studentId ")
	 private String studentId;
	 @ApiModelProperty(position = 2, required = true, value = "brief description of the property :studentName ")
	 private String studentName;
	 @ApiModelProperty(position = 3, required = true, value = "brief description of the property :dept ")
	 private String dept;
	 @ApiModelProperty(position = 4, required = true, value = "brief description of the property :semester ")
	 private int semester;
	 @ApiModelProperty(position = 5, required = true, value = "brief description of the property :rollno ")
	 private int rollno;
	 @ApiModelProperty(position = 6, required = true, value = "brief description of the property :rollno ")
	
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public int getRollno() {
		return rollno;
	}
	public void setRollno(int rollno) {
		this.rollno = rollno;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
}
