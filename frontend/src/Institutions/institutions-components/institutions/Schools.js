import SchoolItem from "./SchoolItem";
import './Schools.css';
import SchoolFilter from "./SchoolFilter";
import React, {useState} from "react";
import Container from 'react-bootstrap/Container';

function Schools(props) {

    // state for filtering
    const [filteredSchool, setFilteredSchool] = useState('All');

    // select filtered country
    const filterChangeHandler = selectedCountry => {
        setFilteredSchool(selectedCountry);
    };

    // filter schools:
    const filteredSchools = props.institutions.filter(school => {
        if ( filteredSchool !== "All" ) {
            return school.country === filteredSchool
        }
        else if ( filteredSchool === "All" ) {
            console.log(school)
            return school !== undefined
        }
        
    })
    // return 
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
                        programs = {school.programs}
                        programType = {school.isErasmus ? "erasmus" : "exchange"}
                        allowance = {school.allowance}
                    />
                )}
            </Container>
    );
}
export default Schools;