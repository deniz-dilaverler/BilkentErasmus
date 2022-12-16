import "./App.css";
import Sidebar from "./Sidebar/Sidebar";
import LoginForm from "./Login/LoginForm";
import CoursesPage from "./Courses/CoursesPage";
import InstitutionsPage from "./Institutions/InstitutionsPage";
import ApplicationsPage from "./Applications/ApplicationsPage";
import StudentApplicationsPage from "./StudentApplication/StudentApplicationsPage";
import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {

  // get user role since pages change according to user role
  var role;
  const getRole = () => {
      return false;
  }

  if ( getRole()) 
  {
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
  else if ( !getRole() )
  {
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
  }
  
}

export default App;
