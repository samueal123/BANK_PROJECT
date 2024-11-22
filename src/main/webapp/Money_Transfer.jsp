<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transfer Money</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        form {
            background-color: #ffffff;
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }
        h3 {
            margin-top: 0;
            font-size: 1.5rem;
            text-align: center;
            color: #333;
        }
        label {
            font-weight: bold;
            margin-bottom: 5px;
            display: block;
        }
        input {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: white;
            font-size: 1rem;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <!-- Transfer Money Form -->
    <form action="TransferMoneyServlet" method="post">
        <h3>Transfer Money</h3>
        <label for="senderAccount">Sender Account:</label>
        <input type="text" id="senderAccount" name="senderAccount" placeholder="Enter Sender Card Number" required>

        <label for="receiverAccount">Receiver Account:</label>
        <input type="text" id="receiverAccount" name="receiverAccount" placeholder="Enter Receiver Card Number" required>

        <label for="amount">Amount:</label>
        <input type="number" id="amount" name="amount" placeholder="Enter Amount" required>

        <button type="submit">Transfer</button>
    </form>
</body>
</html>
