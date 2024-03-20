<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Handling by customers</title>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-image: url('https://images.shiksha.com/mediadata/shikshaOnline/mailers/2021/naukri-learning/oct/27oct/What-is-Consumer-Banking.jpg');
        background-size: cover; 
        background-position: center; 
    }
    .container {
        max-width: 600px;
        margin: 50px auto;
        padding: 20px;
        background-color: rgba(255, 255, 255, 0.8); /* Semi-transparent white background to improve readability */
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    h1 {
        text-align: center;
        margin-bottom: 20px;
    }
    a {
        display: block;
        text-align: center;
        padding: 10px 20px;
        margin-bottom: 10px;
        background-color: pink;
        color: blank;
        text-decoration: none;
        border-radius: 5px;
        transition: background-color 0.3s;
    }
    a:hover {
        background-color: yellow;
    }
</style>
</head>
<body>

    <div class="container">
        <div align="center">
            <h1>ABC Bank - Registered Bank</h1>
        </div>
        <div align="center">
            <h2> Welcome Customers</h2>
        </div>

        <a href="lastLoggedInDetails.jsp">Last Login Time Of Customer</a>
        <a href="InternalBankTransaction.jsp">Internal bank transaction</a>
        <a href="addNomineeDetails.jsp">Add Nominee</a>
        <a href="editNomineeDetails.jsp">Edit Nominee</a>
        <a href="hotlistCard.jsp">Hotlist the Card</a>
        <a href="deleteNomineeDetails.jsp">Delete Nominee</a>
        <a href="AddCardDetails.jsp">Add Card Details</a>
        <a href="PDFGenerator.jsp">Generate the PDF Of Monthly Statement</a>
    </div>
</body>
</html>
