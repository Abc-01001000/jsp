<%@ page import="com.uwu.wdnmd.model.Blog" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Blogs</title>
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

      li {
        margin: 3vh;
        padding: 32px;
        min-height: 100px;

        border-radius: 10px;
        box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3);

        cursor: pointer;
      }

      li:hover {
        box-shadow: 3px 3px 8px rgba(0, 0, 0, 0.3);
      }
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
    <span>Blogs</span>

    <nav>
      <%-- TODO menu --%>
    </nav>

    <% if (session.getAttribute("username")!= null) { %>
      <span id="user-span">Hello, <%= session.getAttribute("username") %>!</span>
    <% } else { %>
      <jsp:include page="../components/login.jsp" />
    <% } %>
  </header>

  <main>
<%--    <jsp:include page="../components/blog.jsp" />--%>
  <ul id="blogs">
    <%

      // 获取 request 属性中的 blogs 列表
      ArrayList<Blog> blogs = (ArrayList<Blog>) request.getAttribute("blogs");
      if (blogs != null) {
        for (Blog blog : blogs) {
    %>
    <li>
      <h3><%= blog.blog_id %></h3>
      <h3><%= blog.title %></h3>
      <p>Author: <%= blog.author %></p>
      <p><%= blog.description %></p>
      <p><a href="<%= blog.url %>">Read more</a></p>
      <p>Views: <%= blog.view %>, Likes: <%= blog.likes %>, Stars: <%= blog.star %></p>
    </li>
    <%
      }
    } else {
    %>
    <li>No blogs available.</li>
    <%
      }
    %>
  </ul>
  </main>

  <footer>&#xa9; Copy Right 1234-5678</footer>
</body>
<%--  <script>--%>
<%--    const user = document.getElementById('user-span');--%>
<%--    const username = sessionStorage.getItem('username');--%>
<%--    const password = sessionStorage.getItem('password');--%>
<%--    const role = sessionStorage.getItem('role');--%>

<%--    async function toManager() {--%>
<%--        const response = await fetch('http://localhost:8080/login-servlet', {--%>
<%--            method: 'POST',--%>
<%--            headers: {--%>
<%--                'Content-Type': 'application/json'--%>
<%--            },--%>
<%--            body: JSON.stringify({--%>
<%--                'username': username,--%>
<%--                'password': password--%>
<%--            })--%>
<%--        });--%>
<%--    }--%>

<%--    user.addEventListener('click', () => {--%>
<%--      if (role === 'admin') {--%>
<%--        toManager();--%>
<%--      } else if (username != null && confirm('Logout?')) {--%>
<%--        sessionStorage.clear();--%>
<%--      }--%>
<%--      location.reload();--%>
<%--    });--%>
<%--  </script>--%>
</html>