import javax.servlet.*;
import javax.servlet.http.*;
import javax.smartcardio.ResponseAPDU;
import javax.servlet.annotation.WebServlet;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

// Import Java Libraries
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Enumeration;

@WebServlet(name = "predicateServletPost", urlPatterns = {"/predicatePost"})
public class PredicatePostServlet extends HttpServlet
{
    String lifeCycleURL = "/predicatePost";
    public void doGet (HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>SWE 432 Assignment 5 Jonah Oentung</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>SWE 432 Assignment 5 Jonah Oentung</h1>");
        out.println("<hr>");
        out.println("<hr>");    
        out.println("<p>");
        out.println("Servlet does not accept GET requests");
        out.println("</p>");
        out.println("</body>");
        out.println("</html>");
        out.close();       
    }
    public void doPost (HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
    {
        String var1 = req.getParameter("var1").trim();
        String var2 = req.getParameter("var2").trim();
        String var3 = req.getParameter("var3").trim();
        String var4 = req.getParameter("var4").trim();
        String var5 = req.getParameter("var5").trim();
        String predicate = req.getParameter("predicate").trim().replaceAll("\\s+", " ");
        ArrayList<String> varList = new ArrayList<String>();
        ArrayList<ArrayList<String>> allowed = new ArrayList<ArrayList<String>>();
        varList.add(var1);
        varList.add(var2);
        varList.add(var3);
        varList.add(var4);
        varList.add(var5);
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>SWE 432 Assignment 5 Jonah Oentung</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>SWE 432 Assignment 5 Jonah Oentung</h1>");
        out.println("<hr>");
        out.println("<hr>");      
        Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        for (String s : varList) {
            ArrayList<String> currArrList = new ArrayList<String>();
            if (!s.replace(" ", "").equals("")) {
                Matcher m = p.matcher(s);
                if (s.length() > 15 || m.find() || s.toLowerCase().equals("or") || s.toLowerCase().equals("and")) {
                    out.println("<p>");
                    out.println("Invalid variables submitted");
                    out.println("</p>");
                    out.println("</body>");
                    out.println("</html>");
                    out.close();
                    return;
                }
                //&& !predicate.substring(0, s.length()).equals(s) && !predicate.substring(predicate.length() - s.length(), predicate.length()).equals(s)
                Pattern x = Pattern.compile("\\b" + s + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher y = x.matcher(predicate);
                if (!y.find()) {
                    out.println("<p>");
                    out.println("Predicate missing one or more variables described in the submission");
                    out.println("</p>");
                    out.println("</body>");
                    out.println("</html>");
                    out.close();
                    return;
                }
                currArrList.add("true");
                currArrList.add("false");
            }
            else {
                currArrList.add("");
            }
            allowed.add(currArrList);
        }
        ArrayList<ArrayList<String>> sum = new ArrayList<ArrayList<String>>();
        ArrayList<String> comb = new ArrayList<String>();
        combine(allowed, 0, sum, comb);
        ArrayList<String> results = new ArrayList<String>();
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("JavaScript");
        String adjustedPredicate = predicate.replaceAll("\\b" + Pattern.quote("&") + "\\b", "&&").replaceAll("\\b" + Pattern.quote("|") + "\\b", "||").replaceAll("(?i)\\b" + Pattern.quote("or") + "\\b", "||").replaceAll("\\b" + Pattern.quote("OR") + "\\b", "||").replaceAll("(?i)\\b" + Pattern.quote("AND") + "\\b", "&&").replaceAll("\\b" + Pattern.quote("and") + "\\b", "&&").replaceAll("\\b" + Pattern.quote("v") + "\\b", "||").replaceAll("\\b" + Pattern.quote("^") + "\\b", "&&");
        for (ArrayList<String> a : sum) {
            String changedPredicate = new String(adjustedPredicate);
            if (!a.get(0).equals("")) {
                changedPredicate = changedPredicate.replaceAll("\\b" + var1 + "\\b", a.get(0));
            }
            if (!a.get(1).equals("")) {
                changedPredicate = changedPredicate.replaceAll("\\b" + var2 + "\\b", a.get(1));
            }
            if (!a.get(2).equals("")) {
                changedPredicate = changedPredicate.replaceAll("\\b" + var3 + "\\b", a.get(2));
            }
            if (!a.get(3).equals("")) {
                changedPredicate = changedPredicate.replaceAll("\\b" + var4 + "\\b", a.get(3));
            }
            if (!a.get(4).equals("")) {
                changedPredicate = changedPredicate.replaceAll("\\b" + var5 + "\\b", a.get(4));
            }
            String result;
            try {
                result = "" + se.eval(changedPredicate).toString().replace("1", "true").replace("0", "false");
                results.add(result);
            }
            catch (ScriptException s) {
                out.println("<p>");
                out.println("Invalid predicate");
                out.println("</p>");
                out.println("</body>");
                out.println("</html>");
                return;
            }
        }
        out.println("<p><strong>" + predicate + "</strong></p>");
        out.println("<table style=\"border:1px;\" border=\"1\">");
        out.println("<tr>");
        out.println("<th>" + var1 + "</th>");
        out.println("<th>" + var2 + "</th>");
        out.println("<th>" + var3 + "</th>");
        out.println("<th>" + var4 + "</th>");
        out.println("<th>" + var5 + "</th>");
        out.println("<th>result</th>");
        out.println("</tr");
        for (int i = 0; i < results.size(); i++) {
            out.println("<tr>");
            out.println("<td>" + sum.get(i).get(0) + "</td>");
            out.println("<td>" + sum.get(i).get(1) + "</td>");
            out.println("<td>" + sum.get(i).get(2) + "</td>");
            out.println("<td>" + sum.get(i).get(3) + "</td>");
            out.println("<td>" + sum.get(i).get(4) + "</td>");
            out.println("<td>" + results.get(i) + "</td>");
            out.println("</tr");
        }
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
    private void combine(ArrayList<ArrayList<String>> lists, int n, ArrayList<ArrayList<String>> sum, ArrayList<String> comb) {
        if (n == lists.size()) {
            sum.add(new ArrayList<String>(comb));
            return;
        }
        for (String s: lists.get(n)) {
            comb.add(s);
            combine(lists, n + 1, sum, comb);
            comb.remove(comb.size() - 1);
        }
    }
}    