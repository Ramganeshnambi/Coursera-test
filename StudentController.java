package com.training.students.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.training.students.dao.StudentDAO;
import com.training.students.model.Data;
import com.training.students.model.ErrorDetails;
import com.training.students.model.MetaData;
import com.training.students.model.Response;
import com.training.students.model.Student;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


///v2/api-docs/
@RestController
@EnableSwagger2

public class StudentController {
	
	@Autowired
	StudentDAO studentDao;
	@Autowired
	MetaData metaData;
	@Autowired
	Data data;
	@Autowired
	Response response;	
	@Autowired
	ErrorDetails errorDetails;
	
	@RequestMapping(value="/students", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value = "retrive the student records using Get method", notes = "returns all student details", response = Response.class)  
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successful retrieval of student details", response = Response.class),
		    @ApiResponse(code = 404, message = "User with given username does not exist",response = Response.class),		   
		    @ApiResponse(code = 400, message = "Bad Request",response = Response.class),
		    @ApiResponse(code = 500, message = "Internal Server Error",response = Response.class)}
		)
	public ResponseEntity<Response> getAllStudentDetails()
	{
		
		List<Student> studentList;
		try {
			studentList = studentDao.getAllStudents();
			if(studentList.isEmpty()){
				errorDetails.setCode("TRA2000");
				errorDetails.setDescription("No data is present in the table");
                saveMetaData(false,"Bad Request","400");             
                saveResponse(null,metaData,errorDetails);
                return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST) ;
			  }
			else{
			saveMetaData(true,"OK","200");
			saveData(null, studentList);
			saveResponse(data,metaData, null);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			errorDetails.setCode("TRA2001");
			errorDetails.setDescription(e.getMessage());
			saveMetaData(false,"Bad Request","400");		
			saveResponse(null,metaData,errorDetails);
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST) ;
		}
		
	}
	

	@RequestMapping(value="students/{id}", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE})
	@ApiParam(name = "id", value =  "Alphanumeric login to the application StudentRegistration", required = true)
	@ApiOperation(value = "retrive the student record using Get method", notes = "Returns the details of a student for a given id", response = Response.class)
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successful retrieval of a student details", response = Response.class),
		    @ApiResponse(code = 404, message = "Invalid Information Sent",response = Response.class),
		    @ApiResponse(code = 400, message = "Bad Request",response = Response.class),
		    @ApiResponse(code = 500, message = "Internal Server Error",response = Response.class)}
		)
	public ResponseEntity<Response> getStudent(@ApiParam(name = "id", value = "Alphanumeric login to the application Student Registration", required = true) @PathVariable("id") String studentId)
	{

		List<Student> studentList = new ArrayList<Student>();
			
			try {	
               // int studid=Integer.parseInt(studentId);
				studentList = studentDao.getStudent(studentId);	
				/*if(studid<=0){
					 saveMetaData(false,"Bad Request","400");
					 errorDetails.setCode("TRA2000");
					 errorDetails.setDescription("Database error");
                     saveResponse(data,metaData, errorDetails);
                     return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST) ;
				}*/
				if(studentList.isEmpty()){
					errorDetails.setCode("TRA2001");
					errorDetails.setDescription("Invalid ID");
					saveMetaData(false,"NOT FOUND","404");
					saveResponse(null,metaData, errorDetails);
					return  new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
				}
				else{
				saveMetaData(true,"OK","200");
				saveData(null, studentList);
				saveResponse(data,metaData, null);
				return  new ResponseEntity<Response>(response, HttpStatus.OK);
				}
								
			} catch (Exception e) {
				e.printStackTrace();
				errorDetails.setCode("TRA2002");				
				if( e instanceof IncorrectResultSizeDataAccessException  || e instanceof EmptyResultDataAccessException)
					errorDetails.setDescription("Bad Request");
				else if( e instanceof DataAccessException)
					errorDetails.setDescription("Database error");
				else
					errorDetails.setDescription(e.getMessage());
				saveMetaData(false,"Bad Request","400");
				saveResponse(null,metaData, errorDetails);
				return  new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST) ;				

			}	
	}
	private void saveResponse(Data data, MetaData metaData, ErrorDetails errorDet) {
		response.setData(data);
		response.setMetaData(metaData);
		response.setError(errorDet);
	}
	private void saveData(ErrorDetails erroDet, List student) {
		response.setError(erroDet);
		data.setOutput(student);
		
	}
	@ApiParam(name = "studentObj", value = "student object", required = true)
	@RequestMapping(value="/students",method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces={ MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value = "creates the student record using Post method", notes = "Creates a new student", response = Response.class)
	@ApiResponses(value = {		   
		    @ApiResponse(code = 201, message = "Created Response", response = Response.class),
		    @ApiResponse(code = 404, message = "Invalid Information Sent", response = Response.class),
		    @ApiResponse(code = 400, message = "Bad Request", response = Response.class),
		    @ApiResponse(code = 500, message = "Internal Server Error", response = Response.class)}
		)
	public ResponseEntity<Response> createStudent(@RequestBody Student student)
	{
		List<Student> studentList = new ArrayList<Student>();
		try{	
			if(student.getStudentId()!=null && student.getStudentName()!=null && student.getRollno()!=0 && student.getDept()!=null && student.getSemester()>=1 && student.getSemester()<=8){			
		   student =studentDao.insertDetails(student);	
			if(student!=null){
			studentList.add(student);		
			saveMetaData(true,"Created","201");
			saveData(null, studentList);
			saveResponse(data,metaData, null);
			return new ResponseEntity<Response>(response, HttpStatus.CREATED);
		}
			  else
              {
				  errorDetails.setCode("TRA2003");
				  errorDetails.setDescription("Student not created");
                  saveMetaData(false,"Conflict","409");
                  saveResponse(null,metaData, errorDetails);
                  return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
              }
              }
			 else
             {
				 errorDetails.setCode("TRA2004");
				 errorDetails.setDescription("Student not created");
                 saveMetaData(false,"Conflict","409");
                 saveResponse(null,metaData, errorDetails);
                 return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
             }
		}
			   catch(Exception e){
                   e.printStackTrace();                                        
                   errorDetails.setCode("TRA2002");
                   if (e instanceof DataAccessException)
                      {
                	   errorDetails.setDescription("Student already exists..");
                      }
                   else{
                	   errorDetails.setDescription("Database Error");
                   }
                   saveMetaData(false,"Bad Request","400");
                   saveResponse(null,metaData, errorDetails);
                   return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
   }
	}
	
	@ApiParam(name = "studentObj", value = "student object", required = true)
	@RequestMapping(value="/students/{id}",method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE, produces={ MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value = "Updates a student record using Put method", notes = "Updates the student data with student Id", response = Response.class)
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successful Response", response = Response.class),
		    @ApiResponse(code = 404, message = "Invalid Information Sent",response = Response.class),
		    @ApiResponse(code = 400, message = "Bad Request",response = Response.class),
		    @ApiResponse(code = 406, message = "Not Acceptable",response=Response.class),
		    @ApiResponse(code = 500, message = "Internal Server Error",response = Response.class)})
	public ResponseEntity<Response> updateStudent(@ApiParam(value = "object that need to be updated", required = true) @PathVariable("id") String id, @RequestBody Student student)
	{
		
		List<Student> studentList = new ArrayList<Student>();
		try
		{	
			if(student.getStudentId()!=null && student.getStudentName()!=null && student.getRollno()!=0 && student.getDept()!=null && student.getSemester()>=1 && student.getSemester()<=8){
		    int studid=Integer.parseInt(id);
			student =studentDao.updateDetails(student,studid);	
			if(student!=null){
				studentList.add(student);		
				saveMetaData(true,"OK","200");
				saveData(null, studentList);
				saveResponse(data,metaData, null);
				return new ResponseEntity<Response>(response, HttpStatus.OK);
			}
		}
			errorDetails.setCode("TRA2003");
			errorDetails.setDescription("Student not updated");
            saveMetaData(false,"Conflict","409");
            saveResponse(null,metaData, errorDetails);
	        return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);	
		}
		catch(Exception e){
			  e.printStackTrace();                                        
			  errorDetails.setCode("TRA2005");
			  errorDetails.setDescription("Miss match type student id");
              saveMetaData(false,"Not Acceptable","406");
              saveResponse(null,metaData, errorDetails);
              return new ResponseEntity<Response>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		
		
	}
	
	
	
	@RequestMapping(value="/students/{id}",method=RequestMethod.DELETE,  produces={ MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value = "deletes a student record using Delete method", notes = "Deletes a student with student id", response = Response.class)
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successful deletion  of student ", response = Response.class),
		    @ApiResponse(code = 404, message = "Invalid Information Sent",response = Response.class),
		    @ApiResponse(code = 400, message = "Bad Request",response = Response.class),
		    @ApiResponse(code = 500, message = "Internal Server Error",response = Response.class),
		    @ApiResponse(code = 406, message = "Not Acceptable",response=Response.class)}
		)
	
	public ResponseEntity<Response> deleteStudent(@ApiParam(name = "id", value = "Alphanumeric login to the application Student Registration", required = true) @PathVariable("id") String studentId  )
	{
		List<Student> studentList = new ArrayList<Student>();
		try{
			
			int studid=Integer.parseInt(studentId);
			Student student =studentDao.deleteDetails(studentId);	
			if(student==null){
				errorDetails.setCode("TRA2006");
				errorDetails.setDescription("Invalid ID..Student doesn't exist");
				saveMetaData(false,"Not Found","404");
				saveResponse(null,metaData, errorDetails);
				return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
			}
			else
			{
			studentList.add(student);
			saveMetaData(true,"OK","200");
			saveData(null, studentList);
			saveResponse(data,metaData, null);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
			
		}
		}
		catch(Exception e){
			e.printStackTrace();			
			errorDetails.setCode("TRA2005");
			errorDetails.setDescription("Invalid Entry or Type Mismatch");;
			saveMetaData(false,"Not Acceptable","406");
			saveData(errorDetails, null);
			saveResponse(null,metaData,errorDetails);
			return new ResponseEntity<Response>(response, HttpStatus.NOT_ACCEPTABLE);
		}	
		
	}
	
	
	private void saveMetaData(boolean success, String description, String responseId){	
		metaData.setSuccess(success);
		metaData.setDescription(description);
		metaData.setResponseId(responseId);
	}
	

}
