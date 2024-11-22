<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Us for ATM Issues</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            min-height: 100vh;
        }

        .container {
            display: flex;
            width: 80%;
            justify-content: space-between;
            margin-top: 50px;
        }

        /* Left Section - Bank Details */
        .bank-details {
            width: 35%;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            text-align: left;
        }

        .bank-details h2 {
            color: #4CAF50;
        }

        .bank-details p {
            font-size: 16px;
            color: #333;
        }

        /* Right Section - Contact Form */
        .contact-form {
            width: 60%;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 30px;
        }

        .contact-form h2 {
            color: #4CAF50;
            text-align: center;
            margin-bottom: 20px;
        }

        .contact-form label {
            font-weight: bold;
            display: block;
            margin-top: 10px;
        }

        .contact-form input[type="text"], .contact-form input[type="email"], .contact-form textarea {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .contact-form textarea {
            resize: vertical;
            height: 100px;
        }

        .contact-form button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
        }

        .contact-form button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

    <div class="container">
        <!-- Left Side - Bank Details -->
        <div class="bank-details">
            <h2>Bank Information</h2>
            <p><strong>Bank Name:</strong> ASR Bank</p>
            <p><strong>Branch:</strong> KUKATPALLY,HYDERABAD,TG</p>
            <p><strong>Customer Support:</strong> avallasamuel123@gmail.com</p>
            <p><strong>Contact Number:</strong> +918499814662,+916281238488</p>
        </div>

        <!-- Right Side - Contact Form -->
        <div class="contact-form">
            <h2>Contact Us for ATM Issues</h2>
            <form action="ContactUsServlet" method="post">
                <label for="fullName">Full Name:</label>
                <input type="text" id="fullName" name="fullName" placeholder="Enter your full name" required>

                <label for="email">Email Address:</label>
                <input type="email" id="email" name="email" placeholder="Enter your email" required>

                <label for="atmLocation">ATM Location:</label>
                <input type="text" id="atmLocation" name="atmLocation" placeholder="ATM Location (Branch or Area)" required>

                <label for="problemDescription">Describe the Problem:</label>
                <textarea id="problemDescription" name="problemDescription" placeholder="Explain the problem you're facing with the ATM" required></textarea>

                <button type="submit">Submit Issue</button>
            </form>
        </div>
    </div>

</body>
</html>

