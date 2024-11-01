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
      <span onclick="toProfile()" id="user-span">Hello, <%= session.getAttribute("username") %>!</span>
    <% } else { %>
      <jsp:include page="../components/login.jsp" />
    <% } %>
  </header>

  <main>
    <jsp:include page="../components/blog.jsp" />
  </main>

  <footer>&#xa9; Copy Right 1234-5678</footer>
</body>
  <script>
    async function toProfile() {
      try {
        const response = await fetch('/profile', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            username: sessionStorage.getItem('username'),
            password: sessionStorage.getItem('password'),
          }),
          credentials: 'include'
        });

        if (response.ok) {
          console.log(response);
        } else {
          window.location.href = '/';
        }
      } catch (err) {
        console.log(err);
      }
    }

    function logout() {
      if (confirm('Are you sure you want to logout?')) {
        window.location.href = '/logout';
      }
    }
  </script>
</html>