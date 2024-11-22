package BANK;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/ContactUsServlet")
public class ContactServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve form parameters
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String atmLocation = request.getParameter("atmLocation");
        String problemDescription = request.getParameter("problemDescription");

        // Retrieve card number from session
        HttpSession session = request.getSession(false); // Get the session, do not create a new one
        String cardNumber = null;
        if (session != null) {
            cardNumber = (String) session.getAttribute("loggedInAccount");
        }

        if (cardNumber == null || cardNumber.isEmpty()) {
            out.println("<html><body>");
            out.println("<h2>Session expired or not logged in. Please log in again.</h2>");
            out.println("<a href='Login.jsp'>Go to Login</a>");
            out.println("</body></html>");
            return;
        }

        // Validate that all fields are not empty
        if (fullName == null || fullName.isEmpty() ||
            email == null || email.isEmpty() ||
            atmLocation == null || atmLocation.isEmpty() ||
            problemDescription == null || problemDescription.isEmpty()) {

            out.println("<html><body>");
            out.println("<h2>All fields are required. Please go back and fill the form completely.</h2>");
            out.println("<a href='ContactUs.jsp'>Go Back to Contact Form</a>");
            out.println("</body></html>");
            return;
        }

        // Database insertion
        try (Connection conn = Conn.getConnection()) {
            String query = "INSERT INTO atm_issues (card_number, full_name, email, atm_location, problem_description) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, cardNumber);
                ps.setString(2, fullName);
                ps.setString(3, email);
                ps.setString(4, atmLocation);
                ps.setString(5, problemDescription);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    out.println("<html><body>");
                    out.println("<h2>Thank you for reporting the issue, " + fullName + ". Our team will contact you shortly.</h2>");
                    out.println("<a href='Home.jsp'>Go Back to Home</a>");
                    out.println("</body></html>");
                } else {
                    out.println("<html><body>");
                    out.println("<h2>There was an issue submitting your request. Please try again later.</h2>");
                    out.println("<a href='ContactUs.jsp'>Go Back to Contact Form</a>");
                    out.println("</body></html>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h2>There was an error processing your request. Please try again later.</h2>");
            out.println("<a href='ContactUs.jsp'>Go Back to Contact Form</a>");
            out.println("</body></html>");
        }
    }
}

