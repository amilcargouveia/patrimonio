<%@page import="java.io.*"%>
<%@page import="javax.servlet.*"%>
<%@page import="javax.servlet.http.*"%>

<%!
  private void forward(String              path,
                       PageContext         pageContext) throws ServletException, IOException {
    pageContext.getServletContext().getRequestDispatcher("/" + path).forward(pageContext.getRequest(), pageContext.getResponse());
  }
%>
