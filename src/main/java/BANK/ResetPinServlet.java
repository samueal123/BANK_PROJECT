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
import java.sql.ResultSet;
import java.sql.SQLException;

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
import java.sql.SQLException;

@WebServlet("/reset-pin")
public class ResetPinServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "c##samuel";
    private static final String DB_PASSWORD = "8499";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String cardNumber = request.getParameter("cardNumber");
        String currentPin = request.getParameter("currentPin");
        String newPin = request.getParameter("newPin");

        // Input Validation
        if (cardNumber == null || currentPin == null || newPin == null ||
            cardNumber.isEmpty() || currentPin.isEmpty() || newPin.isEmpty()) {
            out.println("<h3>Please provide card number, current PIN, and new PIN!</h3>");
            return;
        }

        if (currentPin.length() != 4 || newPin.length() != 4 || 
            !currentPin.matches("\\d{4}") || !newPin.matches("\\d{4}")) {
            out.println("<h3>PINS must be exactly 4 digits!</h3>");
            return;
        }

        Connection conn = null;
        PreparedStatement updateServicesStmt = null;
        PreparedStatement updateLoginStmt = null;

        try {
            // Load JDBC driver and establish connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            conn.setAutoCommit(false); // Disable auto-commit for transaction

            // Prepare SQL statements for both tables
            String updateServicesQuery = "UPDATE signupthree SET pin = ? WHERE cardnumber = ?";
            updateServicesStmt = conn.prepareStatement(updateServicesQuery);
            updateServicesStmt.setString(1, newPin);
            updateServicesStmt.setString(2, cardNumber);

            String updateLoginQuery = "UPDATE login SET pin = ? WHERE cardnumber = ?";
            updateLoginStmt = conn.prepareStatement(updateLoginQuery);
            updateLoginStmt.setString(1, newPin);
            updateLoginStmt.setString(2, cardNumber);

            // Execute updates for both tables
            int servicesRowsUpdated = updateServicesStmt.executeUpdate();
            int loginRowsUpdated = updateLoginStmt.executeUpdate();

            // If both updates were successful, commit the transaction
            if (servicesRowsUpdated > 0 && loginRowsUpdated > 0) {
                conn.commit(); // Commit transaction
                out.println("<h3>PIN reset successfully for both services and login!</h3>");
            } else {
                out.println("<h3>Failed to reset PIN. Please try again.</h3>");
                conn.rollback(); // Rollback transaction if no rows were updated
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error occurred: " + e.getMessage() + "</h3>");
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction on exception
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            // Close resources
            try {
                if (updateServicesStmt != null) updateServicesStmt.close();
                if (updateLoginStmt != null) updateLoginStmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Restore auto-commit
                    conn.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }
}
