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
import java.sql.Timestamp;


public class AddCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String uId = request.getParameter("usrId");
        String fName = request.getParameter("fullname");
        String mail = request.getParameter("email");
        String pno = request.getParameter("pno");
        String address = request.getParameter("address");
        String aNo = request.getParameter("aadhar_number");
        String pNo = request.getParameter("pan_number");
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(request.getParameter("balance")));
        boolean calculateInterest = request.getParameter("calculate_interest") != null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Task", "root", "password");
            String query = "INSERT INTO Customers (user_id, full_name, email, phone_number, address, adhar_number, pan_number, balance, LoginTime, calculate_interest) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)";
            PreparedStatement ps = con.prepareStatement(query);
            
            // Check if full_name is not null
            if (fName != null && !fName.isEmpty()) {
                ps.setInt(1, Integer.parseInt(uId));
                ps.setString(2, fName);
                ps.setString(3, mail);
                ps.setString(4, pno);
                ps.setString(5, address);
                ps.setString(6, aNo);
                ps.setString(7, pNo);
                ps.setBigDecimal(8, amount);
                ps.setBoolean(9, calculateInterest);
                
                int rowsInserted = ps.executeUpdate();
                if (rowsInserted > 0) {
                    out.println("<font color='green'>Customer added successfully!</font>");
                } else {
                    out.println("<font color='red'>Failed to add customer.</font>");
                }
            } else {
                out.println("<font color='red'>Full name cannot be empty.</font>");
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
