package com.training.students.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class Data {


	@ApiModelProperty(position = 1, required = true, value = "brief description of the property :output ")
	private List<Student> output;
	
	
	public List<Student>  getOutput() {
		return output;
	}
	public void setOutput(List<Student> outputList) {
		this.output = outputList;
	}
	
	
}
