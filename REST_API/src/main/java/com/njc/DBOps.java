package com.njc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Path("/njc")
public class DBOps {

	@GET
	@Produces("text/html")
	@Path("/retrieve_data")
	public String get_data(@QueryParam("id") Integer id) throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/njc_demo","root","");
		
			Statement stmt = con.createStatement();
			String sql = "select * from user_info where id="+id;
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int id1 = rs.getInt(1);
				String name = rs.getString(2);
				int age = rs.getInt(3);
				String mail = rs.getString(4);
				String uname = rs.getString(5);
				return "<body style=background-color:aquamarine><h2 style=text-align:center;> ID: "+id1+"<br> Name: "+name+"<br> Age: "+age+"<br> Email: "+mail+"<br> Username: "+uname+"</h2></body>";
			}
			con.close();
		}
			catch (Exception e) {
				return "<body style=background-color:aquamarine><h2 style=text-align:center;>Invalid Input</h2></body>";
			}
		
		return "<body style=background-color:aquamarine><h2 style=text-align:center;>Record not found</h2></body>";
	}
	
	@POST
	@Produces("text/html")
	@Path("/add_record")
	public String add_record(@FormParam("id") Integer id, @FormParam("name") String name, @FormParam("age") Integer age, @FormParam("email") String email, @FormParam("uname") String uname) throws Exception
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/njc_demo","root","");
		
			PreparedStatement stmt = con.prepareStatement("insert into user_info values(?,?,?,?,?)");
			stmt.setInt(1, id);
			stmt.setString(2, name);
			stmt.setInt(3, age);
			stmt.setString(4, email);
			stmt.setString(5, uname);
			
			stmt.executeUpdate();
			con.close();
		}
			catch (Exception e) {
				//e.printStackTrace();
				return "<body style=background-color:aquamarine><h2 style=text-align:center;>Invalid Input</h2></body>";
				
			}
		
		
		return "<body style=background-color:aquamarine><h2 style=text-align:center;>Record Inserted</h2></body>";
	}
	
}
