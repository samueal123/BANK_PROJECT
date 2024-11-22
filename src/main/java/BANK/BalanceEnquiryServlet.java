package BANK;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/BalanceEnquiryServlet")
public class BalanceEnquiryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String cardNumber = request.getParameter("cardNumber");

        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT balance FROM account WHERE cardNumber = ?")) {

            ps.setString(1, cardNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double balance = rs.getDouble("balance");

                    response.getWriter().println("<html><body>");
                    response.getWriter().println("<h3>Your current balance is: " + balance + "</h3>");
                    response.getWriter().println("<form action='Home.jsp' method='get'>");
                    response.getWriter().println("<button type='submit'>Go to Home</button>");
                    response.getWriter().println("</form>");
                    response.getWriter().println("</body></html>");
                } else {
                    response.getWriter().println("<html><body>");
                    response.getWriter().println("<h3>Account not found. Please check your card number.</h3>");
                    response.getWriter().println("<form action='Home.jsp' method='get'>");
                    response.getWriter().println("<button type='submit'>Go to Home</button>");
                    response.getWriter().println("</form>");
                    response.getWriter().println("</body></html>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h3>An error occurred while retrieving your balance. Please try again later.</h3>");
            response.getWriter().println("</body></html>");
        }
    }
}
