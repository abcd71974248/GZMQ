package com.hotsun.mqxxgl.busi.model;
// default package
// Generated 2017-3-30 16:56:24 by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * FwLdxx generated by hbm2java
 */
public class ResponseResults implements Serializable{

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Map<String, String>> getResults() {
		return results;
	}

	public void setResults(List<Map<String, String>> results) {
		this.results = results;
	}

	private String status;
	private List<Map<String, String>> results;



}
