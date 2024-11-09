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

<ul id="blogs">
  <%
    ArrayList<Blog> blogs = (ArrayList<Blog>) request.getAttribute("blogs");
    String scope = (String) request.getAttribute("main");
    if (blogs != null && !blogs.isEmpty()) {
      for (Blog blog : blogs) {
  %>
        <li class="flex justify-around items-center">
          <div class="m-6 p-3 w-4/5 rounded-lg shadow-lg hover:shadow-xl hover:cursor-pointer" onclick="doSomething('<%= blog.blog_id %>')">
            <h1 class="text-2xl font-bold"><%= blog.title %></h1>
            <% if (!scope.equals("Profile")) { %>
              <p>Author: <%= blog.author %></p>
            <% } %>
            <p><%= blog.description %></p>
            <p>Views: <%= blog.view %>, Likes: <%= blog.likes %>, Stars: <%= blog.star %></p>
          </div>
          <% if (scope.equals("Manage") || scope.equals("Profile")) { %>
            <div class="flex justify-around w-1/5">
              <button class="red-btn" onclick="doDelete('<%= blog.blog_id %>')">&#x2192;</button>
            </div>
          <% } %>
        </li>
  <%
      }
    } else {
  %>
      <span>No blogs available.</span>
  <% } %>
</ul>

<script>
  function doSomething(id) {
    window.location.href = "/blog-content?blogId=" + id;
  }

  function doDelete(id) {
    if (!confirm('Are you sure?'))
      return;
    window.location.href = '/blog-delete?blogId=' + id;
  }
</script>

