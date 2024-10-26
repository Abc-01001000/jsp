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
  <style>
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
  </style>
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
        // TODO details contents description
        const li = document.createElement('li');
        li.innerHTML = '<h1>' + item.id + ' - ' + item.title + '</h1><h2>' + item.author + '</h2>';
        ul.appendChild(li);
      })
    } catch (error) {
      console.log(error);
    }
  }
</script>
</html>
