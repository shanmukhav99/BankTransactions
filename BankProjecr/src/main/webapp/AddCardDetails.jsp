<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Card Details</title>
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
    input[type="date"] {
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
        <h2>Add Card Details</h2>
        <form action="AddCardServlet" method="post">
            <label for="cId">Enter The Customer Id :</label>
            <input type="number" name="cId" id="cId">

            <label for="cType">Enter The Card Type :</label>
            <input type="text" name="cType" id="cType">

            <label for="cStatus">Enter The Status :</label>
            <input type="text" name="cStatus" id="cStatus">

            <label for="edate">Enter The Expiration date :</label>
            <input type="date" name="edate" id="edate">

            <input type="submit" value="Add Card">
        </form>
    </div>
</body>
</html>
