import "./App.css";
import Sidebar from "./Sidebar/Sidebar";
import LoginForm from "./Login/LoginForm";
import CoursesPage from "./Courses/CoursesPage";
import InstitutionsPage from "./Institutions/InstitutionsPage";
import ApplicationsPage from "./Applications/ApplicationsPage";
import StudentApplicationsPage from "./StudentApplication/StudentApplicationsPage";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { useState, useEffect } from "react";
import jwt_decode from "jwt-decode";

function App() {
  const [userRole, setUserRole] = useState(null);
  const [loading, setLoading] = useState(true);
  const [loggedIn, setLoggedIn] = useState(false);

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const [role, setRole] = useState(null);

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/api/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });
      const data = await response.json();
      if (response.ok) {
        setLoggedIn(true);
        const token = data.authToken;
        setRole(jwt_decode(token).roles);
        window.location.pathname = "/courses";
      } else {
        throw new Error(response.statusText);
      }
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="App">
      {loggedIn && <Sidebar />}
      <BrowserRouter>
        <Routes>
          <Route
            path="/"
            element={
              <LoginForm
                username={username}
                setUsername={setUsername}
                password={password}
                setPassword={setPassword}
                error={error}
                setError={setError}
                handleSubmit={handleSubmit}
              />
            }
          />
          <Route
            path="/instutitions"
            element={
              <InstitutionsPage loggedIn={loggedIn} setLoggedIn={setLoggedIn} />
            }
          />
          <Route
            path="/applications"
            element={
              !(role === "ROLE_COORDINATOR") ? (
                <StudentApplicationsPage
                  loggedIn={loggedIn}
                  setLoggedIn={setLoggedIn}
                />
              ) : (
                <ApplicationsPage
                  loggedIn={loggedIn}
                  setLoggedIn={setLoggedIn}
                />
              )
            }
          />
          <Route
            path="/courses"
            element={
              <CoursesPage loggedIn={loggedIn} setLoggedIn={setLoggedIn} />
            }
          />
        </Routes>
      </BrowserRouter>
    </div>
  );
}
export default App;
