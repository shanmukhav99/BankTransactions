package demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class PDFGenerationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cId = request.getParameter("cId");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Task", "root", "password");
            
            PreparedStatement ps2 = con.prepareStatement("SELECT description, amount FROM Transactions WHERE customer_id = ?");
            ps2.setInt(1, Integer.parseInt(cId));
            ResultSet rs2 = ps2.executeQuery();
            
            float debitAmount = 0.0f;
            float creditAmount = 0.0f;
            float chequeAmount = 0.0f;
            float unpaidChequeAmount = 0.0f;
            BigDecimal serviceFee = new BigDecimal("45.0"); // Corrected initialization
            while(rs2.next()) {
                String description = rs2.getString("description");
                BigDecimal amount = rs2.getBigDecimal("amount");
                
                if ("Debit".equals(description)) {
                    debitAmount += amount.floatValue();
                } else if ("Credit".equals(description)) {
                    creditAmount += amount.floatValue();
                } else if ("Cheque".equals(description)) {
                    chequeAmount += amount.floatValue();
                } else if ("Unpaid Cheques".equals(description)) {
                    unpaidChequeAmount += amount.floatValue();
                }
            }
            
            PreparedStatement ps3 = con.prepareStatement("SELECT balance FROM Customers WHERE customer_id = ?");
            ps3.setInt(1, Integer.parseInt(cId));
            ResultSet rs3 = ps3.executeQuery();
            BigDecimal remainingBalance = BigDecimal.ZERO;
            if(rs3.next()) {
                BigDecimal balance = rs3.getBigDecimal("balance");
                remainingBalance = balance.subtract(serviceFee);
            }
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Transactions WHERE customer_id = ?");
            ps.setInt(1, Integer.parseInt(cId));
            ResultSet rs = ps.executeQuery();
            
            PreparedStatement ps1 = con.prepareStatement("SELECT * FROM Customers WHERE customer_id = ?");
            ps1.setInt(1, Integer.parseInt(cId));
            ResultSet rs1 = ps1.executeQuery();
            

            if (rs.next() && rs1.next()) {
                try (PDDocument document = new PDDocument()) {
                    PDPage page = new PDPage();
                    document.addPage(page);
                    try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                        float margin = 50;
                        float yStart = page.getMediaBox().getHeight() - margin;
                        float yPosition = yStart;
                        
                        String title = "Monthly statements";
                        float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000 * 12; // Adjust font size if needed
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream.newLineAtOffset((page.getMediaBox().getWidth() - titleWidth) / 2, yPosition);
                        contentStream.showText(title);
                        contentStream.endText();
                        yPosition -= 20; 
                        
                        String bankName = "ABC Bank - Registered Bank";
                        float bankNameWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(bankName) / 1000 * 12;
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream.newLineAtOffset((page.getMediaBox().getWidth() - bankNameWidth) / 2, yPosition - 20);
                        contentStream.showText(bankName);
                        contentStream.endText();
                        yPosition -= 40; 

                        // Print customer details
                        float detailsXPosition = margin;
                        float detailsYPosition = yPosition; // Adjusted position for customer details

                        String customerName = rs1.getString("full_name");
                        String customerAddress = rs1.getString("address");
                        String customerEmail = rs1.getString("email");
                        String phoneNumber = rs1.getString("phone_number");
                        String adharNumber = rs1.getString("adhar_number");
                        String panNumber = rs1.getString("pan_number");
                        BigDecimal balance = rs1.getBigDecimal("balance");
                        java.sql.Timestamp loginTime = rs1.getTimestamp("LoginTime");

                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.beginText();
                        contentStream.newLineAtOffset(detailsXPosition, detailsYPosition);
                        contentStream.showText("Customer Name: " + customerName);
                        contentStream.endText();

                        detailsYPosition -= 20; // Move to the next line
                        contentStream.beginText();
                        contentStream.newLineAtOffset(detailsXPosition, detailsYPosition);
                        contentStream.showText("Customer Address: " + customerAddress);
                        contentStream.endText();

                        detailsYPosition -= 20; // Move to the next line
                        contentStream.beginText();
                        contentStream.newLineAtOffset(detailsXPosition, detailsYPosition);
                        contentStream.showText("Customer Email: " + customerEmail);
                        contentStream.endText();

                        detailsYPosition -= 20; // Move to the next line
                        contentStream.beginText();
                        contentStream.newLineAtOffset(detailsXPosition, detailsYPosition);
                        contentStream.showText("Phone Number: " + phoneNumber);
                        contentStream.endText();

                        detailsYPosition -= 20; // Move to the next line
                        contentStream.beginText();
                        contentStream.newLineAtOffset(detailsXPosition, detailsYPosition);
                        contentStream.showText("Aadhar Number: " + adharNumber);
                        contentStream.endText();

                        detailsYPosition -= 20; // Move to the next line
                        contentStream.beginText();
                        contentStream.newLineAtOffset(detailsXPosition, detailsYPosition);
                        contentStream.showText("PAN Number: " + panNumber);
                        contentStream.endText();

                        detailsYPosition -= 20; // Move to the next line
                        contentStream.beginText();
                        contentStream.newLineAtOffset(detailsXPosition, detailsYPosition);
                        contentStream.showText("Last Login Time: " + loginTime);
                        contentStream.endText();

                        

                        // Bank Statement period
                        String statementPeriod = "Bank Statement for the period 1 March 2024 to 31 March 2024";
                        float statementWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(statementPeriod) / 1000 * 12;
                        float statementYPosition = detailsYPosition - 40; // Adjusted y position for the bank statement
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream.newLineAtOffset((page.getMediaBox().getWidth() - statementWidth) / 2, statementYPosition);
                        contentStream.showText(statementPeriod);
                        contentStream.endText();

                        // Headers
                        String[] headers = {"Date", "Transaction Description", "Amount", "Balance"};
                        float[] columnWidths = {150, 250, 100, 100}; // Adjusted column widths

                        // Calculate the space needed for the transaction details
                        float transactionDetailsHeight = (rs.getFetchSize() + 1) * 30; // Total number of rows + 1 for headers

                        // Draw the headers row
                        float nextXPosition = margin;
                        float totalWidth = page.getMediaBox().getWidth() - 2 * margin;
                        yPosition -= transactionDetailsHeight + 175; // Adjusted position for the transaction details with a buffer space of 150 units

                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 9);
                        contentStream.setLineWidth(1f);

                        for (int i = 0; i < headers.length; i++) {
                            String header = headers[i];
                            float columnWidth = totalWidth * columnWidths[i] / 600; // Adjusted calculation for column width

                            contentStream.beginText();
                            contentStream.newLineAtOffset(nextXPosition + (columnWidth / 2) - (header.length() * 3), yPosition - 10);
                            contentStream.showText(header);
                            contentStream.endText();
                            contentStream.addRect(nextXPosition, yPosition - 15, columnWidth, 20);
                            contentStream.stroke();
                            nextXPosition += columnWidth;
                        }

                        // Data rows
                        do {
                            nextXPosition = margin;
                            yPosition -= 20;

                            String[] values = {
                                    rs.getString("transaction_time"),
                                    rs.getString("description"),
                                    String.valueOf(rs.getBigDecimal("amount")),
                                    String.valueOf(rs.getBigDecimal("balance"))
                            };

                            contentStream.setFont(PDType1Font.HELVETICA, 12);
                            for (int i = 0; i < values.length; i++) {
                                String value = values[i];
                                float columnWidth = totalWidth * columnWidths[i] / 600; // Adjusted calculation for column width

                                contentStream.beginText();
                                contentStream.newLineAtOffset(nextXPosition + (columnWidth / 2) - (value.length() * 3), yPosition - 10);
                                contentStream.showText(value);
                                contentStream.endText();
                                contentStream.addRect(nextXPosition, yPosition - 15, columnWidth, 20);
                                contentStream.stroke();
                                nextXPosition += columnWidth;
                            }
                            
                            
                        } while (rs.next());
                        
                        
                     // Display calculated amounts
                        detailsYPosition -= 300; // Move to the next line
                        contentStream.beginText();
                        contentStream.newLineAtOffset(detailsXPosition, detailsYPosition);
                        contentStream.showText("Debit Amount: " + debitAmount);
                        contentStream.endText();

                        detailsYPosition -= 20; // Move to the next line
                        contentStream.beginText();
                        contentStream.newLineAtOffset(detailsXPosition, detailsYPosition);
                        contentStream.showText("Credit Amount: " + creditAmount);
                        contentStream.endText();
                        
                        float depositAmount = creditAmount+debitAmount;
                        detailsYPosition -= 20; // Move to the next line
                        contentStream.beginText();
                        contentStream.newLineAtOffset(detailsXPosition, detailsYPosition);
                        contentStream.showText("Deposit Amount: " + depositAmount);
                        contentStream.endText();
                        
                        
                        detailsYPosition -= 20; // Move to the next line
                        contentStream.beginText();
                        contentStream.newLineAtOffset(detailsXPosition, detailsYPosition);
                        contentStream.showText("Cheque Amount: " + chequeAmount);
                        contentStream.endText();

                        detailsYPosition -= 20; // Move to the next line
                        contentStream.beginText();
                        contentStream.newLineAtOffset(detailsXPosition, detailsYPosition);
                        contentStream.showText("Unpaid Cheque Amount: " + unpaidChequeAmount);
                        contentStream.endText();
                        
                        detailsYPosition -= 20; // Move to the next line
                        contentStream.beginText();
                        contentStream.newLineAtOffset(detailsXPosition, detailsYPosition);
                        contentStream.showText("Service Fee Amount: " + serviceFee);
                        contentStream.endText();

                        detailsYPosition -= 20; // Move to the next line
                        contentStream.beginText();
                        contentStream.newLineAtOffset(detailsXPosition, detailsYPosition);
                        contentStream.showText("Remaining Balance: " + remainingBalance);
                        contentStream.endText();
                        
                        // Important Notice
                        String noticeHeader = "Important Notice";
                        float noticeHeaderWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(noticeHeader) / 1000 * 12;
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream.newLineAtOffset((page.getMediaBox().getWidth() - noticeHeaderWidth) / 2, detailsYPosition - 20);
                        contentStream.showText(noticeHeader);
                        contentStream.endText();
                        detailsYPosition -= 20;

                        // Notice Text
                        String notice = "Statements are accepted as correct unless queried within 30 days. Any cheques reflected on this statement, which are not attached, will be included with your next statement.";
                        String[] noticeLines = notice.split("(?<=\\G.{87})"); 
                        for (String line : noticeLines) {
                            detailsYPosition -= 20; // Move to the next line
                            contentStream.beginText();
                            contentStream.newLineAtOffset(detailsXPosition, detailsYPosition);
                            contentStream.showText(line);
                            contentStream.endText();
                        }

                        // Close the content stream
                        contentStream.close();
                    }

                    document.save(response.getOutputStream());
                } catch (Exception e) {
                    throw new ServletException("Exception while generating PDF", e);
                }
            }

            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
