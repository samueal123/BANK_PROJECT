package BANK;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/CloseAccountServlet")
public class CloseAccountServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "c##samuel";
    private static final String DB_PASSWORD = "8499";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            conn.setAutoCommit(false);

            // Delete from signupthree
            String deleteSignupThreeSQL = "DELETE FROM signupthree WHERE cardnumber = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSignupThreeSQL)) {
                pstmt.setString(1, accountNumber);
                pstmt.executeUpdate();
            }

            // Delete from account
            String deleteAccountSQL = "DELETE FROM account WHERE cardnumber = ?";
            int rowsAffected;
            try (PreparedStatement pstmt = conn.prepareStatement(deleteAccountSQL)) {
                pstmt.setString(1, accountNumber);
                rowsAffected = pstmt.executeUpdate();
            }

            conn.commit();

            // Generate response
            if (rowsAffected > 0) {
                out.println("<html><head><title>Account Closed</title></head><body>");
                out.println("<h3 style='color:green;'>Account " + accountNumber + " has been successfully closed.</h3>");
                out.println("<a href='Login.jsp'>Login Page</a>");
                out.println("</body></html>");
            } else {
                out.println("<html><head><title>Error</title></head><body>");
                out.println("<h3 style='color:red;'>Account " + accountNumber + " does not exist.</h3>");
                out.println("<a href='Login.jsp'>Try Again</a>");
                out.println("</body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace(out);
            out.println("<html><head><title>Error</title></head><body>");
            out.println("<h3 style='color:red;'>An error occurred while closing the account. Please try again later.</h3>");
            out.println("<a href='Home.jsp'>Go back to Home</a>");
            out.println("</body></html>");
        }
    }
}
