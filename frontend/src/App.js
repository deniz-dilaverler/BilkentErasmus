import "./App.css";
import Sidebar from "./Sidebar/Sidebar";
import LoginForm from "./Login/LoginForm";
import CoursesPage from "./Courses/CoursesPage";
import InstitutionsPage from "./Institutions/InstitutionsPage";
import ApplicationsPage from "./Applications/ApplicationsPage";
import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {
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

export default App;
