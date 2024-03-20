package demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class EditCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String cId = request.getParameter("cId");
        String mail = request.getParameter("mail");
        String pno = request.getParameter("pno");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Task", "root", "password");
            String query = "SELECT * FROM Customers WHERE customer_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(cId));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (mail != null && !mail.isEmpty()) {
                    String updateEmailQuery = "UPDATE Customers SET email = ?, LoginTime = NOW() WHERE customer_id = ?";
                    PreparedStatement ps1 = con.prepareStatement(updateEmailQuery);
                    ps1.setString(1, mail);
                    ps1.setInt(2, Integer.parseInt(cId));
                    ps1.executeUpdate();
                    ps1.close();
                }
                if (pno != null && !pno.isEmpty()) {
                    String updatePhoneQuery = "UPDATE Customers SET phone_number = ?, LoginTime = NOW() WHERE customer_id = ?";
                    PreparedStatement ps2 = con.prepareStatement(updatePhoneQuery);
                    ps2.setString(1, pno);
                    ps2.setInt(2, Integer.parseInt(cId));
                    ps2.executeUpdate();
                    ps2.close();
                }
                out.println("<font color='green' size='18'>Update Success</font><br>");
            } else {
                out.println("<font color='red' size='18'>Customer ID doesn't exist</font><br>");
                out.println("<a href='editCustomer.jsp'>TRY AGAIN</a>");
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
