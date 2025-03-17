package com.chat.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DbHandler {
public boolean isUserAuthenticated(UserTransfer userTransfer) throws SQLException, ClassNotFoundException, Exception{
    	
  		Connection connection = null;
  		PreparedStatement pstmt = null;
  		ResultSet rs = null;
  		String SQL = "select userid from users where userid=? and password=?";
  		
		try {
			connection = (Connection) IDbHandler.createConnection();
			pstmt = connection.prepareStatement(SQL);
		
			pstmt.setString(1,  userTransfer.getUserid());	
			pstmt.setString(2,  (String)userTransfer.getPassword());
			
			rs = pstmt.executeQuery();
			return rs.next();
		}
		finally {
			if(rs != null)rs.close();
			if(pstmt != null)pstmt.close();
			if(connection != null)connection.close();
		}
	}

	public int storeUser(UserTransfer userTransfer) throws ClassNotFoundException, Exception {
		Connection connection = null;
  		PreparedStatement pstmt = null;
  		ResultSet rs = null;
  		String sql = "INSERT INTO users (userid, password) VALUES (?, ?)";
  		
		try {
			connection = (Connection) IDbHandler.createConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  userTransfer.getUserid());	
			pstmt.setString(2,  (String)userTransfer.getPassword());
			
			int record = pstmt.executeUpdate();
			return record;		
		}
		finally {
			if(rs != null)rs.close();
			if(pstmt != null)pstmt.close();
			if(connection != null)connection.close();
		}
		
	}
	
public boolean fetchUser(UserTransfer userTransfer) throws SQLException, ClassNotFoundException, Exception{
    	
  		Connection connection = null;
  		PreparedStatement pstmt = null;
  		ResultSet rs = null;
  		String SQL = "select userid from users where userid=?";
  		
		try {
			connection = (Connection) IDbHandler.createConnection();
			pstmt = connection.prepareStatement(SQL);
			String userid = userTransfer.getUserid();
                      
			pstmt.setString(1,  userid);			
			rs = pstmt.executeQuery();
			return rs.next();
		}
		finally {
			if(rs != null)rs.close();
			if(pstmt != null)pstmt.close();
			if(connection != null)connection.close();
		}
	}
}
