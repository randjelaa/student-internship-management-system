<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 4/4/2026
  Time: 6:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="com.example.util.ApiClient" %>
<%@ page import="java.io.OutputStream" %>

<%
    try {
        byte[] pdf = ApiClient.getBytes("/cv/pdf/" + request.getParameter("studentId"), request);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=cv.pdf");

        OutputStream os = response.getOutputStream();
        os.write(pdf);
        os.flush();
        return;

    } catch (Exception e) {
        response.setContentType("text/plain");
        e.printStackTrace();
    }
%>
