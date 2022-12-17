import "./App.css";
import Sidebar from "./Sidebar/Sidebar";
import LoginForm from "./Login/LoginForm";
import CoursesPage from "./Courses/CoursesPage";
import InstitutionsPage from "./Institutions/InstitutionsPage";
import ApplicationsPage from "./Applications/ApplicationsPage";
import StudentApplicationsPage from "./StudentApplication/StudentApplicationsPage";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { useState, useEffect } from "react";

function App() {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Fetch the user's role from the back-end when the component mounts
  /**useEffect(() => {
    fetch("/user")
      .then((response) => response.json())
      .then((data) => {
        setUser(data);
        setLoading(false);
      });
  }, []);
  */

  /**if (loading) {
    return <p className = "spinner">Loading...</p>;
  }
  */

  var role = true;

  if (role === true) {
    return (
      <div className="App">
        <Sidebar />
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<LoginForm />} />
            <Route path="/instutitions" element={<InstitutionsPage />} />
            <Route path="/applications" element={<StudentApplicationsPage />} />
            <Route path="/courses" element={<CoursesPage />} />
          </Routes>
        </BrowserRouter>
      </div>
    );
  } else if (role === false) {
    return (
      <div className="App">
        <Sidebar />
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<LoginForm />} />
            <Route path="/instutitions" element={<InstitutionsPage />} />
            <Route path="/applications" element={<ApplicationsPage />} />
            <Route path="/courses" element={<CoursesPage />} />
          </Routes>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
