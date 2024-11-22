package BANK;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/TransactionStatementServlet")
public class Transaction_Statements extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the response content type to HTML
        response.setContentType("text/html");

        // Get the writer to send HTML content
        PrintWriter out = response.getWriter();

        // Retrieve the logged-in user's account number from the session
        HttpSession session = request.getSession(false);  // false means it won't create a new session if one doesn't exist
        if (session == null) {
            out.println("<html><body><h2>Session not found. Please log in again.</h2></body></html>");
            RequestDispatcher rd= request.getRequestDispatcher("Login.jsp");
            rd.forward(request, response);
            
            return;
        }

        // Retrieve the card number from the session
        String cardNumber = (String) session.getAttribute("loggedInAccount");

        // Debugging: Check if cardNumber is correctly retrieved
        if (cardNumber == null || cardNumber.isEmpty()) {
            out.println("<html><body><h2>No account logged in. Please log in again.</h2></body></html>");
            return;
        }

        // Debugging: Output the card number to the console
        out.println("Card Number from Session: " + cardNumber);

        // HTML structure to display transaction statement
        out.println("<html><head><title>Transaction Statement</title></head><body>");
        out.println("<h2>Your Transaction Statement</h2>");
        out.println("<table border='1' cellpadding='5'>");
        out.println("<tr><th>Transaction ID</th><th>Amount</th><th>Target Account</th><th>Transaction Type</th><th>Transaction Time</th></tr>");

        try (Connection conn = Conn.getConnection()) {
            // Query to fetch transaction details for the logged-in user
            String query = "SELECT * FROM transaction_history WHERE cardnumber = ? ORDER BY transaction_time DESC";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, cardNumber);
                ResultSet rs = ps.executeQuery();

                // Loop through result set and print each transaction
                while (rs.next()) {
                    String transactionId = rs.getString("id");
                    String amount = rs.getString("amount");
                    String targetAccount = rs.getString("target_account");
                    String transactionType = rs.getString("transaction_type");
                    String transactionTime = rs.getString("transaction_time");

                    // Add each transaction to the HTML table
                    out.println("<tr>");
                    out.println("<td>" + transactionId + "</td>");
                    out.println("<td>" + amount + "</td>");
                    out.println("<td>" + targetAccount + "</td>");
                    out.println("<td>" + transactionType + "</td>");
                    out.println("<td>" + transactionTime + "</td>");
                    out.println("</tr>");
                   
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<tr><td colspan='5'>Error fetching transaction statement.</td></tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
        out.println("<a href='Home.jsp'>Home</a>");
    }
}
