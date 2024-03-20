<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Other Description Transactions </title>
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
        text-align: center;
    }
    h1 {
        margin-bottom: 20px;
    }
    form table {
        margin: 0 auto;
        text-align: left;
    }
    input[type="number"], input[type="text"], input[type="submit"] {
        padding: 10px 20px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        transition: border-color 0.3s;
    }
    input[type="number"]:focus, input[type="text"]:focus {
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
        <h2>Other Description Transactions</h2>
    </div>
    <form action="OtherDescriptionServlet" method="post">
        <table>
            <tr>
                <td>Enter The Customer ID : <input type="number" name="cId"></td>
            </tr>
            <tr>
                <td>Enter The Description : <input type="text" name="description"></td>
            </tr>
            <tr>
                <td>Enter The Amount : <input type="number" name="amount"></td>
            </tr>
            <tr>
                <td><input type="submit" value="Add Money"></td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>
