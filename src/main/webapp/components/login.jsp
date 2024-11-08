<%--
  Created by IntelliJ IDEA.
  User: abc
  Date: 2024/10/26
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<span>
  <button 
    id="to-login" 
    class="normal-btn"
  >Login</button>

  <dialog id="login-dialog" class="rounded-2xl">
    <form 
      id="login-form" 
      action="http://localhost:8080/login" 
      method="post"
      class="normal-dialog"
    >
      <span class="p-6 text-lg font-bold">Login</span>
      <div>
        <div class="normal-input">
          <label for="username">Username:</label>
          <input type="text" name="username" id="username" />
        </div>
        <div class="normal-input">
          <label for="password">Password:</label>
          <input type="password" name="password" id="password" />
        </div>
      </div>
      <div class="flex justify-between items-center w-full">
        <button type="button" id="cancel" class="normal-btn">Cancel</button>
        <button type="button" id="to-register" class="normal-btn">No account?</button>
        <button type="submit" id="login" class="normal-btn">Login</button>
      </div>
    </form>
  </dialog>

  <dialog id="register-dialog" class="rounded-2xl">
    <form 
      id="register-form" 
      action="http://localhost:8080/register" 
      method="post"
      class="normal-dialog"
    >
      <span class="p-6 text-lg font-bold">Register</span>
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
        <button type="submit" id="register" class="normal-btn">Register</button>
      </div>
    </form>
  </dialog>
</span>

<script>
  const toLoginButton = document.getElementById('to-login');

  const loginDialog = document.getElementById('login-dialog');
  const cancelButton = document.getElementById('cancel');
  const loginButton = document.getElementById('login');
  const toRegisterButton = document.getElementById('to-register');

  const registerDialog = document.getElementById('register-dialog');
  const backButton = document.getElementById('back');
  const registerButton = document.getElementById('register');

  const rUsernameDiv = document.getElementById('r-username-div');
  const rUsernameInput = document.getElementById('r-username');
  const checkUsernameSpan = document.getElementById('check-username-span');

  toLoginButton.addEventListener('click', () => {
    loginDialog.showModal();
  });

  cancelButton.addEventListener('click', () => {
    loginDialog.close();
  });

  toRegisterButton.addEventListener('click', () => {
    loginDialog.close();
    registerDialog.showModal();
  });

  backButton.addEventListener('click', () => {
    registerDialog.close();
    loginDialog.showModal();
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
