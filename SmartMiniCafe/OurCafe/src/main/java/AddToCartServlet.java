import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try {
            String itemName = request.getParameter("itemName");
            String itemPrice = request.getParameter("itemPrice");
            
            if (itemName == null || itemName.isEmpty() || itemPrice == null || itemPrice.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Item name and price are required\"}");
                return;
            }
            
            String itemDescription = request.getParameter("itemDescription");
            String itemImage = request.getParameter("itemImage");
            
            HttpSession session = request.getSession(true);
            List<String> cart = (List<String>) session.getAttribute("cart");
            
            if (cart == null) {
                cart = new ArrayList<>();
                session.setAttribute("cart", cart);
            }
            
            String itemDetails = String.join("||", 
                itemName,
                itemPrice,
                itemDescription != null ? itemDescription : "",
                itemImage != null ? itemImage : ""
            );
            
            cart.add(itemDetails);
            
            out.print("{\"status\":\"success\", \"cartSize\":" + cart.size() + "}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Server error\"}");
        } finally {
            out.close();
        }
    }
}