package com.training.students.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class StudentMapper implements RowMapper<Student> {

	public Student mapRow(ResultSet rs, int arg1) throws SQLException {
		Student student = new Student();
		student.setStudentId(rs.getString(1));
		student.setStudentName(rs.getString(2));
		student.setDept(rs.getString(3));
		student.setSemester(rs.getInt(4));
		student.setRollno(rs.getInt(5));
		return student;
	}

}
