import React, {useState} from "react";
import './NewSchool.css';
import SchoolForm from "./SchoolForm";

const NewSchool = (props) => {
    
    const saveSchoolDataHandler = (enteredSchoolData) => {


        try {
            const response = await fetch("/api/login", {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify({ enteredSchoolData }),
            });
            const data = await response.json();
      
            if (data.error) {
              setError(data.error);
            } else {
              console.log("oldu aşko yolladım <3")
            }
          } catch (error) {
            setError("An unexpected error occurred.");
          }
        };













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