package com.example.rss.domain;

import java.io.Serializable;
import java.util.Date;

public class Feed implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String url;
	private String title;
	private String description;
	private Date date;

	public Feed() {

	}

	public Feed(String url, String description, Date date, String title) {
		super();
		this.url = url;
		this.description = description;
		this.date = date;
		this.title = title;
	}

	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
