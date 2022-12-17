import React, {useState} from "react";
import './NewSchool.css';
import SchoolForm from "./SchoolForm";

const NewSchool = (props) => {
    const [error, setError] = useState("");

    const saveSchoolDataHandler = (enteredSchoolData) => {
        const schoolData = {
            ...enteredSchoolData,
        };
        console.log(schoolData);
        props.onAddSchool(schoolData);
        setIsEditing(false);
    };

    const isCoordinator = () => {
        return true
    };

    const [isEditing, setIsEditing] = useState(false);
    const startEditingHandler = () => {
        setIsEditing(true);
    }

    const stopEditingHandler = () => {
        setIsEditing(false);
    }

    if ( isCoordinator() ) {
        return (<div className = "new-expense">
            {!isEditing && <button onClick={startEditingHandler}>Add New Institution</button>}
            {isEditing && <SchoolForm onSaveSchoolData={saveSchoolDataHandler} onCancel={stopEditingHandler}></SchoolForm>}
        </div>);
    }
    
}

export default NewSchool;