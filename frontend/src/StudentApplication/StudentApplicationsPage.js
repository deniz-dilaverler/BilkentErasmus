
import React, {useState, useEffect} from "react";
import StudentApplications from "./StudentApplications"

// Mock data, database'den çek
const studentApplicationsMockData = [
  {
    no: 1,
    school: 'Bamberg University',
    semester: 'Spring',
  },
  {
    no: 2,
    school: 'University of Saarland',
    semester: 'Spring',
  },
  {
    no: 3,
    school: 'ESIEE Paris',
    semester: 'Spring',
  },
  {
    no: 4,
    school: 'Kingston',
    semester: 'Spring',
  },
  {
    no: 5,
    school: 'Kingston',
    semester: 'Spring',
  }
];



function StudentApplicationsPage() {

      // student applications
      const [studentApplications, setStudentApplications] = useState();
      // FETCH STUDENT APPLICATIONS!
      useEffect(() => {
        fetch("http://localhost:8080/application/erasmus/3")
          .then((response) => response.json())
          .then((studentApplications) => setStudentApplications(studentApplications));
      }, []);
      if ( !studentApplications )
        return null;

      console.log("Student applications")
      console.log(studentApplications)

  return (
    <div>
      <StudentApplications applications={studentApplications}></StudentApplications>
    </div>
  );
}

export default StudentApplicationsPage;