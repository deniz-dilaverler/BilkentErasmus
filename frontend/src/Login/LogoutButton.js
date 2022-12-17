import React from "react";
import "./Login.css";

function LogoutButton() {
  const handleLogout = () => {
    // remove the authentication information from the client's session
    sessionStorage.removeItem("authToken");
    sessionStorage.removeItem("username");

    // redirect the user to the login page
    window.location = "/";
  };

  return (
    <button onClick={handleLogout} className="logout-form__button">
      Log out
    </button>
  );
}

export default LogoutButton;
