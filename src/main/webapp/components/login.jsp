<%--
  Created by IntelliJ IDEA.
  User: abc
  Date: 2024/10/26
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
  button {
    padding: 1vh;

    border: #000 solid 1px;
    border-radius: 5px;
    background: none;
    outline: none;
    cursor: pointer;
  }

  button:hover {
    background: #000;
    color: #eee;
  }

  #to-login {
    margin: 0 5vh;
  }

  #login-dialog, #register-dialog {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);

    width: 360px;
    min-height: 200px;
    padding: 20px;

    border: none;
    border-radius: 10px;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
  }

  #login-form, #register-form {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-direction: column;

    height: 100%;
    width: 100%;
  }

  .inputs>div {
    display: flex;
    justify-content: space-between;
    align-items: center;

    margin: 10px 0;
    padding: 0 10px;
    height: 30px;

    border: 1px solid black;
    border-radius: 15px;
    /*box-shadow: -5px -5px 5px rgba(0, 0, 0, 0.3);*/

    label {
      width: 82px;
      color: #666;
    }

    input {
      border: none;
      background-color: white!important;
      outline: none!important;

      font-size: large;
    }
  }

  .buttons {
    display: flex;
    justify-content: space-around;
    align-items: center;

    margin: 10px 0;
    width: 100%;
  }

  .exists-border {
    border: 1px solid red!important;
  }

  .exists-font {
    color: red!important;
  }

  #check-username-span {
    font-size: small;
    color: green;
  }
</style>

<button id="to-login">Login</button>
<dialog id="login-dialog">
  <form id="login-form" action="http://localhost:8080/login" method="post">
    <span>Login</span>
    <div class="inputs">
      <div class="username-div">
        <label for="username">Username:</label>
        <input type="text" name="username" id="username" />
      </div>
      <div class="password-div">
        <label for="password">Password:</label>
        <input type="password" name="password" id="password" />
      </div>
    </div>
    <div class="buttons">
      <button type="button" id="cancel">Cancel</button>
      <button type="button" id="to-register">No account?</button>
      <button type="submit" id="login">Login</button>
    </div>
  </form>
</dialog>

<dialog id="register-dialog">
  <form id="register-form" action="http://localhost:8080/register" method="post">
    <span>Register</span>
    <div class="inputs">
      <span id="check-username-span"></span>
      <div class="username-div" id="r-username-div">
        <label for="r-username">Username:</label>
        <input type="text" name="username" id="r-username" oninput="checkUsername(this)" />
      </div>
      <div class="password-div">
        <label for="r-password">Password:</label>
        <input type="password" name="password" id="r-password" />
      </div>
<%--        <div class="password-div">--%>
<%--          <label for="cr-password">Confirm:</label>--%>
<%--          <input type="password" name="cr-password" id="cr-password" />--%>
<%--        </div>--%>
      <div class="email-div">
        <label for="email">Email:</label>
        <input type="email" name="email" id="email" />
      </div>
    </div>
    <div class="buttons">
      <button type="button" id="back">Back</button>
      <button type="submit" id="register">Register</button>
    </div>
  </form>
</dialog>

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
    try {
      const username = e.value;

      if (username.length === 0) {
        rUsernameDiv.setAttribute('class', 'username-div');
        checkUsernameSpan.textContent = '';
        return;
      }

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
        return;
      }

      const data = await response.json();
      console.log(data);

      if (data.exists) {
        registerButton.disabled = true;
        rUsernameDiv.classList.add('exists-border');
        rUsernameInput.classList.add('exists-font');
        checkUsernameSpan.classList.add('exists-font');
        checkUsernameSpan.textContent = username + ' already exists';
      } else {
        registerButton.disabled = false;
        rUsernameDiv.classList.remove('exists-border');
        rUsernameInput.classList.remove('exists-font');
        checkUsernameSpan.classList.remove('exists-font');
        checkUsernameSpan.textContent = username + ' is available';
      }
    } catch (err) {
      console.error(err);
    }
  }
</script>
