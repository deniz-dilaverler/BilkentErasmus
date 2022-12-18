import "./App.css";
import Sidebar from "./Sidebar/Sidebar";
import LoginForm from "./Login/LoginForm";
import CoursesPage from "./Courses/CoursesPage";
import InstitutionsPage from "./Institutions/InstitutionsPage";
import ApplicationsPage from "./Applications/ApplicationsPage";
import StudentApplicationsPage from "./StudentApplication/StudentApplicationsPage";
import NonLoggedSidebar from "./Sidebar/NonLoggedSidebar";
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
        const decodedToken = jwt_decode(token);
        const role = decodedToken.roles;
        setRole(role);
        localStorage.setItem('role', JSON.stringify(role));
        window.location.pathname = "/instutitions";
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
      {!loggedIn && <NonLoggedSidebar loggedIn={loggedIn}
                setLoggedIn={setLoggedIn} />}
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
                role={role}
              />
            }
          />
          <Route
            path="/instutitions"
            element={
              <InstitutionsPage
                loggedIn={loggedIn}
                setLoggedIn={setLoggedIn}
                role={role}
              />
            }
          />
          <Route
            path="/applications"
            element={
              JSON.parse(localStorage.getItem('role')) !== null &&
              (JSON.parse(localStorage.getItem('role'))[0].authority === "ROLE_STUDENT") ? (
                <StudentApplicationsPage
                  loggedIn={loggedIn}
                  setLoggedIn={setLoggedIn}
                  role={role}
                />
              ) : (
                <ApplicationsPage
                  loggedIn={loggedIn}
                  setLoggedIn={setLoggedIn}
                  role={role}
                />
              )
            }
          />
          <Route
            path="/courses"
            element={
              <CoursesPage
                loggedIn={loggedIn}
                setLoggedIn={setLoggedIn}
                role={role}
              />
            }
          />
          <Route
            path="/coursesNonLogged"
            element={
              <CoursesPage
                loggedIn={loggedIn}
                setLoggedIn={setLoggedIn}
                role={role}
              />
            }
          />
          <Route
            path="/instutitionsNonLogged"
            element={
              <InstitutionsPage
                loggedIn={loggedIn}
                setLoggedIn={setLoggedIn}
                role={role}
              />
            }
          />
          <Route
            path="/instutitionsNonLogged"
            element={
              <NonLoggedSidebar
                loggedIn={loggedIn}
                setLoggedIn={setLoggedIn}
                role={role}
              />
            }
          />
        </Routes>  
      </BrowserRouter>
    </div>
  );
}

export default App;
