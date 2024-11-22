package BANK;



import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Retrieve the existing session
        if (session != null) {
            session.invalidate(); // Invalidate the session
        }
        response.sendRedirect("Login.jsp"); // Redirect to login page after logout
    }
}


