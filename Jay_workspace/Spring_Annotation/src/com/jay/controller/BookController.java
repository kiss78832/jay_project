package com.jay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jay.service.BookService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
}
