package com.jay.hibernate.helloworld;

import java.sql.Date;

/**
 * Hibernate �Ыث��[�Ƽh(Java Bean)�����~�Ӥ����O�Τ����A�ɭP���[�Ƽh��L���즾�V�C(�C�I�J���ج[)
 * 
 * */



public class News {
	
	private Integer id;
	private String title;
	private String author;
	
	private Date date_;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDate_() {
		return date_;
	}

	public void setDate_(Date date_) {
		this.date_ = date_;
	}

	public News() {
		super();
		// TODO Auto-generated constructor stub
	}

	public News(String title, String author, Date date_) {
		super();
		this.title = title;
		this.author = author;
		this.date_ = date_;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", author=" + author + ", date_=" + date_ + "]";
	}
	
	
}
