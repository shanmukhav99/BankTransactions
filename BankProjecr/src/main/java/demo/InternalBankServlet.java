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


public class InternalBankServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String cId1 = request.getParameter("cId1");
        String cId2 = request.getParameter("cId2");
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(request.getParameter("amount")));

        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps5 = null;
        PreparedStatement ps6 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/Task", "root", "password");

            String query = "SELECT * FROM Customers WHERE customer_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(cId1));
            rs = ps.executeQuery();

            String query1 = "SELECT * FROM Customers WHERE customer_id = ?";
            ps1 = con.prepareStatement(query1);
            ps1.setInt(1, Integer.parseInt(cId2));
            rs1 = ps1.executeQuery();

            if (rs.next() && rs1.next() && !cId1.equals(cId2)) {
                String query2 = "SELECT balance FROM Customers WHERE customer_id = ?";
                ps2 = con.prepareStatement(query2);
                ps2.setInt(1, Integer.parseInt(cId1));
                rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    BigDecimal balance = BigDecimal.valueOf(rs2.getInt("balance"));
                    if (balance.compareTo(amount) >= 0) {
                        BigDecimal updatedBalancecId1 = balance.subtract(amount);
                        BigDecimal updatedBalancecId2 = balance.add(BigDecimal.valueOf(amount.doubleValue()));


                        String query3 = "UPDATE Customers SET balance = ?, LoginTime= NOW() WHERE customer_id = ?";
                        ps3 = con.prepareStatement(query3);
                        ps3.setBigDecimal(1, updatedBalancecId1);
                        ps3.setInt(2, Integer.parseInt(cId1));
                        ps3.executeUpdate();

                        String query4 = "UPDATE Customers SET balance = ?,LoginTime= NOW() WHERE customer_id = ?";
                        ps4 = con.prepareStatement(query4);
                        ps4.setBigDecimal(1, updatedBalancecId2);
                        ps4.setInt(2, Integer.parseInt(cId2));
                        ps4.executeUpdate();

                        out.println("<font color='green'>Updated Successfully in Customers Database.</font>");

                        String query5 = "INSERT INTO Transactions(customer_id, description, amount, balance, transaction_time) VALUES (?, 'Debit', ?, ?, NOW())";
                        ps5 = con.prepareStatement(query5);
                        ps5.setInt(1, Integer.parseInt(cId1));
                        ps5.setBigDecimal(2, amount);
                        ps5.setBigDecimal(3, updatedBalancecId1);
                        ps5.executeUpdate();

                        String query6 = "INSERT INTO Transactions(customer_id, description, amount, balance, transaction_time) VALUES (?, 'Credit', ?, ?, NOW())";
                        ps6 = con.prepareStatement(query6);
                        ps6.setInt(1, Integer.parseInt(cId2));
                        ps6.setBigDecimal(2, amount);
                        ps6.setBigDecimal(3, updatedBalancecId2);
                        ps6.executeUpdate();

                        out.println("<font color='green'>Adding rows in transactions data.</font>");
                    } else {
                        out.println("<font color='red'>Insufficient balance.</font>");
                    }
                }
            } else {
                out.println("<font color='red'>Customer ID not found.</font>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (rs1 != null) rs1.close();
                if (rs2 != null) rs2.close();
                if (rs3 != null) rs3.close();
                if (rs4 != null) rs4.close();
                if (ps != null) ps.close();
                if (ps1 != null) ps1.close();
                if (ps2 != null) ps2.close();
                if (ps3 != null) ps3.close();
                if (ps4 != null) ps4.close();
                if (ps5 != null) ps5.close();
                if (ps6 != null) ps6.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
