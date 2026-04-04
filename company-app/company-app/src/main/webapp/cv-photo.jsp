<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 4/4/2026
  Time: 6:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.util.ApiClient" %>
<%@ page import="java.io.OutputStream" %>

<%
    try {
        String studentId = request.getParameter("studentId");

        byte[] image = ApiClient.getBytes("/cv/photo/" + studentId, request);

        response.setContentType("image/*");
        OutputStream os = response.getOutputStream();
        os.write(image);
        os.flush();
        return;

    } catch (Exception e) {
        e.printStackTrace();
    }
%>
