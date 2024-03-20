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
import java.sql.ResultSet;
import java.sql.SQLException;


public class OtherDescriptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String cID = request.getParameter("cId");
        String description = request.getParameter("description");
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(request.getParameter("amount")));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Task", "root", "password")) {
                String query = "SELECT balance FROM customers WHERE customer_id = ?";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setInt(1, Integer.parseInt(cID));
                    try (ResultSet rs = ps.executeQuery()) {
                        BigDecimal updatedBalance = null; 

                        if (rs.next()) {
                            BigDecimal balance = rs.getBigDecimal("balance");

                            if(description.equals("Cheque")) {
                                updatedBalance = balance.add(amount);
                            }
                            else if(description.equals("Unpaid Cheques")) {
                                updatedBalance = balance.subtract(amount);
                            }
                            else if(description.equals("Other Debits")) {
                                updatedBalance = balance.subtract(amount);
                            }
                            else {
                                // Handle unknown description
                                out.println("<font color='red'>Unknown description.</font>");
                                return; // Exit the method if the description is unknown
                            }

                            String query1 = "UPDATE Customers SET LoginTime = NOW() WHERE customer_id = ?";
                            try (PreparedStatement ps1 = con.prepareStatement(query1)) {
                                ps1.setInt(1, Integer.parseInt(cID));
                                ps1.executeUpdate();
                            }

                            String query2 = "INSERT INTO Transactions (customer_id, description, amount, balance, transaction_time) VALUES (?, ?, ?, ?, NOW())";
                            try (PreparedStatement ps2 = con.prepareStatement(query2)) {
                                ps2.setInt(1, Integer.parseInt(cID));
                                ps2.setString(2, description);
                                ps2.setBigDecimal(3, amount);
                                ps2.setBigDecimal(4, updatedBalance);
                                ps2.executeUpdate();
                            }

                            String query3 = "UPDATE Customers SET balance = ? WHERE customer_id = ?";
                            try (PreparedStatement ps3 = con.prepareStatement(query3)) {
                                ps3.setBigDecimal(1, updatedBalance);
                                ps3.setInt(2, Integer.parseInt(cID));
                                ps3.executeUpdate();
                            }

                            out.println("<font color='green'>Transaction added successfully.</font>");
                        }
                        else {
                            out.println("<font color='red'>Customer ID not found.</font>");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(out); 
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace(out); 
        }
    }
}
