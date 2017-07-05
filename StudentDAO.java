package com.training.students.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import com.training.students.model.Student;
import com.training.students.model.StudentMapper;

@Component
public class StudentDAO extends JdbcDaoSupport {

	@Autowired
	public StudentDAO(DataSource datasource) {
		setDataSource(datasource);
	}

	public List<Student> getAllStudents() {
		List<Student> bookbean = null;
		bookbean = getJdbcTemplate().query("Select * from T_XBBNHGV_Student",
				new StudentMapper());
		return bookbean;

	}
	
	  public List<Student> getStudent(String student_id){
	    	
	    	List<Student> studList = new ArrayList<Student>();
	    	studList = getJdbcTemplate().query("Select * from T_XBBNHGV_Student where student_id = ?", new Object[]{student_id}, new StudentMapper());
	    	return studList;
	    	
	    }

	public Student insertDetails(Student student) {
		int numRows = 0;
	    int[] types = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.INTEGER, Types.INTEGER };
		Object param[] = { student.getStudentId(), student.getStudentName(),
				student.getDept(), student.getSemester(),
				student.getRollno() };

		numRows = getJdbcTemplate().update(
				"insert into T_XBBNHGV_Student values(?,?,?,?,?)", param, types);
		return( numRows == 1 ?  student: null);
	}

	public Student deleteDetails(String id) {
		int numRows = 0;
		Student student = null;
		try{
			student = getJdbcTemplate().queryForObject(
					"select * from T_XBBNHGV_Student where student_id = ? ",
					new Object[] { id }, new StudentMapper());
			numRows = getJdbcTemplate().update(
					"delete from T_XBBNHGV_Student where student_id = ?",
					new Object[] { id }, new int[] { Types.VARCHAR });
		}
		catch(Exception e){
			student = null;
		}
		
		return(numRows == 1 ? student : null);
	}

	public Student updateDetails(Student student,int id) {
		int numRows = 0;
		try {
			numRows = getJdbcTemplate()
					.update("update T_XBBNHGV_Student set dept = ? where student_id = ?",
							new Object[] { student.getDept(),
							student.getStudentId() },
							new int[] { Types.VARCHAR, Types.VARCHAR });
		} catch (Throwable error) {
			System.out.println("Got error.  Returning null (404)");
			error.printStackTrace();
		} finally {
			if (numRows > 0)
				return student;
			else
				return null;

		}
	}
}

