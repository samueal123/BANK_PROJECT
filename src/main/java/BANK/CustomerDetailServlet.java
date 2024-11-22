package BANK;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/CustomerDetailsServlet")
public class CustomerDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the response content type to HTML
        response.setContentType("text/html");

        // Get the writer to send HTML content
        PrintWriter out = response.getWriter();

        // Retrieve the logged-in user's card number from the session
        HttpSession session = request.getSession(false); // Do not create a new session if one doesn't exist
        if (session == null) {
            // Display session expired message and redirect to login page
            out.println("<html><body>");
            out.println("<h2>Session has expired. Redirecting to login page...</h2>");
            out.println("</body></html>");
            response.setHeader("Refresh", "3; URL=Login.jsp"); // Redirect after 3 seconds
            return;
        }

        // Retrieve the card number from the session
        String cardNumber = (String) session.getAttribute("loggedInAccount");

        if (cardNumber == null || cardNumber.isEmpty()) {
            out.println("<html><body><h2>No account logged in. Please log in again.</h2></body></html>");
            response.setHeader("Refresh", "3; URL=Login.jsp"); // Redirect after 3 seconds
            return;
        }

        // Debugging: Output the card number
        System.out.println("Card Number from Session: " + cardNumber);

        // HTML structure to display customer details
        out.println("<html><head><title>Customer Details</title></head><body>");
        out.println("<h2>Customer Details</h2>");
        out.println("<table border='1' cellpadding='5'>");
        out.println("<tr>"
                + "<th>Account Number</th><th>First Name</th><th>Last Name</th><th>Full Name</th>"
                + "<th>Account Holder</th><th>Account Type</th><th>Card Number</th><th>PIN</th>"
                + "<th>Country</th><th>State</th><th>DOB</th><th>Gender</th>"
                + "<th>Email</th><th>Phone Number</th><th>Services</th><th>Balance</th>"
                + "</tr>");

        try (Connection conn = Conn.getConnection()) {
            // Query to fetch customer details using the card number
            String query = "SELECT accountnumber, firstname, lastname, fullname, accountholder, accounttype, "
                    + "cardnumber, pin, country, state, dob, gender, email, phonenumber, services, balance "
                    + "FROM signupthree WHERE cardnumber = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, cardNumber);
                ResultSet rs = ps.executeQuery();

                // Loop through the result set and display customer details
                if (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getString("accountnumber") + "</td>");
                    out.println("<td>" + rs.getString("firstname") + "</td>");
                    out.println("<td>" + rs.getString("lastname") + "</td>");
                    out.println("<td>" + rs.getString("fullname") + "</td>");
                    out.println("<td>" + rs.getString("accountholder") + "</td>");
                    out.println("<td>" + rs.getString("accounttype") + "</td>");
                    out.println("<td>" + rs.getString("cardnumber") + "</td>");
                    out.println("<td>" + rs.getString("pin") + "</td>");
                    out.println("<td>" + rs.getString("country") + "</td>");
                    out.println("<td>" + rs.getString("state") + "</td>");
                    out.println("<td>" + rs.getString("dob") + "</td>");
                    out.println("<td>" + rs.getString("gender") + "</td>");
                    out.println("<td>" + rs.getString("email") + "</td>");
                    out.println("<td>" + rs.getString("phonenumber") + "</td>");
                    out.println("<td>" + rs.getString("services") + "</td>");
                    out.println("<td>" + rs.getString("balance") + "</td>");
                    out.println("</tr>");
                } else {
                    out.println("<tr><td colspan='16'>No customer details found for this account.</td></tr>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<tr><td colspan='16'>Error fetching customer details.</td></tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
        out.println("<a href='Home.jsp'>Home</a>");
    }
}
