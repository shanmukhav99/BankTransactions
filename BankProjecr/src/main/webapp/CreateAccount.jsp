<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create an Account</title>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-image: url('https://t3.ftcdn.net/jpg/04/84/36/98/360_F_484369865_uGrJURH8FtldtXF9C9SfGqgx2xj0oWWA.jpg'); 
        background-size: cover;
        background-position: center;
    }
    .container {
        max-width: 800px;
        margin: 50px auto;
        padding: 20px;
        background-color: #f4f4f4; 
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    h1 {
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
    input[type="password"] {
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
        <div align="center">
            <h1>ABC Bank - Registered Bank</h1>
        </div>
        <div align="center">
            <h2>Create account</h2>
        </div>
        <form action="CreateServlet" method="post">
            <label for="usrName">Enter The UserName :</label>
            <input type="text" name="usrName" id="usrName">
            <label for="pwd">Enter The Password :</label>
            <input type="password" name="pwd" id="pwd">
            <label for="role">Enter The Role :</label>
            <input type="text" name="role" id="role">
            <input type="submit" value="SignUp">
        </form>
    </div>
</body>
</html>
