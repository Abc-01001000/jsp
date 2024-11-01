<%--
  Created by IntelliJ IDEA.
  User: abc
  Date: 2024/10/26
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.uwu.wdnmd.model.Blog" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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

