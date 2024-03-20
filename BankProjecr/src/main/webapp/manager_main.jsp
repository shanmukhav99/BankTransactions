<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Handling by managers</title>

    <style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
        background-image: url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRPNZWkdWS6DvtG0crB2XJmeA47qA96oXCyh0Q3yTT9hCWdKazBnFq8OtlYd8QAn_XYYs0&usqp=CAU');
        background-size: cover; 
        background-position: center; 
    }
    .container {
        max-width: 600px;
        margin: 50px auto;
        padding: 20px;
        background-color: #fff;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    h1,h2 {
        text-align: center;
        margin-bottom: 20px;
    }
    a {
        display: block;
        text-align: center;
        padding: 10px 20px;
        margin-bottom: 10px;
        background-color: pink;
        color: black;
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
    <div>
        <h1>ABC Bank - Registered Bank</h1>
    </div>
    <div>
        <h2>Welcome Managers</h2>
    </div>

    <a href="add_customer.jsp">Add Customer</a>
    <a href="editCustomer.jsp">Edit Customer</a>
    <a href="deleteCustomer.jsp">Delete Customer</a>
    <a href="lastLoggedInDetails.jsp">Last Login Time Of Customer</a>
    <a href="AddCardDetails.jsp">Add Card Details</a>
    <a href="ListCustomersTime.jsp">List The Customers for the given Time</a>
    <a href="hotlistCard.jsp">Hotlist the Customer</a>
    <a href="CalculateInterest.jsp">Calculate the Interest</a>
    <a href="OtherDescriptionTransactions.jsp">Other Description Transactions</a>
    <a href="PDFGenerator.jsp">Generate the PDF Of Monthly Statement</a>
    <a href="EmailGeneration.jsp">Email Generation</a>
</div>

</body>
</html>
