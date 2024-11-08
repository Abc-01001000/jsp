<%--
  Created by IntelliJ IDEA.
  User: abc
  Date: 2024/10/27
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1> <%= session.getAttribute("username") %> </h1>

<hr />

<jsp:include page="/src/components/blog.jsp"/>
