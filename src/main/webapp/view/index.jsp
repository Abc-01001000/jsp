<%@ page import="com.uwu.wdnmd.model.Blog" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>BlogHub</title>
  <link href="/static/output.css" rel="stylesheet">
</head>
<body>
  <header class="fixed top-0 z-index:10 flex justify-between align-items-center px-10 h-16 w-full shadow bg-gray-50 z-10">
    <%
      String currentContent = (String) request.getAttribute("main");
      if (currentContent == null) currentContent = "Blog";
    %>
    <span class="flex items-center text-2xl font-bold">
      <% if (!currentContent.equals("Blog")) { %>
        <button class="rounded-lg py-1 px-2 hover:bg-black hover:text-white" id="back-btn" onclick="back()">&lt;</button>
      <% } %>
      <%= currentContent %>
    </span>

    <nav>
      <%-- TODO menu --%>
    </nav>

    <span class="flex items-center">
      <%
        if (session.getAttribute("username") != null) {
          if (currentContent.equals("Profile") || currentContent.equals("Manage")) {
      %>
            <button class="red-btn" onclick="logout()">Logout</button>
      <%  } else { %>
            <span class="flex items-center py-1 px-2 rounded-lg text-2xl font-bold hover:bg-black hover:text-white hover:cursor-pointer" onclick="toProfile()">
              <%= "Hi, " + session.getAttribute("username") + "!" %>
            </span>
      <%
        }
      } else {
      %>
          <jsp:include page="../components/login.jsp" />
      <% } %>
    </span>
  </header>

  <main class="relative top-20 mx-auto w-3/4">
    <%
      switch (currentContent) {
        case "Blog" -> {
    %>
          <jsp:include page="../components/blog.jsp"/>
    <%
        } case "Content" -> {
    %>
          <jsp:include page="blogPage.jsp"/>
    <%
        } case "Profile" -> {
    %>
          <jsp:include page="profile.jsp"/>
    <%
        } case "Manage" -> {
    %>
          <jsp:include page="manage.jsp"/>
    <%
        } case "Error" -> {
    %>
          <jsp:include page="error.jsp"/>
    <%
        }
      }
    %>
  </main>

  <footer class="flex justify-around items-center mt-24 h-16 bg-gray-200 text-gray-700">&#xa9; Copy Right 1234-5678</footer>
</body>
  <script>
    function back() {
      window.history.back();
    }

    function toProfile() {
      console.log("<%= session.getAttribute("user_id") %>")
      window.location.href = "/profile?userId=" + "<%= session.getAttribute("user_id") %>";
    }

    function logout() {
      if (confirm('Are you sure you want to logout?')) {
        window.location.href = '/logout';
      }
    }
  </script>
</html>