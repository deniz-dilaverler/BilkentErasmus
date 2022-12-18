import SchoolItem from "./SchoolItem";
import './Schools.css';
import InstitutionCard from "../UI/InstitutionCard";
import SchoolFilter from "./SchoolFilter";
import React, {useState} from "react";
import Container from 'react-bootstrap/Container';

function Schools(props) {

    const [filteredSchool, setFilteredSchool] = useState('All');

    const filterChangeHandler = selectedCountry => {
        console.log("Mirbaaaa");
        console.log(selectedCountry);
        setFilteredSchool(selectedCountry);
    };

    const filteredSchools = props.institutions.filter(school => {
        if ( filteredSchool !== "All" ) {
            return school.country === filteredSchool
        }
        else if ( filteredSchool === "All" ) {
            return props.institutions
        }
        
    })
    return(
            <Container className="schools">
                <div><SchoolFilter onChangeFilter={filterChangeHandler} selected={filteredSchool}></SchoolFilter></div>
                {filteredSchools.map((school) => 
                    <SchoolItem
                        key = {school.id}
                        name = {school.name}
                        country = {school.country}
                        language = {school.languageRequirement}
                        semester = {school.semester }
                        quota = {school.programs[0].quota}
                        department = {school.programs[0].department}
                        programType = {school.isErasmus ? "erasmus" : "exchange"}
                        allowance = {school.allowance}
                    />
                )}
            </Container>
    );
}
export default Schools;