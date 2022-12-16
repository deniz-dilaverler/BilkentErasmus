
import React, {useState} from "react";
import StudentApplications from "./StudentApplications"

// Mock data, database'den çek
const studentApplications = [
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

  return (
    <div>
      <StudentApplications applications={studentApplications}></StudentApplications>
    </div>
  );
}

export default StudentApplicationsPage;