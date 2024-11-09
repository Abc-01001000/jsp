<%--
  Created by IntelliJ IDEA.
  User: abc
  Date: 2024/11/9
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
  <form 
    action="http://localhost:8080/blog-add" 
    method="POST"
    class="w-4/5 mx-auto p-6 rounded-2xl shadow-2xl"
  >
    <div class="normal-input">
      <label for="title">标题</label>
      <input class="w-full" type="text" id="title" name="title" required><br><br>
    </div>

    <div class="normal-input">
      <label for="description">描述</label>
      <input class="w-full" type="text" id="description" name="description" required><br><br>
    </div>

    <div class="normal-input">
      <label for="content">内容</label>
      <textarea class="w-full" id="content" name="content" rows="4" cols="50" required></textarea><br><br>
    </div>

    <div class="flex justify-around">
      <button class="normal-btn" type="submit">提交</button>
    </div>
  </form>
</div>
