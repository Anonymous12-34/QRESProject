package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class keyword_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("         <title>Garbled Circuit TOR AWS-Cloud</title>\n");
      out.write("        <script type=\"text/javascript\">\n");
      out.write("            window.onpageshow = function (event) {\n");
      out.write("                if (event.persisted) {\n");
      out.write("                    window.location.reload()\n");
      out.write("                }\n");
      out.write("            };\n");
      out.write("            window.onload = function () {\n");
      out.write("                document.getElementById(\"textbox\").value = \"\";\n");
      out.write("            }\n");
      out.write("            function validateForm() {\n");
      out.write("                var x = document.getElementById(\"textbox\").value.toString().trim();\n");
      out.write("                if (x == \"\") {\n");
      out.write("                    alert(\"Write Message and Click button\");\n");
      out.write("                    return false;\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("        </script>\n");
      out.write("    </head>\n");
      out.write("    <body>        \n");
      out.write("        <form name=\"keyword\" action=\"searchKeyword\" method=\"post\" onsubmit=\"return validateForm()\">\n");
      out.write("            <h4>Search Keyword in Cloud Database</h4>\n");
      out.write("            <input type=\"text\" id=\"keyword\" name=\"keyword\" size=\"20\"/>\n");
      out.write("            <br><br>\n");
      out.write("            <input type=\"submit\" value=\"Search\">\n");
      out.write("        </form>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
