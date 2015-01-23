package com.jia.voa.db.bean;

import java.io.Serializable;


public class ArticleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8837036378753640751L;
	
	private int id;
	private int subjectId;
	private String title;
	private String url;
	private String content;
	private String contentZh;
	private String mp3Url;
	private String mp3Local;
	private String lrcUrl;
	private String lrcLocal;
	private String videoUrl;
	private String videoLocal;
	private int browseTimes;
	private boolean isCollected;
	private int state;
	private String date;

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

	public String getMp3Url() {
		return mp3Url;
	}

	public void setMp3Url(String mp3Url) {
		this.mp3Url = mp3Url;
	}

	public String getMp3Local() {
		return mp3Local;
	}

	public void setMp3Local(String mp3Local) {
		this.mp3Local = mp3Local;
	}

	public String getLrcUrl() {
		return lrcUrl;
	}

	public void setLrcUrl(String lrcUrl) {
		this.lrcUrl = lrcUrl;
	}

	public String getLrcLocal() {
		return lrcLocal;
	}

	public void setLrcLocal(String lrcLocal) {
		this.lrcLocal = lrcLocal;
	}

	public int getBrowseTimes() {
		return browseTimes;
	}

	public void setBrowseTimes(int browseTimes) {
		this.browseTimes = browseTimes;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentZh() {
		return contentZh;
	}

	public void setContentZh(String contentZh) {
		this.contentZh = contentZh;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoLocal() {
		return videoLocal;
	}

	public void setVideoLocal(String videoLocal) {
		this.videoLocal = videoLocal;
	}

	public boolean isCollected() {
		return isCollected;
	}

	public void setCollected(boolean isCollected) {
		this.isCollected = isCollected;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
