// From "Professional Java Server Programming", Patzer et al.,

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

// Import Java Libraries
import java.io.*;
import java.util.Enumeration;

@WebServlet(name = "attributeServlet", urlPatterns = {"/attribute"})
public class AttributeServlet extends HttpServlet
{
   String lifeCycleURL = "/attribute";
public void doGet (HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException
{
   String action = request.getParameter("action");
   if (action != null && action.equals("invalidate")) 
   {
      HttpSession session = request.getSession();
      session.invalidate();
      out.println("<html>");
      out.println("<head>");
      out.println(" <title>Session state</title>");
      out.println("</head>");
      out.println("");
      out.println("<body>");
      out.println("<p>Your session is now invalidated</P>");
      out.println("<a href=\"" + lifeCycleURL + "?action=newSession\">");
      out.println("Create new session</A>");

      out.println("</body>");
      out.println("</html>");
      out.close();
   }
   else 
   {
      HttpSession session = request.getSession();
      String name   = request.getParameter("attrib_name");
      String value  = request.getParameter("attrib_value");
      String profession = request.getParameter("attrib_profession");
      String remove = request.getParameter("attrib_remove");

      if (remove != null && remove.equals("on"))
      {
         session.removeAttribute(name);
      }
      else
      {
         if ((name != null && name.length() > 0) && (value != null && value.length() > 0) && (profession != null && profession.length() > 0))
         {
            session.setAttribute(name, value + " " + profession);
         }

      }

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();

      out.println("<html>");
      // no-cache lets the page reload by clicking on the reload link
      out.println("<meta http-equiv=\"Pragma\" content=\"no-cache\">");
      out.println("<head>");
      out.println(" <title>Session lifecycle</title>");
      out.println("</head>");
      out.println("");
      
      out.println("<body>");

      out.println("<h1><center>Session life cycle</center></h1>");
      out.print  ("<BR>Session status: ");
      if (session.isNew())
      {
         out.println ("New session.");
      }
      else
      {
         out.println ("Old session.");
      }

      out.println("<h1><center>Session attributes</center></h1>");

      out.println("Enter name, value, and profession of an attribute");


      String url = response.encodeURL("attribute");
      out.println("<form action=\"" + url + "\" method=\"GET\">");
      out.println(" Name: ");
      out.println(" <input type=\"text\" size=\"10\" name=\"attrib_name\">");

      out.println(" Value: ");
      out.println(" <input type=\"text\" size=\"10\" name=\"attrib_value\">");

      out.println(" Profession: ");
      out.println(" <input type=\"text\" size=\"10\" name=\"attrib_profession\">");

      out.println(" <br><input type=\"checkbox\" name=\"attrib_remove\">Remove");
      out.println(" <input type=\"submit\" name=\"update\" value=\"Update\">");
      out.println("</form>");
      out.println("<hr>");
      out.print  ("<br><br><a href=\"" + lifeCycleURL + "?action=invalidate\">");
      out.println("Invalidate the session</a>");
      out.println("Attributes in this session:");
      Enumeration e = session.getAttributeNames();
      while (e.hasMoreElements())
      {
         String att_name  = (String) e.nextElement();
         String att_combined = (String) session.getAttribute(att_name);
         String [] att_array = att_combined.split(" ");
         String att_value = att_array[0];
         String att_profession = att_array[1];
         out.print  ("<br><b>Name:</b> ");
         out.println(att_name);
         out.print  ("<br><b>Value:</b> ");
         out.println(att_value);
         out.print  ("<br><b>Profession:</b> ");
         out.println(att_profession);
      } //end while

      out.println("</body>");
      out.println("</html>");
      out.close();
   }
} // End doGet
} //End  SessionLifeCycle
