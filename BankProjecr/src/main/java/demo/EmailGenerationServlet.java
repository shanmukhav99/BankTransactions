package demo;

import javax.mail.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmailGenerationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cId = request.getParameter("cId");
        try {
            String subject = "Regarding to update the details";
            String from = "chinnu117@outlook.com";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Task", "root", "password");
            PreparedStatement ps = con.prepareStatement("SELECT adhar_number, pan_number FROM Customers WHERE customer_id = ?");
            ps.setInt(1, Integer.parseInt(cId));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String aNo = rs.getString("adhar_number");
                String pNo = rs.getString("pan_number");
                String text = "";

                if (aNo.isEmpty()) {
                    text = "Your Aadhar card details have not been updated! Please update it.";
                } else if (pNo.isEmpty()) {
                    text = "Your PAN card details have not been updated! Please update it.";
                } else if (aNo.isEmpty() && pNo.isEmpty()) {
                    // Both Aadhar and PAN are updated
                    text = "Your Aadhar card and PAN card details have not been updated!";
                }

                String to = "chinnu117@outlook.com";
                String host = "smtp.office365.com";
                Properties properties = System.getProperties();
                System.out.println("Properties : " + properties);
                properties.put("mail.smtp.host", host);
                properties.put("mail.smtp.port", "587");
                //properties.put("mail.smtp.ssl.enable","true");
                properties.put("mail.smtp.starttls.enable", "true");

                properties.put("mail.smtp.auth", "true");
                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, "1234chinnu");
                    }
                });
                session.setDebug(true);
                MimeMessage m = new MimeMessage(session);
                properties.put("mail.debug", "true");

                // Trust all certificates
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }

                            public void checkClientTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }

                            public void checkServerTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                        }
                };
                try {
                    SSLContext sc = SSLContext.getInstance("SSL");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    properties.put("mail.smtp.ssl.socketFactory", sc.getSocketFactory());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    m.setFrom(from);
                    m.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(to)));
                    m.setSubject(subject);
                    m.setText(text);
                    Transport.send(m);
                    System.out.println("Success .. . . . . .  ");
                    
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
