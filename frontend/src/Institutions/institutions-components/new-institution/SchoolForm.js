import React, {useState} from "react";
import './SchoolForm.css';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';
import Multiselect from 'multiselect-react-dropdown';
import Select from 'react-select';

const languages = {
    options: [{name: 'English B1', id: 1},{name: 'English B2', id: 2},{name: 'English C1', id: 3},{name: 'German B1', id: 4},{name: 'German B2', id: 5},
    {name: 'Italian B1', id: 6}, {name: 'Italian B2', id: 7}, {name: 'French B1', id: 8}, {name: 'French B2', id: 9}]
};

const SchoolForm = (props) => {
    const [enteredInstName, setEnteredInstName] = useState('');
    const [enteredInstCountry, setEnteredInstCountry] = useState('');
    const [enteredInstLanguage, setEnteredInstLanguage] = useState('');
    const [enteredInstQuota, setEnteredInstQuota] = useState('');
    const [enteredProgramType, setEnteredProgramType] = useState('');
    const [enteredAllowance, setEnteredAllowance] = useState('');


    const institutionChangeHandler = (event) => {
        setEnteredInstName(event.target.value);
    };

    const quotaChangeHandler = (event) => {
        setEnteredInstQuota(event.target.value);
    };
    
    const allowanceChangeHandler = (event) => {
        setEnteredAllowance(event.target.value);
    }
    const onSelect = (event) => {
        console.log("hi");
        //setEnteredInstLanguage(event.target.value);
        console.log(event.target.value)
        //console.log(languages.selectedValue);
        
    };

    const onRemove = (selectedList, selectedItem) => {
        console.log("hi");
        //setEnteredInstLanguage(enteredInstLanguage + event.target.value);
    };

    const countryChangeHandler = (event) => {
        setEnteredInstCountry(event.target.value)
    }
    const radioButtonChangeHandler = (event) => {
        setEnteredProgramType(event.target.value);
        //console.log(event.target.value)
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
            props.onSaveSchoolData(-1); // For NULL Check
        }
        

        
    };
    /*
    <Multiselect
                options={languages.options} // Options to display in the dropdown
                selectedValues={languages.selectedValue} // Preselected value to persist in dropdown
                onSelect={onSelect} // Function will trigger on select event
                onRemove={onRemove} // Function will trigger on remove event
                displayValue="name" // Property name to display in the dropdown options
            />

            
    <div className = "new-expense__control">
                <label>Language Requirements</label>
                <input type="text" value={enteredInstLanguage} onChange={languageChangeHandler}></input>
            </div> */

    return(
        <Container className = "school-form">
            <form onSubmit={submitHandler}></form>
            <Row>
                <Col>
                <FormControl>
                    <FormLabel id="demo-controlled-radio-buttons-group">Program Type</FormLabel>
                        <RadioGroup
                            aria-labelledby="demo-controlled-radio-buttons-group"
                            name="controlled-radio-buttons-group"
                            value={enteredProgramType}
                            onChange={radioButtonChangeHandler}>                                   
                            <FormControlLabel value="erasmus" control={<Radio />} label="Erasmus" />
                            <FormControlLabel value="exchange" control={<Radio />} label="Exchange" />
                        </RadioGroup>
                </FormControl>
                </Col>
            </Row>
            <Row>
                <Col>
                    <label>Institution Name</label>
                    <input type="text" value={enteredInstName} onChange={institutionChangeHandler}></input>
                </Col>
                <Col>
                        <label>Quota</label>
                        <input type="text" value={enteredInstQuota} onChange={quotaChangeHandler}></input>
                </Col>
            </Row>
            <Row>
                <Col>
                        <label>Country</label>
                        <input type="text" value={enteredInstCountry} onChange={countryChangeHandler}></input>
                </Col>
                <Col>
                {enteredProgramType === "erasmus" && <div className = "new-expense__control">
                <label>Allowance</label>
                <input type="text" value={enteredAllowance} onChange={allowanceChangeHandler}></input>
                </div>}
                </Col>
            </Row>
            <Row>
                <Col className="school-form_actions">
                    <button type="button" onClick={props.onCancel}>Cancel</button>
                </Col>
                <Col>
                    <button type="submit">Add Institution</button>
                </Col>
            </Row>
        </Container>
    ); 
        /*

        <div className = "new-expense__actions">
            <button type="button" onClick={props.onCancel}>Cancel</button>
            <button type="submit">Add Institution</button>
        </div>
        </Container>
    </form>
    */
};

export default SchoolForm;