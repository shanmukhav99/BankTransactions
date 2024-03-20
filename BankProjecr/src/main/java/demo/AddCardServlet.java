package demo;

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
import java.sql.SQLException;
import java.sql.Date;

public class AddCardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String cId = request.getParameter("cId");
        String cType = request.getParameter("cType");
        String cStatus = request.getParameter("cStatus");
        String edate = request.getParameter("edate");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Task", "root", "password");
            String query = "INSERT INTO Cards (customer_id, card_type, status, expiration_date) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(cId));
            ps.setString(2, cType);
            ps.setString(3, cStatus);
            ps.setDate(4, Date.valueOf(edate)); 
            ps.executeUpdate();
            out.println("<font color='green'>Card added successfully!</font>");

            ps.close();
            con.close();
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
