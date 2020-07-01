package com.jay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jay.dao.BookDao;



@Service
public class BookService {

//	@Qualifier("bookDao2")
	@Autowired(required = false)
	private BookDao bookDao;
	
	public void print() {
		System.out.println("Book Dao");
	}

	@Override
	public String toString() {
		return "BookService [bookDao=" + bookDao + "]";
	}
	
	
}
