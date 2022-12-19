import React, { useState } from "react";
import { useEffect } from "react";
import "./Login.css";
import jwt_decode from 'jwt-decode';

function LoginForm({username, setUsername, password, setPassword, error, handleSubmit}) {

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
