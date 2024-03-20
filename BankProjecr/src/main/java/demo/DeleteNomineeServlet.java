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
import java.sql.SQLException;

public class DeleteNomineeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
        String nId = request.getParameter("nId");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Task", "root", "password");
            PreparedStatement ps = con.prepareStatement("DELETE FROM Nominee WHERE nominee_id = ?");
            ps.setInt(1, Integer.parseInt(nId));
            int rowsDeleted = ps.executeUpdate();
            if(rowsDeleted>0) {
            	out.println("<font color='green' size='15'>Nominee Details has been deleted successfully</font><br>");
            }
            else {
            	out.println("<font color='red' size='15'>No nominee found with the nominee id</font><br>");
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
