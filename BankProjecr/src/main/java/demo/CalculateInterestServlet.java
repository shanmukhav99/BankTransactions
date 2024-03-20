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
import java.sql.ResultSet;
import java.sql.SQLException;

public class CalculateInterestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String cID = request.getParameter("cId");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Task", "root", "password");

            String query = "SELECT balance, calculate_interest FROM Customers WHERE customer_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(cID));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                float balanceAmount = rs.getFloat("balance");
                boolean calculateInterest = rs.getBoolean("calculate_interest");
                float interestRate = 0.05f; 
                
                if (calculateInterest) {
                    float interest = balanceAmount * interestRate;
                    float newBalance = balanceAmount + interest;
                    out.println("Interest calculated successfully!");
                    out.println("Initial Balance: Rs " + balanceAmount);
                    out.println("Interest Rate: " + (interestRate * 100));
                    out.println("Interest Amount: Rs " + interest);
                    out.println("New Balance: Rs " + newBalance);
                } else {
                    out.println("<font color='red'>Interest calculation skipped!</font>");
                    out.println("<font color='red'>No interest calculated.!</font>");
                }
            } else {
                out.println("<font color='red'>No customer exists with customer id.</font>");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
