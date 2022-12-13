import "./App.css";
import Sidebar from "./Sidebar/Sidebar";
import LoginForm from "./Login/LoginForm";
import CoursesPage from "./Courses/CoursesPage";
import InstitutionsMainPage from "./Institutions/InstitutionsMainPage";
import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {
  return (
    <div className="App">
      <Sidebar />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<LoginForm />} />
          <Route path="/courses" element={<CoursesPage />} />
          <Route path="/instutitions" element={<InstitutionsMainPage />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
