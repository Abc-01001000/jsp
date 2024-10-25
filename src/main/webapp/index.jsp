<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
    <script>
        window.onload = async () => {
            const url = "http://localhost:8080/test";
            const response = await fetch(url);
            console.log(response);

            const data = await response.json();
            console.log(data);
        }

    </script>
</html>