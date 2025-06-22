package com.thiru.daliyworklog.daliyworklog.model;

import lombok.Data;
import java.util.*;

@Data
public class BaseResponse {
  private String status;
  private int code;
  private String message;
  private Map<String, Object> data = new HashMap<>();
  public void addData(String key, Object value) { data.put(key, value); }
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public int getCode() {
	return code;
}
public void setCode(int code) {
	this.code = code;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Map<String, Object> getData() {
	return data;
}
public void setData(Map<String, Object> data) {
	this.data = data;
}

  


}
