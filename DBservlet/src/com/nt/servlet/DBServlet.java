package com.nt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DBServlet extends HttpServlet {
	Connection con;
	PreparedStatement ps;
	
  public void init() {
		try
		{
			System.out.println("hi");
			//get access to servletcontext object
			ServletContext sc=getServletContext();
			//read context parametervalues
			String s1=sc.getInitParameter("driver");
			String s2=sc.getInitParameter("dburl");
			String s3=sc.getInitParameter("dbuser");
			String s4=sc.getInitParameter("dbpwd");
			//create jdbc con object
			Class.forName(s1);
		con=DriverManager.getConnection(s2,s3,s4);
		//create jdbc prepared statement
		ps=con.prepareStatement("SELECT ENO,ENAME,JOB,SAL FROM EMPLOYEE WHERE ENO=?");		}//try
		catch(Exception se) {
			se.printStackTrace();
		}//init
	}
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
	try {
		//read form data from formpage
		int no=Integer.parseInt(req.getParameter("teno"));
		//get printwriter object
			PrintWriter pw=res.getWriter();
		//set content type
			res.setContentType("text/html");
		//write jdbc code
		//set value to parameter of the query
			ps.setInt(1,no);
		//execute the query
	ResultSet rs=ps.executeQuery();	
			//process resultset object and display emp details
			if(rs.next()) {

			pw.println("<br><b><i>Emp number is:</i></b>"+rs.getInt(1));
				pw.println("<br><b><i>Emp name is:</i></b>"+rs.getString(2));
				pw.println("<br><b><i>Emp salary is:</i></b>"+rs.getFloat(3));
				pw.println("<br><b><i>Emp job is:</i></b>"+rs.getString(4));
			
			}//if
			//close resultset object
			rs.close();
			//close stream object
			pw.close();
			
	}
	
		//try
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}//doGet()
public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
 doGet(req,res);
 
}
public void destroy(Object rs) {
	//close jdbc objects
	try {
		if(rs!=null)
			((Connection) rs).close();
	}
	catch(Exception se) {
		se.printStackTrace();
	}
	try {
		if(ps!=null)
			ps.close();
	}
	catch(SQLException se) {
		se.printStackTrace();
	}
	try {
		if(con!=null)
			con.close();
	}
	catch(SQLException se) {
		se.printStackTrace();
	}
	
}//destroy


}//class
