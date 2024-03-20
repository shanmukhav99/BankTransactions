<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List Customers</title>
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
    input[type="date"], input[type="submit"] {
        padding: 10px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        transition: border-color 0.3s;
    }
    input[type="date"]:focus {
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
        <h2>List customers added for the given time</h2>
    </div>
    <form action="ListCustomerServlet" method="post">
        <table>
            <tr>
                <td>Start Date:</td>
                <td><input type="date" name="startDate"></td>
            </tr>
            <tr>
                <td>End Date:</td>
                <td><input type="date" name="endDate"></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="List Customers"></td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>
