package com.chat.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface IDbHandler {
	public static Connection createConnection() throws ClassNotFoundException, SQLException{		
		final String url = "jdbc:mysql://localhost/dbchat";
		final String login = "root";
		final String password = "root";
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(url,login,password);
		if(con!=null)System.out.println("Connection created...");
		return con;
	}
}
