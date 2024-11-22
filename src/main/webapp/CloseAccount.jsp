<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Close Account</title>
    <style>
        /* Overall page styles */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background: linear-gradient(135deg, #f4f4f9, #e6e6fa);
        }

        /* Form container styling */
        form {
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            padding: 30px;
            width: 350px;
            text-align: center;
        }

        /* Input field styling */
        input[type="text"] {
            width: calc(100% - 20px);
            padding: 12px;
            margin: 20px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 16px;
        }

        /* Button styling */
        button {
            width: 100%;
            background-color: #d9534f;
            color: white;
            padding: 12px 0;
            margin: 10px 0;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #c9302c;
        }

        /* Add a heading for better context */
        h2 {
            font-size: 24px;
            color: #333;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <form action="CloseAccountServlet" method="post">
        <h2>Close Account</h2>
        <input type="text" name="accountNumber" placeholder="Enter Account Number" required>
        <button type="submit">Close Account</button>
    </form>
</body>
</html>
