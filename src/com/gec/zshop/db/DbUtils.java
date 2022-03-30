package com.gec.zshop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
	
	private static Connection conn=null;
	private final static String URL="jdbc:mysql://localhost:3306/mysql";
    private final static String USER="root";
    private final static String PASSWORD="1111";
    
static {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection openConn() throws SQLException
	{
		if(conn==null)
		{
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
		}
		
		return conn;
	}
	
	public static void closeConn()
	{
		if(conn!=null)
		{
			try {
				conn.close();
				conn=null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}