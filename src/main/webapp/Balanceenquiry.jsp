<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Styled ATM Form</title>
    <style>
        /* Reset body styles */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f4f4f9;
        }

        /* Centered form container */
        form {
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            padding: 30px;
            width: 300px;
            text-align: center;
        }

        /* Input field styling */
        input[type="text"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 15px 0;
            border: 1px solid #cccccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 16px;
        }

        /* Button styling */
        button {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 10px 0;
            margin: 10px 0;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <form action="BalanceEnquiryServlet" method="post">
    <label for="amount">Enter Card Number:</label>             
        <input type="text" name="cardNumber" placeholder="Card Number" required>
        <button type="submit">Check Balance</button>
    </form>
</body>
</html>

</html>