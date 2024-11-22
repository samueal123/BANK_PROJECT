package BANK;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/NewAccountServlet")
public class NewAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Collect data from the form
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String fullname = firstname + " " + lastname; // Concatenate for fullname
        String accountHolder = request.getParameter("accountHolder");
        String accountType = request.getParameter("accountType");
        String cardNumber = request.getParameter("cardNumber");
        String pin = request.getParameter("pin");
        String country = request.getParameter("country");
        String state = request.getParameter("state");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String[] services = request.getParameterValues("services");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        
        // Get the balance from the form and check for null/empty
        String balanceStr = request.getParameter("balance");
        double balance = 0;
        if (balanceStr != null && !balanceStr.trim().isEmpty()) {
            try {
                balance = Double.parseDouble(balanceStr);
            } catch (NumberFormatException e) {
                response.getWriter().println("Error: Invalid balance input.");
                return;
            }
        } else {
            response.getWriter().println("Error: Balance is required and cannot be empty.");
            return;
        }

        // Validate balance (minimum 5000)
        if (balance < 5000) {
            response.getWriter().println("Error: Minimum deposit amount is 5000.");
            return;
        }

        String servicesString = services != null ? String.join(", ", services) : "";
        String accountNumber = Long.toString(System.currentTimeMillis()).substring(6); // Generate account number

        try (Connection conn = Conn.getConnection()) {
            if (conn != null) {
                conn.setAutoCommit(false);

                try (PreparedStatement ps1 = conn.prepareStatement(
                            "INSERT INTO login (cardNumber, pin) VALUES (?, ?)");
                     PreparedStatement ps2 = conn.prepareStatement(
                            "INSERT INTO signupthree (accountNumber, firstname, lastname, fullname, accountHolder, accountType, cardNumber, pin, country, state, dob, gender, email, phoneNumber, services, balance) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, ?,?)");
                     PreparedStatement ps3 = conn.prepareStatement(
                            "INSERT INTO account (cardNumber, balance) VALUES (?, ?)")) {

                    // Insert login details
                    ps1.setString(1, cardNumber);
                    ps1.setString(2, pin);

                    // Insert account details
                    ps2.setLong(1, Long.parseLong(accountNumber));
                    ps2.setString(2, firstname);
                    ps2.setString(3, lastname);
                    ps2.setString(4, fullname);
                    ps2.setString(5, accountHolder);
                    ps2.setString(6, accountType);
                    ps2.setString(7, cardNumber);
                    ps2.setString(8, pin);
                    ps2.setString(9, country);
                    ps2.setString(10, state);
                    ps2.setString(11, dob);
                    ps2.setString(12, gender);
                    ps2.setString(13, email);
                    ps2.setString(14, phoneNumber);
                    ps2.setString(15, servicesString);
                    ps2.setDouble(16, balance); // Insert balance into signupthree

                    // Insert balance into account table
                    ps3.setString(1, cardNumber);
                    ps3.setDouble(2, balance);

                    // Execute queries
                    ps1.executeUpdate();
                    ps2.executeUpdate();
                    ps3.executeUpdate();

                    conn.commit(); // Commit transaction
                    response.getWriter().println("Account created successfully with an initial deposit of " + balance + "!");
                } catch (SQLException e) {
                    conn.rollback();
                    response.getWriter().println("Error: " + e.getMessage());
                }
            } else {
                response.getWriter().println("Error: Database connection failed.");
            }
        } catch (SQLException e) {
            response.getWriter().println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

