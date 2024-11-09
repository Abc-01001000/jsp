<%@ page import="com.uwu.wdnmd.model.Blog" %><%--
  Created by IntelliJ IDEA.
  User: abc
  Date: 2024/11/1
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  String content = (String) request.getAttribute("content");
  content = content.replace("\n", "<br>");
  Blog blog = (Blog) request.getAttribute("blog");
%>
<div>
  <% if (blog.author_id == Integer.parseInt(session.getAttribute("user_id").toString())) { %>
    <button class="px-2 py-1 rounded-lg hover:bg-black hover:text-white" onclick="edit()"> Edit </button>
  <dialog id="edit-blog-dialog" class="rounded-2xl">
    <form
        action="http://localhost:8080/blog-edit"
        method="POST"
        class="p-6 rounded-2xl shadow-2xl"
    >
      <input type="hidden" name="blog_id" value="<%= blog.blog_id %>">

      <div class="normal-input">
        <label for="title">标题</label>
        <input class="w-full" type="text" id="title" name="title" value="<%= blog.title %>" required><br><br>
      </div>

      <div class="normal-input">
        <label for="description">描述</label>
        <input class="w-full" type="text" id="description" name="description" value="<%= blog.description %>" required><br><br>
      </div>

      <div class="normal-input">
        <label for="content">内容</label>
        <textarea class="w-full" id="content" name="content" rows="4" cols="50" required><%= content %></textarea><br><br>
      </div>

      <div class="flex justify-around">
        <button class="normal-btn" onclick="cancel()" type="button">取消</button>
        <button class="normal-btn" type="submit">提交</button>
      </div>
    </form>
  </dialog>
  <% } %>
  <h1 class="text-2xl font-bold"><%= blog.title %>&nbsp;(by: <%= blog.author %>)</h1>
  <h2 class="text-lg"><%= blog.description %></h2>
  <p><%= content %></p>
</div>

<script>
  const blogEditDialog = document.getElementById('edit-blog-dialog');

  function edit() {
    blogEditDialog.showModal();
  }

  function cancel() {
    blogEditDialog.close();
  }
</script>
