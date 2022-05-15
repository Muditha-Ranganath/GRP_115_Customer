package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final String query = "INSERT INTO customers(customerName,customerAcc_no,customerEmail,customerPhone) VALUES(?,?,?,?)";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get PrintWriter
				PrintWriter pw = resp.getWriter();
				//set content type
				resp.setContentType("text/html");
				//GET THE book info
			
				String name = req.getParameter("name");
				String Acc_no = req.getParameter("Acc_no");
				String email = req.getParameter("email");
				String phone = req.getParameter("phone");
				//LOAD jdbc driver
				try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				}catch(ClassNotFoundException cnf) {
					cnf.printStackTrace();
				}
				//generate the connection
				try(Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test2", "root", "");
						PreparedStatement ps = con.prepareStatement(query);){
					
					ps.setString(1, name);
					ps.setString(2, Acc_no);
					ps.setString(3, email);
					ps.setString(4, phone);
					int count = ps.executeUpdate();
					if(count==1) {
						pw.println("<h2>Record Is Registered Sucessfully</h2>");
					}else {
						pw.println("<h2>Record not Registered Sucessfully");
					}
				}catch(SQLException se) {
					se.printStackTrace();
					pw.println("<h1>"+se.getMessage()+"</h2>");
				}catch(Exception e) {
					e.printStackTrace();
					pw.println("<h1>"+e.getMessage()+"</h2>");
				}
				pw.println("<a href='Customer.html'>Home</a>");
				pw.println("<br>");
				pw.println("<a href='cusList'>Customer List</a>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}

}
