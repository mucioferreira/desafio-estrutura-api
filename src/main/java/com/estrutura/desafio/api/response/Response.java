package com.estrutura.desafio.api.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class Response<T> {

	private T data;
	private List<String> errors;
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	
	public List<String> getErrors() {
		if(this.errors == null) this.errors = new ArrayList<String>();
		return errors;
	}
	
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	
	public ResponseEntity<Response<T>> getResponseWithErrors(Response<T> response, BindingResult result) {
		result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
		return ResponseEntity.badRequest().body(response);
	}
	
}
