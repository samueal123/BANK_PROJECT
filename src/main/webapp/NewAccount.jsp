<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account</title>
    <style>
        /* Center the form on the page */
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }

        form {
            background-color: #ffffff;
            padding: 15px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            width: 90%;
            box-sizing: border-box;
        }

        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr; /* Two columns for compact design */
            gap: 10px; /* Reduced gap for compactness */
        }

        .form-grid div {
            display: flex;
            flex-direction: column;
        }

        form label {
            font-size: 12px; /* Compact label size */
            font-weight: bold;
            margin-bottom: 3px;
        }

        form input, form select {
            padding: 6px; /* Smaller padding for fields */
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 12px; /* Compact input size */
        }

        form .full-width {
            grid-column: span 2; /* Span both columns for full-width fields */
        }

        form button {
            grid-column: span 2;
            background-color: #007BFF;
            color: white;
            border: none;
            cursor: pointer;
            padding: 10px;
            text-align: center;
            font-size: 14px;
            border-radius: 5px;
        }

        form button:hover {
            background-color: #0056b3;
        }

        small {
            font-size: 10px; /* Compact small text */
            color: #666;
        }
    </style>
</head>
<body>
    <form action="NewAccountServlet" method="post">
        <div class="form-grid">
            <div>
                <label>First Name:</label>
                <input type="text" name="firstname" required>
            </div>
            <div>
                <label>Last Name:</label>
                <input type="text" name="lastname" required>
            </div>
            <div>
                <label>Account Holder Name:</label>
                <input type="text" name="accountHolder" required>
            </div>
            <div>
                <label>Email Address:</label>
                <input type="email" name="email" required>
            </div>
            <div>
                <label>Phone Number:</label>
                <input type="text" name="phoneNumber" required>
            </div>
            <div>
                <label>Country:</label>
                <input type="text" name="country" required>
            </div>
            <div>
                <label>State:</label>
                <input type="text" name="state" required>
            </div>
            <div>
                <label>Account Type:</label>
                <select name="accountType" required>
                    <option value="Saving Account">Saving Account</option>
                    <option value="Fixed Deposit Account">Fixed Deposit Account</option>
                </select>
            </div>
            <div>
                <label>Card Number:</label>
                <input type="text" name="cardNumber" required>
            </div>
            <div>
                <label>PIN:</label>
                <input type="password" name="pin" required>
            </div>
            <div>
                <label>Date of Birth:</label>
                <input type="date" name="dob" required>
            </div>
            <div>
                <label>Gender:</label>
                <select name="gender" required>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                </select>
            </div>
            <div class="full-width">
                <label>Services:</label>
                <label><input type="checkbox" name="services" value="ATM Card"> ATM Card</label>
                <label><input type="checkbox" name="services" value="Internet Banking"> Internet Banking</label>
                <label><input type="checkbox" name="services" value="Mobile Banking"> Mobile Banking</label>
                <label><input type="checkbox" name="services" value="SMS Alerts"> SMS Alerts</label>
                <label><input type="checkbox" name="services" value="Cheque Book"> Cheque Book</label>
            </div>
            <div class="full-width">
                <label>Initial Deposit:</label>
                <input type="number" name="balance" min="5000" required>
                <small>(Minimum deposit is 5000)</small>
            </div>
            <button type="submit">Create Account</button>
        </div>
    </form>
</body>
</html>







