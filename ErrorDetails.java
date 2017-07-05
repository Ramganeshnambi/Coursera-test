package com.training.students.model;
 
import io.swagger.annotations.ApiModelProperty;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlRootElement;

@Generated("org.jsonschema2pojo")
@XmlRootElement(name = "Errors")
public class ErrorDetails {
	
    @ApiModelProperty(position = 1, required = true, value = "brief description of the property :code ")
	private String code;
    @ApiModelProperty(position = 2, required = true, value = "brief description of the property :description ")
	private String description;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}