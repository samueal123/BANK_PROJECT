<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATM Interface</title>
    <style>
        /* Reset body styles */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        /* Container for the entire interface */
        .container {
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            padding: 30px;
            width: 600px;
            text-align: center;
        }

        /* Main title */
        .container h2 {
            margin-bottom: 20px;
            color: #333333;
        }

        /* Grid container for the forms */
        .form-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
        }

        /* Input fields styling */
        input[type="text"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 10px;
            box-sizing: border-box;
            border: 1px solid #cccccc;
            border-radius: 5px;
        }

        /* Buttons styling */
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
    <div class="container">
        <h2>Welcome to the ATM</h2>

        <div class="form-grid">
            
            
             <form action="Balanceenquiry.jsp" method="get">
                <button type="submit">Check Balance</button>
            </form>

            <form action="CustomerDetailsServlet" method="get">
                <button type="submit">Customer Details</button>
            </form>

            <form action="Withdraw.jsp" method="get">
                <button type="submit">Withdraw Money</button>
            </form>

            <form action="Deposite.jsp" method="get">
                <button type="submit">Deposit Money</button>
            </form>

            <form action="Money_Transfer.jsp" method="get">
                <button type="submit">Transfer Money</button>
            </form>

            <form action="TransactionStatementServlet" method="get">
                <button type="submit">Transaction History</button>
            </form>

            <form action="CloseAccount.jsp" method="get">
                <button type="submit">Delete Account</button>
            </form>

            <form action="LogoutServlet" method="post">
                <button type="submit">Logout</button>
            </form>
            
            <form action="Contact.jsp" method="get">
                <button type="submit">Bank Contact Details</button>
            </form>
        </div>
    </div>
</body>
</html>

