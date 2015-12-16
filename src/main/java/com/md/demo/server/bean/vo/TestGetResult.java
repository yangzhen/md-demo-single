package com.md.demo.server.bean.vo;

import java.util.List;

/**
 * 
 * TestGetResult
 * 
 * @author chenchao
 * @date Jul 14, 2015 2:31:14 PM
 *
 */
public class TestGetResult {

	private String text;

	private int id;

	List<String> strList;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<String> getStrList() {
		return strList;
	}

	public void setStrList(List<String> strList) {
		this.strList = strList;
	}

}
