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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ListCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Task", "root", "password");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            String query = "SELECT * FROM Customers WHERE LoginTime BETWEEN ? AND ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));

            ResultSet rs = ps.executeQuery();

            out.println("<h2>Customers added between " + startDateStr + " and " + endDateStr + "</h2>");

            while (rs.next()) {
                out.println("Customer ID: " + rs.getInt("customer_id") + "<br>");
                out.println("Name: " + rs.getString("full_name") + "<br>");
                out.println("Email: " + rs.getString("email") + "<br>");
                out.println("<hr>");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
