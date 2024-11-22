package BANK;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();  // Getting the PrintWriter object
        
        String cardNumber = request.getParameter("cardNumber");
        double amount = Double.parseDouble(request.getParameter("amount"));

        // Validate inputs (optional)
        if (amount <= 0) {
            out.println("<h3>Invalid withdrawal amount.</h3>");
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
                    out.println("<h3>Card number not found in account table.</h3>");
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
                        out.println("<h3>Card number not found in signupthree table.</h3>");
                        out.println("<h3>Insufficient balance.</h3>");
                        out.println("<html><body>");
                       
                        out.println("<form action='Home.jsp' method='get'>");
                        out.println("<button type='submit'>Go to Home</button>");
                        out.println("</form>");
                        out.println("</body></html>");
                        conn.rollback();
                        return;
                    }
                    double signupThreeBalance = rs2.getDouble("balance");

                    // Check sufficient balance
                    if (accountBalance < amount || signupThreeBalance < amount) {
                        out.println("<h3>Insufficient balance.</h3>");
                        out.println("<html><body>");
                       
                        out.println("<form action='Home.jsp' method='get'>");
                        out.println("<button type='submit'>Go to Home</button>");
                        out.println("</form>");
                        out.println("</body></html>");
                        conn.rollback();
                        return;
                    }

                    // Update `account` balance
                    String updateAccount = "UPDATE account SET balance = ? WHERE cardnumber = ?";
                    try (PreparedStatement ps3 = conn.prepareStatement(updateAccount)) {
                        ps3.setDouble(1, accountBalance - amount);
                        ps3.setString(2, cardNumber);
                        ps3.executeUpdate();
                    }

                    // Update `signupthree` balance
                    String updateSignupThree = "UPDATE signupthree SET balance = ? WHERE cardnumber = ?";
                    try (PreparedStatement ps4 = conn.prepareStatement(updateSignupThree)) {
                        ps4.setDouble(1, signupThreeBalance - amount);
                        ps4.setString(2, cardNumber);
                        ps4.executeUpdate();
                    }

                    // Log transaction in transaction_history table (withdrawal)
                    String logWithdrawalQuery = "INSERT INTO transaction_history (cardnumber, transaction_type, amount, target_account) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement logStmt = conn.prepareStatement(logWithdrawalQuery)) {
                        logStmt.setString(1, cardNumber);
                        logStmt.setString(2, "Withdrawal");
                        logStmt.setDouble(3, amount);
                        logStmt.setString(4, "ATM/BANK");
                        logStmt.executeUpdate();
                    }

                    conn.commit(); // Commit the transaction

                    // Send successful response with "Go to Home" button
                    out.println("<html><body>");
                    out.println("<h2>Withdrawal successful!</h2>");
                    out.println("<p>New balance: " + (accountBalance - amount) + "</p>");
                    out.println("<form action='Home.jsp' method='get'>");
                    out.println("<button type='submit'>Go to Home</button>");
                    out.println("</form>");
                    out.println("</body></html>");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Transaction failed: " + e.getMessage() + "</h3>");
        }
    }
}


