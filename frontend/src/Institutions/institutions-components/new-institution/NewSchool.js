import React, {useState} from "react";
import './NewSchool.css';
import SchoolForm from "./SchoolForm";

const NewSchool = (props) => {
    const [error, setError] = useState("");

    const saveSchoolDataHandler = async (event, enteredSchoolData) => {
        event.preventDefault()
        console.log("yolladım")
            try {
                const response = await fetch("http://localhost:8080/university/erasmus", {
                  method: "POST",
                  headers: {
                    "Content-Type": "application/json",
                  },
                  body: JSON.stringify({enteredSchoolData}),
                });
                const data = await response.json();
          
                if (data.error) {
                  setError(data.error);
                } else {
                  // store authentication information in the client's session
                console.log("yolladım")
                  setError("");
                }
              } catch (error) {
                setError("An unexpected error occurred.");
              }

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