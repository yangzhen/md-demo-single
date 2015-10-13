package com.md.demo.server.bean.entry;

/**
 * 
 * TestMap 
 * @author chenchao
 * @date Jul 14, 2015 2:48:50 PM
 *
 */
public class TestMap {

	private int id;

	private String text;
	
	private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestMap [id=" + id + ", text=" + text + ", name=" + name + "]";
    }

}