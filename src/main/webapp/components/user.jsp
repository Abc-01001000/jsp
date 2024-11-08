<%@ page import="com.uwu.wdnmd.model.User" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: abc
  Date: 2024/11/7
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<ul>
  <%
    ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
    if (users != null && !users.isEmpty()) {
      for (User user : users) {
  %>
    <li class="flex justify-around items-center">
      <div class="m-6 p-3 w-4/5 rounded-lg shadow-lg hover:shadow-xl hover:cursor-pointer" onclick="doSomething('<%= user.user_id %>')">
        <%= user.user_id %> - <%= user.username %> - <%= user.role %>
      </div>
      <div class="flex justify-around w-1/5">
        <button class="red-btn" onclick="doDelete('<%= user.user_id %>')">&#x2192;</button>
      </div>
    </li>
  <%
      }
    } else {
  %>
    <span>There is no user.</span>
  <% } %>
</ul>

<script>
  function doDelete(id) {
    if (!confirm('Are you sure?'))
      return;
    window.location.href = '/user-delete?userId=' + id;
  }

</script>