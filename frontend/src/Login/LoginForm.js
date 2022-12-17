import React, { useState } from "react";
import { useEffect } from "react";
import "./Login.css";

function LoginForm() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");



  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await fetch("/api/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });
      const data = await response.json();

      if (data.error) {
        setError(data.error);
      } else {
        // store authentication information in the client's session
        sessionStorage.setItem("authToken", data.authToken);
        sessionStorage.setItem("username", data.username);
        setError("");
        window.location.pathname = "/dashboard";
      }
    } catch (error) {
      setError("An unexpected error occurred.");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="login-form">
      <label htmlFor="username" className="login-form__label">
        Username/ID:
      </label>
      <input
        type="text"
        id="username"
        value={username}
        onChange={(event) => setUsername(event.target.value)}
        className="login-form__input"
      />
      <label htmlFor="password" className="login-form__label">
        Password:
      </label>
      <input
        type="password"
        id="password"
        value={password}
        onChange={(event) => setPassword(event.target.value)}
        className="login-form__input"
      />
      <button type="submit" className="login-form__button">
        Login
      </button>
      {error && <p className="login-form__error">{error}</p>}
    </form>
  );
}

export default LoginForm;
