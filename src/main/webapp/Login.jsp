<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        /* Apply full height to the page */
        body, html {
            height: 100%;
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }

        /* Create a flex container for centering */
        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100%;
            flex-direction: column; /* Stack the bank name and login box */
        }

        /* Normal static bank name outside the box */
        .bank-name-static {
            font-size: 50px;
            font-weight: bold;
            color: #4CAF50;
            margin-bottom: 30px; /* Space between bank name and login box */
            text-align: center;
        }

        /* Scrolling bank name inside the box */
        .login-box {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
            position: relative; /* Container for the scrolling text */
        }

        .login-box h2 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }

        /* Style the scrolling bank name inside the box */
        .scrolling-bank-name {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            font-size: 30px;
            font-weight: bold;
            color: #4CAF50;
            white-space: nowrap;
            overflow: hidden;
        }

        .scrolling-bank-name span {
            display: inline-block;
            animation: marquee 10s linear infinite;
        }

        @keyframes marquee {
            0% {
                transform: translateX(100%);
            }
            100% {
                transform: translateX(-100%);
            }
        }

        /* Style the input fields */
        .login-box input[type="text"], .login-box input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        /* Style the submit button */
        .login-box input[type="submit"], .login-box button {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px; /* Add gap between buttons */
        }

        .login-box input[type="submit"]:hover, .login-box button:hover {
            background-color: #45a049;
        }

        /* Add a little responsiveness */
        @media (max-width: 600px) {
            .login-box {
                width: 250px;
            }
        }
    </style>
</head>
<body>

    <div class="container">
        <!-- Static Bank Name Outside the Login Box -->
        <div class="bank-name-static">
            ASR Bank
        </div>

        <div class="login-box">
            <!-- Scrolling Bank Name Inside the Login Box -->
            <div class="scrolling-bank-name">
                <span>Welcome To ASR Bank Login Page</span>
            </div>

            <h2>Login</h2>
            <form action="LoginServlet" method="post">
                <input type="text" name="cardNumber" placeholder="Card Number" required><br>
                <input type="password" name="pin" placeholder="PIN" required><br>
                <input type="submit" value="Login" />
            </form>

            <!-- Add a margin-top for the Reset Pin and Create Account buttons -->
            <form action="ResetPin.jsp" method="get">
                <button type="submit">Reset Pin</button>
            </form>
            <form action="NewAccount.jsp" method="get">
                <button type="submit">Create New Account</button>
            </form>
        </div>
    </div>

</body>
</html>



    