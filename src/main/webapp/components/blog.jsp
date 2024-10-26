<%--
  Created by IntelliJ IDEA.
  User: abc
  Date: 2024/10/26
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
  <ul id="blogs"></ul>
</body>
<script>
  window.onload = async () => {
    const ul = document.getElementById('blogs');

    try {
      const url = "blog-servlet";
      const response = await fetch(url);
      console.log(response);

      const data = await response.json();
      console.log(data);

      data.forEach(item => {
        const li = document.createElement('li');
        li.innerText = item.title;
        ul.appendChild(li);
      })
    } catch (error) {
      console.log(error);
    }
  }
</script>
</html>
