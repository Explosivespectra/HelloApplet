import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

// Import Java Libraries
import java.io.*;
import java.util.*;
import java.util.Enumeration;

@WebServlet(name = "predicateServletGet", urlPatterns = {"/predicateGet"})
public class PredicateGetServlet extends HttpServlet
{
    String lifeCycleURL = "/predicateGet";
    public void doGet (HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>SWE 432 Assignment 8 Jonah Oentung</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>SWE 432 Assignment 8 Jonah Oentung</h1>");
        out.println("<hr>");
        out.println("<hr>");
        out.println("<p><strong>This application accepts different variables and a predicate to return a truth table for a given predicate. Restrictions are defined as follows:</strong></p>");
        out.println("<p><strong><ul><li>Boolean operators that may be handled are OR and AND</li><li>Boolean operators may be represented in word, discrete, or computer logic form (or/and, OR/AND, &&/||, &/|, ^/v)</li><li>Parentheses are permitted</li><li>No more than five predicates may be accepted</li><li>Variables, boolean operators, and parantheses should be split apart by at least a single space</li><li>Variables may not consist of non-alphabetical or non-numerical characters or be boolean operand keywords</li><li>Maximum length of variable names is 15 characters</li></ul></strong></p>");
        out.println("<hr>");
        out.println("<hr>");
        out.println("<p>");
        //https://cs.gmu.edu:8443/offutt/servlet/formHandler replaced
        out.println("<form method=\"post\" action=\"https://helloserv.herokuapp.com/predicateGet\">");
        out.println("<label for=\"var1\">Variable 1</label><br>");
        out.println("<input type=\"text\" id=\"var1\" name=\"var1\" maxlength=\"15\"><br><br>");
        out.println("<label for=\"var2\">Variable 2</label><br>");
        out.println("<input type=\"text\" id=\"var2\" name=\"var2\" maxlength=\"15\"><br><br>");
        out.println("<label for=\"var3\">Variable 3</label><br>");
        out.println("<input type=\"text\" id=\"var3\" name=\"var3\" maxlength=\"15\"><br><br>");
        out.println("<label for=\"var4\">Variable 4</label><br>");
        out.println("<input type=\"text\" id=\"var4\" name=\"var4\" maxlength=\"15\"><br><br>");
        out.println("<label for=\"var5\">Variable 5</label><br>");
        out.println("<input type=\"text\" id=\"var5\" name=\"var5\" maxlength=\"15\"><br><br>");
        out.println("<label for=\"predicate\">Predicate</label><br>");
        out.println("<input type=\"text\" id=\"predicate\" name=\"predicate\"><br><br>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form");
        out.println("</p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
    public void doPost (HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
    {
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/predicatePost");
        requestDispatcher.forward(req, res);
    }
    
}