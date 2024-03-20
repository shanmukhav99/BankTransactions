<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit the Customer</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
    }
    .container {
        max-width: 700px; /* Adjusted max-width for better alignment */
        margin: 50px auto;
        padding: 20px;
        background-color: #fff;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        text-align: center;
    }
    h1 {
        margin-bottom: 20px;
    }
    form table {
        margin: 0 auto;
        text-align: left;
    }
    input[type="number"], input[type="email"], input[type="tel"], input[type="submit"] {
        width: 100%; /* Set input fields to full width */
        padding: 10px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        transition: border-color 0.3s;
        box-sizing: border-box; /* Include padding in width calculation */
    }
    input[type="number"]:focus, input[type="email"]:focus, input[type="tel"]:focus {
        outline: none;
        border-color: #007bff;
    }
    input[type="submit"] {
        background-color: #007bff;
        color: #fff;
        cursor: pointer;
        transition: background-color 0.3s;
    }
    input[type="submit"]:hover {
        background-color: #0056b3;
    }
</style>
</head>
<body>

<div class="container">
    <div>
        <h1>ABC Bank - Registered Bank</h1>
    </div>
    <div>
        <h2>Edit Customer Account</h2>
    </div>
    <form action="EditCustomerServlet" method="post">
        <table>
            <tr>
                <td>Enter The Customer Id :</td>
                <td><input type="number" name="cId"></td>
            </tr>
            <tr>
                <td>Enter The Email :</td>
                <td><input type="email" name="mail"></td>
            </tr>
            <tr>
                <td>Enter The Phone number :</td>
                <td><input type="tel" name="pno"></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Edit"></td> <!-- Adjusted colspan for the submit button -->
            </tr>
        </table>
    </form>
</div>

</body>
</html>
