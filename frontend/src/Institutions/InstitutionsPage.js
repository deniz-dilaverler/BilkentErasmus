import Schools from "./institutions-components/institutions/Schools";
import NewSchool from "./institutions-components/new-institution/NewSchool";
import React, {useState} from "react";
import './InstitutionsPage.css';

const initialInstitutions = [
  {
    id: 1,
    name: 'Bamberg University',
    country: 'Germany',
    language: 'English B2, German B2',
    quota: '2x5 months',
  },
  {
    id: 2,
    name: 'University of Saarland',
    country: 'Germany',
    language: 'English B2',
    quota: '2x5 months',
  },
  {
    id: 3,
    name: 'ESIEE Paris',
    country: 'France',
    language: '-',
    quota: '2x5 months',
  },
  {
    id: 4,
    name: 'Kingston',
    country: 'UK',
    language: 'English B2',
    quota: '2x5 months',
  },
  {
    id: 5,
    name: 'Kingston',
    country: 'UK',
    language: 'English B2',
    quota: '2x5 months',
  },
  {
    id: 6,
    name: 'Kingston',
    country: 'UK',
    language: 'English B2',
    quota: '2x5 months',
  },
  {
    id: 7,
    name: 'Kingston',
    country: 'UK',
    language: 'English B2',
    quota: '2x5 months',
  },
];


function InstitutionsMainPage() {
  const [institutions, setInstitutions] = useState(initialInstitutions);
  const addSchoolHandler = school => {
    setInstitutions(
      (prevInstitutions) => {
        return [school, ...prevInstitutions];
      }
    );
  }
  return (
    <div>
          <div className = "header"><h2>Partner Institutions</h2></div>
          <NewSchool onAddSchool={addSchoolHandler} ></NewSchool>
      <Schools institutions={institutions}></Schools>
    </div>
  );
}

export default InstitutionsMainPage;