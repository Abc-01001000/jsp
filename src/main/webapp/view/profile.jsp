<%--
  Created by IntelliJ IDEA.
  User: abc
  Date: 2024/10/27
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
  <div class="flex flex-col justify-between items-center m-3">
    <h1 class="m-3 text-2xl font-bold"> <%= session.getAttribute("username") %> </h1>
    <button class="px-2 py-1 rounded-lg hover:bg-black hover:text-white" id="edit-btn"> Edit </button>
  </div>
    <dialog id="register-dialog" class="rounded-2xl">
    <form 
      id="register-form" 
      action="http://localhost:8080/user-edit" 
      method="POST"
      class="normal-dialog"
    >
      <span class="p-6 text-lg font-bold">Edit</span>
      <div>
        <span id="check-username-span"></span>
        <div class="normal-input" id="r-username-div">
          <label for="r-username">Username:</label>
          <input type="text" name="username" id="r-username" oninput="checkUsername(this)" />
        </div>
        <div class="normal-input">
          <label for="r-password">Password:</label>
          <input type="password" name="password" id="r-password" />
        </div>
        <div class="normal-input">
          <label for="email">Email:</label>
          <input type="email" name="email" id="email" />
        </div>
      </div>
      <div class="flex justify-between items-center w-full">
        <button type="button" id="back" class="normal-btn">Back</button>
        <button type="submit" id="register" class="normal-btn">OK</button>
      </div>
    </form>
  </dialog>
  <hr />
  <jsp:include page="../components/blog.jsp"/>
</div>

<script>
  const editButton = document.getElementById('edit-btn');

  const registerDialog = document.getElementById('register-dialog');
  const backButton = document.getElementById('back');
  const registerButton = document.getElementById('register');

  const rUsernameDiv = document.getElementById('r-username-div');
  const rUsernameInput = document.getElementById('r-username');
  const checkUsernameSpan = document.getElementById('check-username-span');

  editButton.addEventListener('click', () => {
    registerDialog.showModal();
  });

  backButton.addEventListener('click', () => {
    registerDialog.close();
  });

  async function checkUsername(e) {
    const username = e.value;

    if (username.length === 0) {
      registerButton.disabled = true;
      if (!rUsernameDiv.classList.contains('border-black'))
        rUsernameDiv.classList.add('border-black');
      if (rUsernameDiv.classList.contains('border-red-600'))
        rUsernameDiv.classList.remove('border-red-600');
      if (rUsernameDiv.classList.contains('text-red-600'))
        rUsernameInput.classList.remove('text-red-600');
      if (checkUsernameSpan.classList.contains('text-red-600'))
        checkUsernameSpan.classList.remove('text-red-600');
      checkUsernameSpan.textContent = '';
      return;
    }

    try {
      const response = await fetch('/check-username', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          username: username,
        }),
        credentials: 'include'
      });

      if (!response.ok) {
        console.error(response.status);
        return;
      }

      const data = await response.json();

      if (data.exists) {
        registerButton.disabled = true;
        rUsernameDiv.classList.remove('border-black');
        rUsernameDiv.classList.add('border-red-600');
        rUsernameInput.classList.add('text-red-600');
        checkUsernameSpan.classList.add('text-red-600');
        checkUsernameSpan.textContent = username + ' already exists';
      } else {
        registerButton.disabled = false;
        rUsernameDiv.classList.add('border-black');
        rUsernameDiv.classList.remove('border-red-600');
        rUsernameInput.classList.remove('text-red-600');
        checkUsernameSpan.classList.remove('text-red-600');
        checkUsernameSpan.textContent = username + ' is available';
      }
    } catch (err) {
      console.error(err);
    }
  }
</script>
