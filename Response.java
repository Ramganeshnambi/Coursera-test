package com.training.students.model;

import io.swagger.annotations.ApiModelProperty;

public class Response {
	@ApiModelProperty(position =1, required = true, value = "Test Duration containing numbers in mins")
	private MetaData metaData;
	@ApiModelProperty(position = 2, required = true, value = "Test Duration containing numbers in mins")
	private Data data;
	@ApiModelProperty(position = 3, required = true, value = "Test details")
	private ErrorDetails error;
	public MetaData getMetaData() {
		return metaData;
	}
	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public ErrorDetails getError() {
		return error;
	}
	public void setError(ErrorDetails error) {
		this.error = error;
	}
	

}
