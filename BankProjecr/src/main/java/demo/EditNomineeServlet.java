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

public class EditNomineeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String nId = request.getParameter("nId");
        String nomineeName = request.getParameter("nName");
        String relationship = request.getParameter("rship");
        String cNo = request.getParameter("cno");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Task", "root", "password");
            
            // Check if the nominee with the provided ID exists
            String selectQuery = "SELECT * FROM Nominee WHERE nominee_id = ?";
            PreparedStatement psSelect = con.prepareStatement(selectQuery);
            psSelect.setInt(1, Integer.parseInt(nId));
            ResultSet rs = psSelect.executeQuery();
            
            if (rs.next()) {
                // Update nominee details
                String updateQuery = "UPDATE Nominee SET nominee_name = ?, relationship = ?, contact_no = ? WHERE nominee_id = ?";
                PreparedStatement psUpdate = con.prepareStatement(updateQuery);
                psUpdate.setString(1, nomineeName);
                psUpdate.setString(2, relationship);
                psUpdate.setString(3, cNo);
                psUpdate.setInt(4, Integer.parseInt(nId));
                
                int rowsUpdated = psUpdate.executeUpdate();
                if (rowsUpdated > 0) {
                    out.println("<font color='green'>Nominee Details Updated successfully!</font>");
                } else {
                    out.println("<font color='red'>Failed to update nominee details.</font>");
                }
                
                psUpdate.close();
            } else {
                out.println("<font color='red'>No Nominee found!</font>");
            }
            
            psSelect.close();
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
