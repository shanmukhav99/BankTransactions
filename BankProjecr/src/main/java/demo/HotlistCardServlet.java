package demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class HotlistCardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String cId = request.getParameter("cId");
        String reason = request.getParameter("reason");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Task", "root", "password");
            
            if(reason.equals("Stolen") || reason.equals("Fraud") || reason.equals("Breach")) {
                String query = "UPDATE Cards SET status = 'hotlisted' where card_id = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(cId));
                ps.executeUpdate();
                ps.close();
                con.close();
                out.println("<font color='green'>Hotlist the card Success!</font>");
            }
            else if(reason.equals("Expiry")) {
                String query1 = "SELECT expiration_date FROM Cards WHERE card_id = ?";
                PreparedStatement ps1 = con.prepareStatement(query1);
                ps1.setInt(1, Integer.parseInt(cId));
                ResultSet rs = ps1.executeQuery();

                if(rs.next()) {
                    Date expirationDate = rs.getDate("expiration_date");
                    LocalDate today = LocalDate.now();
                    LocalDate expirationLocalDate = expirationDate.toLocalDate();
                    
                    if(expirationLocalDate.isBefore(today)) {
                        String query2 = "UPDATE Cards SET status = 'hotlisted' WHERE card_id = ?";
                        PreparedStatement ps2 = con.prepareStatement(query2);
                        ps2.setInt(1, Integer.parseInt(cId));
                        ps2.executeUpdate();
                        ps2.close();
                        out.println("<font color='green'>Hotlist the card Success!</font>");
                    } else {
                        out.println("<font color='red'>Card cannot be hotlisted as it is not expired yet!</font>");
                    }
                } else {
                    out.println("<font color='red'>Card ID not found!</font>");
                }

                rs.close();
                ps1.close();
            }
            else {
                out.println("<font color='red'>You can't hotlist the card without proper reason!</font>");
            }
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
