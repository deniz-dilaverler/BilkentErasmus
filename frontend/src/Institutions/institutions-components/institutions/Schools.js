import SchoolItem from "./SchoolItem";
import './Schools.css';
import InstitutionCard from "../UI/InstitutionCard";
import SchoolFilter from "./SchoolFilter";
import React, {useState} from "react";
import Container from 'react-bootstrap/Container';

function Schools(props) {

    const [filteredSchool, setFilteredSchool] = useState('');

    const filterChangeHandler = selectedCountry => {
        console.log("Mirbaaaa");
        console.log(selectedCountry);
        setFilteredSchool(filteredSchool);
    };

    const filteredSchools = props.institutions.filter(school => {
        return school.country === filteredSchool
    })

    return(
            <Container className="schools">
                <div className="filter"><SchoolFilter onChangeFilter={filterChangeHandler} selected={filteredSchool}></SchoolFilter></div>
                {props.institutions.map((school) => 
                    <SchoolItem
                        key = {school.id}
                        name = {school.name}
                        country = {school.country}
                        language = {school.language}
                        quota = {school.quota}
                    />
                )}
            </Container>
    );
}
export default Schools;