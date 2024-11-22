package BANK;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String cardNumber = request.getParameter("cardNumber");
        String pin = request.getParameter("pin");

        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM login WHERE cardNumber = ? AND pin = ?")) {

            ps.setString(1, cardNumber);
            ps.setString(2, pin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Create a new session if not exists
                HttpSession session = request.getSession(true); // true will create a new session if it doesn't exist
                session.setAttribute("loggedInAccount", cardNumber); // Store the card number or account info
                response.sendRedirect("Home.jsp"); // Redirect to the home page after successful login
            } else {
                response.getWriter().println("<h3>Invalid login credentials. Please try again.</h3>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>An error occurred while processing your request. Please try again later.</h3>");
        }
    }
}





