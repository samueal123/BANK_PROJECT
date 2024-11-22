<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset PIN</title>
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
        }

        /* Style the reset form box */
        .reset-box {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }

        .reset-box h2 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }

        /* Style the input fields */
        .reset-box input[type="text"], .reset-box input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        /* Style the submit button */
        .reset-box button {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        .reset-box button:hover {
            background-color: #45a049;
        }

        /* Add a little responsiveness */
        @media (max-width: 600px) {
            .reset-box {
                width: 250px;
            }
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="reset-box">
            <h2>Reset Your PIN</h2>
            <form action="reset-pin" method="post">
                <label for="cardNumber">Card Number:</label><br>
                <input type="text" id="cardNumber" name="cardNumber" maxlength="16" required><br><br>

                <label for="currentPin">Current PIN:</label><br>
                <input type="password" id="currentPin" name="currentPin" maxlength="4" required><br><br>

                <label for="newPin">New PIN:</label><br>
                <input type="password" id="newPin" name="newPin" maxlength="4" required><br><br>

                <button type="submit">Reset PIN</button>
            </form>
        </div>
    </div>

</body>
</html>

    