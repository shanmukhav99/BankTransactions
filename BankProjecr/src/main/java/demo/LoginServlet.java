package demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String userId = request.getParameter("usrId");
        String password = request.getParameter("pwd");
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/Task","root","password");
            
            // Check user credentials
            ps = con.prepareStatement("SELECT * FROM Users WHERE user_id = ? AND password = ?");
            ps.setInt(1, Integer.parseInt(userId));
            ps.setString(2, password);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                // User found, retrieve role
                String roleQuery = "SELECT role FROM Users WHERE user_id = ?";
                PreparedStatement ps1 = con.prepareStatement(roleQuery);
                ps1.setInt(1, Integer.parseInt(userId));
                ResultSet rs1 = ps1.executeQuery();
                
                if (rs1.next()) {
                    String role = rs1.getString("role");
                    if ("Customer".equals(role)) {
                        RequestDispatcher rd = request.getRequestDispatcher("customer_main.jsp");
                        rd.forward(request, response);
                    } else if ("Manager".equals(role)) {
                        RequestDispatcher rd = request.getRequestDispatcher("manager_main.jsp");
                        rd.forward(request, response);
                    }
                }
                ps1.close();
                rs1.close();
            } else {
                out.println("<font color = red size =18 > Invalid Password or User ID <br>");
                out.println("<a href = LoginAccount.jsp> TRY AGAIN!! </a>");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace(out);
        } catch (SQLException e) {
            e.printStackTrace(out);
        } finally {
            // Close database resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace(out);
            }
        }
    }
}
