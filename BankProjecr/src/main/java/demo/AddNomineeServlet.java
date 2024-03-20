package demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AddNomineeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
	    String cId = request.getParameter("cId");
	    String nomineeName = request.getParameter("nName");
	    String relationship = request.getParameter("rship");
	    String cNo = request.getParameter("cno");
	    

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Task", "root", "password");
	        String query = "INSERT INTO Nominee (customer_id, nominee_name, relationship ,contact_no) VALUES (?, ?, ?, ?)";
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setInt(1, Integer.parseInt(cId));
	        ps.setString(2, nomineeName);
	        ps.setString(3, relationship);
	        ps.setString(4, cNo);
	        int rowsInserted = ps.executeUpdate();
	        if (rowsInserted > 0) {
	            out.println("<font color='green'>Nominee Details added successfully!</font>");
	        } 
	        ps.close();
	        con.close();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
