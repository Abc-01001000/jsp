<%@ page import="com.uwu.wdnmd.model.Blog" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>BlogHub</title>
  <style>
    * {
      margin: 0;
      padding: 0;
      text-decoration: none;
      list-style: none;
    }

    header {
      position: fixed;
      top: 0;
      z-index: 10;

      display: flex;
      justify-content: space-between;
      align-items: center;

      width: 100%;
      height: 10vh;

      background: #eee;
      box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.3);

      #backBtn {
        padding: 0 5px;

        border-radius: 5px;
        border: none;

        font-weight: bold;
        font-size: x-large;
        cursor: pointer;
      }

      #backBtn:hover {
        color: white;
        background: black;
      }

      span {
        margin: 0 5vh;

        font-weight: bold;
        font-size: x-large;
      }

      nav {
        display: flex;
        justify-content: space-between;
        align-items: center;
      }
    }

    main {
      position: relative;
      top: 10vh;

      width: 80%;
      margin: 5vh auto;
      margin-bottom: 15vh;
    }

    footer {
      display: flex;
      justify-content: space-around;
      align-items: center;

      width: 100%;
      height: 10vh;

      background: #eee;
      color: #666;
    }
  </style>
</head>
<body>
  <header>
    <%
      String currentContent = (String) request.getAttribute("main");
      currentContent = currentContent == null ? "Blog" : currentContent;
    %>
    <span>
      <% if (!currentContent.equals("Blog")) { %>
        <button id="backBtn" onclick="back()">&lt;</button>
      <% } %>
      <%= currentContent %>
    </span>

    <nav>
      <%-- TODO menu --%>
    </nav>
    <% if (session.getAttribute("username")!= null) { %>
      <span onclick="toProfile()" id="user-span">
        <%= currentContent.equals("Profile") ? "" : "Hi, " + session.getAttribute("username") + "!" %>
      </span>
    <% } else { %>
      <jsp:include page="../components/login.jsp" />
    <% } %>
  </header>

  <main>
    <%
      switch (currentContent) {
        case "Blog" -> {
    %>
          <jsp:include page="../components/blog.jsp"/>
    <%
        } case "Content" -> {
    %>
          <jsp:include page="../components/blogPage.jsp"/>
    <%
        } case "Profile" -> {
    %>
          <jsp:include page="../components/profile.jsp"/>
    <%
        } case "Manage" -> {
    %>
          <jsp:include page="../components/manage.jsp"/>
    <%
        }
      } %>
  </main>

  <footer>&#xa9; Copy Right 1234-5678</footer>
</body>
  <script>
    function back() {
      window.history.back();
    }

    async function toProfile() {
      window.location.href = "/profile";
    }

    function logout() {
      if (confirm('Are you sure you want to logout?')) {
        window.location.href = '/logout';
      }
    }
  </script>
</html>