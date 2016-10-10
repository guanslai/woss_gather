package com.briup.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

public class ConnectionFactory {
	
	private static String driver; 
	private static String url;
	private static String user;
	private static String password;
	
	static{
		Properties p = new Properties();
		try {
			p.load(ConnectionFactory.class.getResourceAsStream("db.properties"));
			driver = p.getProperty("driver");
			url = p.getProperty("url");
			user = p.getProperty("user");
			password = p.getProperty("password");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Connection getConnetion(){
		Connection conn = null;
		Map<String, String> map = ParseXML.ParseToDbStore();
		driver = map.get("driver");
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return conn;
	}
	
	public static void close(ResultSet rs,Statement stmt,Connection conn){
		if(rs!=null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if(stmt!=null)
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public static void close(Statement stmt,Connection conn){
		close(null,stmt,conn);
	}
}
