package demo;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LastLoggedInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
        String cId = request.getParameter("cID");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Task","root","password");
            PreparedStatement ps = con.prepareStatement("SELECT customer_id,full_name, LoginTime FROM Customers WHERE customer_id = ?");
            ps.setInt(1, Integer.parseInt(cId));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	 	int customerId = rs.getInt("customer_id");
            	    String fullName = rs.getString("full_name");
            	    String loginTime = rs.getString("LoginTime");
            	    out.println("<div style='color: black; font-size: 18px; text-align: center;'>");
            	    out.println("<h2>The Details of The customer:</h2>");
            	    out.println("<p><strong>Customer ID:</strong> " + customerId + "</p>");
            	    out.println("<p><strong>Full Name:</strong> " + fullName + "</p>");
            	    out.println("<p><strong>Last Logged In Time:</strong> " + loginTime + "</p>");
            	    out.println("</div>");
              
            } else {
                out.println("<font color = red size =18 > No customer found with the customer ID <br>");
                out.println("<a href = lastLoggedInDetails.jsp> TRY AGAIN!! </a>");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace(out);
        } catch (SQLException e) {
            e.printStackTrace(out);
        }
	}

}
