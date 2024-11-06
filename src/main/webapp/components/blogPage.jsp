<%--
  Created by IntelliJ IDEA.
  User: abc
  Date: 2024/11/1
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String content = (String) request.getAttribute("content");%>
<span>
  <%= content %>
</span>
