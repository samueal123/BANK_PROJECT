<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Deposit Money</title>
    <style>
        /* Global Reset */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        /* Body styling */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        /* Container styling */
        .container {
            background-color: #fff;
            padding: 30px;
            width: 100%;
            max-width: 450px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        /* Heading styling */
        h2 {
            font-size: 1.8rem;
            text-align: center;
            color: #333;
            margin-bottom: 25px;
        }

        /* Input Group */
        .input-group {
            margin-bottom: 20px;
        }

        /* Label styling */
        label {
            font-weight: bold;
            font-size: 1rem;
            color: #555;
            margin-bottom: 5px;
            display: block;
        }

        /* Input styling */
        input {
            width: 100%;
            padding: 12px;
            font-size: 1rem;
            border: 1px solid #ddd;
            border-radius: 5px;
            margin-top: 5px;
        }

        /* Button styling */
        button {
            width: 100%;
            padding: 12px;
            font-size: 1.1rem;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        /* Button hover effect */
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Deposit Money</h2>
        <form action="DepositServlet" method="post">
            <div class="input-group">
                <label for="cardNumber">Card Number:</label>
                <input type="text" id="cardNumber" name="cardNumber" placeholder="Enter your Card Number" required>
            </div>

            <div class="input-group">
                <label for="amount">Amount:</label>
                <input type="number" id="amount" name="amount" placeholder="Enter the Amount" required>
            </div>

            <button type="submit">Deposit</button>
        </form>
    </div>
</body>
</html>
