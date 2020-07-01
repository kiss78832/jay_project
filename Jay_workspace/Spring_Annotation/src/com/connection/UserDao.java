package com.connection;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void insert() {
		String sql = "INSERT INTO TABLE01 (ID,USERNAME,AGE) VALUES(TABLE_SEQ.nextval,?,?)";
		String username = UUID.randomUUID().toString().substring(0,5);
		Double random = Math.random()*100000;
		int age = Integer.valueOf(random.toString().substring(0,2));
		jdbcTemplate.update(sql,username,age);
	}
}
