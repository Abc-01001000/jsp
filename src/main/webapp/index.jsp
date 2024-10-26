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
      <%-- menu --%>
    </nav>

    <button>Login</button>
  </header>

  <main>
    <jsp:include page="components/blog.jsp" flush="true" />
  </main>

  <footer></footer>
</body>
</html>