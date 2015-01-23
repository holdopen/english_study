package com.jia.voa.db.bean;

import java.io.Serializable;


public class SubjectBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3357398282224025611L;
	
	private int id;
	private String title;
	private String url;
	private String readTitle;
	private int entryCount;
	private int total;
	private int learnedCount;
	private int newCount;
	private String category;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReadTitle() {
		return readTitle;
	}

	public void setReadTitle(String readTitle) {
		this.readTitle = readTitle;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getLearnedCount() {
		return learnedCount;
	}

	public void setLearnedCount(int learnedCount) {
		this.learnedCount = learnedCount;
	}

	public int getNewCount() {
		return newCount;
	}

	public void setNewCount(int newCount) {
		this.newCount = newCount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getEntryCount() {
		return entryCount;
	}

	public void setEntryCount(int entryCount) {
		this.entryCount = entryCount;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
