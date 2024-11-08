<%--
  Created by IntelliJ IDEA.
  User: abc
  Date: 2024/10/27
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<span class="flex justify-between">
  <div class="w-1/6 border-r-2">
    <div id="manage-user" class="normal-menu-item" onclick="manageUser()">USER</div>
    <div id="manage-blog" class="normal-menu-item" onclick="manageBlog()">BLOG</div>
  </div>

  <div class="w-4/5">
    <%
      String manageScope = (String) request.getAttribute("manageScope");
      manageScope = manageScope == null? "blog" : manageScope;
      if (manageScope.equals("user")) {
    %>
        <jsp:include page="../components/user.jsp"/>
    <%
      } else if (manageScope.equals("blog")) {
    %>
      <jsp:include page="../components/blog.jsp"/>
    <% } %>
  </div>
</span>

<script>
  function manageUser() {
    window.location.href = '/users';
  }

  function manageBlog() {
    window.location.href = '/blogs';
  }
</script>