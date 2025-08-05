import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/GetCartServlet")
public class GetCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try {
            HttpSession session = request.getSession(false);
            List<String> cart = session != null ? (List<String>) session.getAttribute("cart") : null;
            
            if (cart == null || cart.isEmpty()) {
                out.print("[]");
                return;
            }
            
            out.print("[");
            for (int i = 0; i < cart.size(); i++) {
                String[] parts = cart.get(i).split("\\|\\|", -1);
                out.print(String.format(
                    "{\"name\":\"%s\",\"price\":\"%s\",\"description\":\"%s\",\"image\":\"%s\"}",
                    escapeJson(parts[0]),
                    escapeJson(parts[1]),
                    escapeJson(parts[2]),
                    escapeJson(parts[3])
                ));
                if (i < cart.size() - 1) out.print(",");
            }
            out.print("]");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Error retrieving cart\"}");
        } finally {
            out.close();
        }
    }
    
    private String escapeJson(String input) {
        return input.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}