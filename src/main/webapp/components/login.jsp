<%--
  Created by IntelliJ IDEA.
  User: abc
  Date: 2024/10/26
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
  </style>
</head>
<body>
  <button id="to-login">Login</button>
  <dialog id="login-dialog">
    <form id="login-form" action="http://localhost:8080/login-servlet" method="post">
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
    <form id="register-form" action="http://localhost:8080/register-servlet" method="post">
      <span>Register</span>
      <div class="inputs">
        <div class="username-div">
          <label for="r-username">Username:</label>
          <input type="text" name="r-username" id="r-username" />
        </div>
        <div class="password-div">
          <label for="r-password">Password:</label>
          <input type="password" name="r-password" id="r-password" />
        </div>
        <div class="password-div">
          <label for="cr-password">Confirm:</label>
          <input type="password" name="cr-password" id="cr-password" />
        </div>
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
</body>
<script>
  const toLoginButton = document.getElementById('to-login');

  const loginDialog = document.getElementById('login-dialog');
  const cancelButton = document.getElementById('cancel');
  const loginButton = document.getElementById('login');
  const toRegisterButton = document.getElementById('to-register');

  const registerDialog = document.getElementById('register-dialog');
  const backButton = document.getElementById('back');
  const registerButton = document.getElementById('register');

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
</script>
</html>
