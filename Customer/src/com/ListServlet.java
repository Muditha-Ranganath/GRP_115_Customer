package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cusList")
public class ListServlet extends HttpServlet {
	private static final String query = "SELECT ID,customerName,customerAcc_no,customerEmail,customerPhone FROM customers";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get PrintWriter
				PrintWriter pw = resp.getWriter();
				//set content type
				resp.setContentType("text/html");
				//LOAD jdbc driver
				try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				}catch(ClassNotFoundException cnf) {
					cnf.printStackTrace();
				}
				
				//generate the connection
				try(Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test2", "root", "");
						PreparedStatement ps = con.prepareStatement(query);){
					ResultSet rs = ps.executeQuery();
					pw.println("<table border='1' align='center'>");
					pw.println("<thead>");
					pw.println("<tr>");
					pw.println("<th>Customer ID</th>");
					pw.println("<th>Customer Name</th>");
					pw.println("<th>Customer Acc_no</th>");
					pw.println("<th>Customer Email</th>");
					pw.println("<th>Customer Phone</th>");
					pw.println("<th>Edit</th>");
					pw.println("<th>Delete</th>");
					pw.println("</tr>");
					pw.println("</thead>");
					while(rs.next()) {
						pw.println("<tr>");
						pw.println("<td>"+rs.getInt(1)+"</td>");
						pw.println("<td>"+rs.getString(2)+"</td>");
						pw.println("<td>"+rs.getString(3)+"</td>");
						pw.println("<td>"+rs.getString(4)+"</td>");
						pw.println("<td>"+rs.getString(5)+"</td>");
						pw.println("<td><a href='editScreen?customerid="+rs.getInt(1)+"'>Edit</a></td>");
						pw.println("<td><a href='deleteurl?customerid="+rs.getInt(1)+"'>Delete</a></td>");
						pw.println("</tr>");
					}
					pw.println("</table>");
				}catch(SQLException se) {
					se.printStackTrace();
					pw.println("<h1>"+se.getMessage()+"</h2>");
				}catch(Exception e) {
					e.printStackTrace();
					pw.println("<h1>"+e.getMessage()+"</h2>");
				}
				pw.println("<a href='Customer.html'>Home</a>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req,resp);
	}

}
