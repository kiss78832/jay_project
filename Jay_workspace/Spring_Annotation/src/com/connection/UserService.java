package com.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	
	@Transactional
	public void insertUser() {
		userDao.insert();
		System.out.println("���J����");
		int i = 10/0; //�G�N����
	}
}