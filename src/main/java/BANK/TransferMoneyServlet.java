package BANK;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/TransferMoneyServlet")

public class TransferMoneyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve logged-in user details
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInAccount") == null) {
            out.println("<h3>Session expired. Please log in again.</h3>");
            return;
        }

        String senderAccountStr = (String) session.getAttribute("loggedInAccount");
        long senderAccount = Long.parseLong(senderAccountStr);
        String receiverAccountStr = request.getParameter("receiverAccount");
        String amountStr = request.getParameter("amount");

        if (receiverAccountStr == null || amountStr == null || receiverAccountStr.isEmpty() || amountStr.isEmpty()) {
            out.println("<h3>Please provide valid receiver account and amount.</h3>");
            return;
        }

        long receiverAccount = Long.parseLong(receiverAccountStr);
        double transferAmount = Double.parseDouble(amountStr);

        if (receiverAccount == senderAccount) {
            out.println("<h3>You cannot transfer money to your own account.</h3>");
            return;
        }

        try {
            String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
            String DB_USER = "c##samuel";
            String DB_PASS = "8499";

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // Check sender balance in account table
            String checkBalanceQuery = "SELECT balance FROM account WHERE cardnumber = ?";
            PreparedStatement checkBalanceStmt = con.prepareStatement(checkBalanceQuery);
            checkBalanceStmt.setLong(1, senderAccount);
            ResultSet rs = checkBalanceStmt.executeQuery();

            if (!rs.next()) {
                out.println("<h3>Sender account not found.</h3>");
                return;
            }

            double senderBalance = rs.getDouble("balance");
            if (senderBalance < transferAmount) {
                out.println("<h3>Insufficient balance.</h3>");
                return;
            }

            // Validate receiver account
            String validateReceiverQuery = "SELECT COUNT(*) FROM account WHERE cardnumber = ?";
            PreparedStatement validateReceiverStmt = con.prepareStatement(validateReceiverQuery);
            validateReceiverStmt.setLong(1, receiverAccount);
            ResultSet receiverRs = validateReceiverStmt.executeQuery();

            if (receiverRs.next() && receiverRs.getInt(1) == 0) {
                out.println("<h3>Receiver account not found.</h3>");
                return;
            }

            con.setAutoCommit(false); // Start transaction

            // Update sender's balance in both tables
            String deductAccountQuery = "UPDATE account SET balance = balance - ? WHERE cardnumber = ?";
            try (PreparedStatement deductAccountStmt = con.prepareStatement(deductAccountQuery)) {
                deductAccountStmt.setDouble(1, transferAmount);
                deductAccountStmt.setLong(2, senderAccount);
                deductAccountStmt.executeUpdate();
            }

            String deductSignupThreeQuery = "UPDATE signupthree SET balance = balance - ? WHERE cardnumber = ?";
            try (PreparedStatement deductSignupThreeStmt = con.prepareStatement(deductSignupThreeQuery)) {
                deductSignupThreeStmt.setDouble(1, transferAmount);
                deductSignupThreeStmt.setLong(2, senderAccount);
                deductSignupThreeStmt.executeUpdate();
            }

            // Update receiver's balance in both tables
            String addAccountQuery = "UPDATE account SET balance = balance + ? WHERE cardnumber = ?";
            try (PreparedStatement addAccountStmt = con.prepareStatement(addAccountQuery)) {
                addAccountStmt.setDouble(1, transferAmount);
                addAccountStmt.setLong(2, receiverAccount);
                addAccountStmt.executeUpdate();
            }

            String addSignupThreeQuery = "UPDATE signupthree SET balance = balance + ? WHERE cardnumber = ?";
            try (PreparedStatement addSignupThreeStmt = con.prepareStatement(addSignupThreeQuery)) {
                addSignupThreeStmt.setDouble(1, transferAmount);
                addSignupThreeStmt.setLong(2, receiverAccount);
                addSignupThreeStmt.executeUpdate();
            }

            // Log transaction in transaction_history table for sender
            String logTransactionQuery = "INSERT INTO transaction_history (cardnumber, transaction_type, amount, target_account) VALUES (?, ?, ?, ?)";
            try (PreparedStatement logTransactionStmt = con.prepareStatement(logTransactionQuery)) {
                logTransactionStmt.setLong(1, senderAccount);
                logTransactionStmt.setString(2, "Transfer");
                logTransactionStmt.setDouble(3, transferAmount);
                logTransactionStmt.setLong(4, receiverAccount);
                logTransactionStmt.executeUpdate();
            }

            // Log transaction for the receiver in transaction_history table
            String logReceiverTransactionQuery = "INSERT INTO transaction_history (cardnumber, transaction_type, amount, target_account) VALUES (?, ?, ?, ?)";
            try (PreparedStatement logReceiverTransactionStmt = con.prepareStatement(logReceiverTransactionQuery)) {
                logReceiverTransactionStmt.setLong(1, receiverAccount);
                logReceiverTransactionStmt.setString(2, "Received");
                logReceiverTransactionStmt.setDouble(3, transferAmount);
                logReceiverTransactionStmt.setLong(4, senderAccount);
                logReceiverTransactionStmt.executeUpdate();
            }

            con.commit(); // Commit the transaction
            out.println("<h3>Transaction successful. ₹" + transferAmount + " transferred to account " + receiverAccount + ".</h3>");
            out.println("<a href='Home.jsp'>Home</a>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Transaction failed: " + e.getMessage() + "</h3>");
        }
    }
}
