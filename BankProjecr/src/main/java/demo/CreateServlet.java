package demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String userName = request.getParameter("usrName");
        String password = request.getParameter("pwd");
        String role = request.getParameter("role");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Task", "root", "password");
            if (validatePassword(password)) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO Users(username, password, role, last_logged_in) VALUES (?, ?, ?, ?)");
                ps.setString(1, userName);
                ps.setString(2, password);
                ps.setString(3, role);
                ps.setTimestamp(4, new Timestamp(System.currentTimeMillis())); // Corrected index
                ps.executeUpdate();
                ps.close();
                con.close();
                out.println("<font color='green' size='18'>Account Created</font><br>");
            } else {
                out.println("<font color='red' size='18'>Password should have at least 1 Uppercase, 1 Lowercase, 1 special, and 1 numerical character and minimum length of 7.</font><br>");
                out.println("<a href='CreateAccount.jsp'>TRY AGAIN!!</a>");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean validatePassword(String password) {
        int countUpper = 0;
        int countLower = 0;
        int countSpecial = 0;
        int countNumeric = 0;

        if (password.length() >= 7) {
            for (int i = 0; i < password.length(); i++) {
                char ch = password.charAt(i);
                if (Character.isUpperCase(ch)) {
                    countUpper++;
                } else if (Character.isLowerCase(ch)) {
                    countLower++;
                } else if (Character.isDigit(ch)) {
                    countNumeric++;
                } else {
                    countSpecial++;
                }
            }
            return (countUpper >= 1 && countLower >= 1 && countNumeric >= 1 && countSpecial >= 1);
        }
        return false;
    }
}
