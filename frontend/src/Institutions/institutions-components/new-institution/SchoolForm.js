import React, {useState} from "react";
import './SchoolForm.css';

const SchoolForm = (props) => {
    const [enteredInstName, setEnteredInstName] = useState('');
    const [enteredInstCountry, setEnteredInstCountry] = useState('');
    const [enteredInstLanguage, setEnteredInstLanguage] = useState('');
    const [enteredInstQuota, setEnteredInstQuota] = useState('');

    const institutionChangeHandler = (event) => {
        setEnteredInstName(event.target.value);
    };

    const quotaChangeHandler = (event) => {
        setEnteredInstQuota(event.target.value);
    };

    const languageChangeHandler = (event) => {
        setEnteredInstLanguage(event.target.value);
    };

    const countryChangeHandler = (event) => {
        setEnteredInstCountry(event.target.value)
    }

    const submitHandler = (event) => {
        event.preventDefault();
        if ( enteredInstName !== "" && enteredInstCountry !== "" && enteredInstLanguage !== "" )
        {
            const schoolData = {
                name: enteredInstName,
                country: enteredInstCountry,
                language: enteredInstLanguage,
                quota: enteredInstQuota,
                key: Math.random(),
            };
            props.onSaveSchoolData(schoolData);
            setEnteredInstLanguage('');
            setEnteredInstName('');
            setEnteredInstCountry('');
            setEnteredInstQuota('');
        }
        else
        {

        }
        

        
    };

    return <form onSubmit={submitHandler}>
        <div className = "new-expense__controls">
            <div className = "new-expense__control">
                <label>Institution Name</label>
                <input type="text" value={enteredInstName} onChange={institutionChangeHandler}></input>
            </div>
            <div className = "new-expense__control">
                <label>Quota</label>
                <input type="text" value={enteredInstQuota} onChange={quotaChangeHandler}></input>
            </div>
            <div className = "new-expense__control">
                <label>Language Requirements</label>
                <input type="text" value={enteredInstLanguage} onChange={languageChangeHandler}></input>
            </div>
            <div className = "new-expense__control">
                <label>Country</label>
                <input type="text" value={enteredInstCountry} onChange={countryChangeHandler}></input>
            </div>
        </div>
        <div className = "new-expense__actions">
            <button type="button" onClick={props.onCancel}>Cancel</button>
            <button type="submit">Add Institution</button>
        </div>
    </form>
};

export default SchoolForm;