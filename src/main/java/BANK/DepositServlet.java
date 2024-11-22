package BANK;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cardNumber = request.getParameter("cardNumber");
        double amount = Double.parseDouble(request.getParameter("amount"));

        // Validate inputs
        if (amount <= 0) {
            response.getWriter().println("Invalid deposit amount.");
            return;
        }

        try (Connection conn = Conn.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // Fetch balance from `account` table
            String queryAccountBalance = "SELECT balance FROM account WHERE cardnumber = ?";
            try (PreparedStatement ps1 = conn.prepareStatement(queryAccountBalance)) {
                ps1.setString(1, cardNumber);
                ResultSet rs1 = ps1.executeQuery();

                if (!rs1.next()) {
                    response.getWriter().println("Card number not found in account table.");
                    conn.rollback();
                    return;
                }
                double accountBalance = rs1.getDouble("balance");

                // Fetch balance from `signupthree` table
                String querySignupThreeBalance = "SELECT balance FROM signupthree WHERE cardnumber = ?";
                try (PreparedStatement ps2 = conn.prepareStatement(querySignupThreeBalance)) {
                    ps2.setString(1, cardNumber);
                    ResultSet rs2 = ps2.executeQuery();

                    if (!rs2.next()) {
                        response.getWriter().println("Card number not found in signupthree table.");
                        conn.rollback();
                        return;
                    }
                    double signupThreeBalance = rs2.getDouble("balance");

                    // Update `account` balance
                    String updateAccount = "UPDATE account SET balance = ? WHERE cardnumber = ?";
                    try (PreparedStatement ps3 = conn.prepareStatement(updateAccount)) {
                        ps3.setDouble(1, accountBalance + amount);
                        ps3.setString(2, cardNumber);
                        ps3.executeUpdate();
                    }

                    // Update `signupthree` balance
                    String updateSignupThree = "UPDATE signupthree SET balance = ? WHERE cardnumber = ?";
                    try (PreparedStatement ps4 = conn.prepareStatement(updateSignupThree)) {
                        ps4.setDouble(1, signupThreeBalance + amount);
                        ps4.setString(2, cardNumber);
                        ps4.executeUpdate();
                    }

                    // Record transaction in `transaction_history`
                    String insertTransaction = "INSERT INTO transaction_history (cardnumber, transaction_type, amount,target_account) VALUES (?, ?,?, ?)";
                    try (PreparedStatement ps5 = conn.prepareStatement(insertTransaction)) {
                        ps5.setString(1, cardNumber);
                        ps5.setString(2, "Deposit");
            
                        ps5.setDouble(3, amount);
                        ps5.setString(4,"Bank/ATM");
                        ps5.executeUpdate();
                    }

                    conn.commit(); // Commit the transaction
                    response.getWriter().println("Deposit successful! New balance: " + (accountBalance + amount));
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Transaction failed: " + e.getMessage());
        }
    }
}


