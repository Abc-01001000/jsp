<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Blogs</title>
  <link rel="stylesheet" type="text/css" href="main.css"></link>
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
      <jsp:include page="components/login.jsp" />
    <% } %>
  </header>

  <main>
    <jsp:include page="components/blog.jsp" />
  </main>

  <footer>&#xa9; Copy Right 1234-5678</footer>
</body>
  <script>
    <% if (session.getAttribute("username") != null) { %>
      const user = document.getElementById('user-span');

      user.addEventListener('click', () => {
        if (!confirm('logout?')) return;
        <%
          session = request.getSession(false);

          if (session!= null) {
            session.invalidate();
          }
        %>
        location.reload();
      });
    <% } %>
  </script>
</html>