<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Adding a customer</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
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
    form {
        display: flex;
        flex-direction: column;
    }
    label {
        margin-bottom: 10px;
    }
    input[type="text"],
    input[type="number"],
    input[type="email"],
    input[type="tel"] {
        width: 100%;
        padding: 10px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        box-sizing: border-box;
    }
    input[type="submit"] {
        width: 100%;
        padding: 10px;
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }
    input[type="submit"]:hover {
        background-color: #0056b3;
    }
</style>
</head>
<body>
    <div class="container">
        <h1>ABC Bank - Registered Bank</h1>
        <h2>Add Customer</h2>
        <form action="AddCustomerServlet" method="post">
            <label for="usrId">Enter The UserId :</label>
            <input type="number" name="usrId" id="usrId">

            <label for="fullname">Enter The Full Name :</label>
            <input type="text" name="fullname" id="fullname">

            <label for="email">Enter The Email :</label>
            <input type="email" name="email" id="email">

            <label for="pno">Enter The Phone number :</label>
            <input type="tel" name="pno" id="pno">

            <label for="address">Enter The Address :</label>
            <input type="text" name="address" id="address">

            <label for="aadhar_number">Enter The Aadhar number :</label>
            <input type="text" name="aadhar_number" id="aadhar_number" pattern="[0-9]{12}">

            <label for="pan_number">Enter The Pan number :</label>
            <input type="text" name="pan_number" id="pan_number" pattern="[A-Z]{5}[0-9]{4}[A-Z]{1}">

            <label for="balance">Enter The Money to add :</label>
            <input type="text" name="balance" id="balance">

            <label for="calculate_interest">Calculate Interest:</label>
            <input type="checkbox" name="calculate_interest" id="calculate_interest" checked>

            <input type="submit" value="Create">
        </form>
    </div>
</body>
</html>
