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
import { Theme, useTheme } from '@mui/material/styles';
import OutlinedInput from '@mui/material/OutlinedInput';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Select, { SelectChangeEvent } from '@mui/material/Select';

const languages = [
    'English B1',
    'English B2',
    'English C1',
    'German B1',
    'German B2',
    'Italian B1',
    'Italian B2',
    'French B1',
    'French B2',
  ];

    const ITEM_HEIGHT = 48;
    const ITEM_PADDING_TOP = 8;
    const MenuProps = {
    PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
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

    const countryChangeHandler = (event) => {
        setEnteredInstCountry(event.target.value)
    }
    const radioButtonChangeHandler = (event) => {
        setEnteredProgramType(event.target.value);
        //console.log(event.target.value)
    }

    const submitHandler = (event) => {
        event.preventDefault();
        if ( enteredInstName !== "" && enteredInstCountry !== "" )
        {
          console.log("Buraya giriyor mu?")
            const schoolData = {
                name: enteredInstName,
                languageRequirement: "dil",
                semester: "FALL",
                country: enteredInstCountry,
                allowance: enteredAllowance,
                coordinatorIds:[ 1],
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
    }

    function getStyles(name, language, theme) {
        return {
          fontWeight:
          language.indexOf(name) === -1
              ? theme.typography.fontWeightRegular
              : theme.typography.fontWeightMedium,
        };
      }

        const theme = useTheme();
        const [language, setLanguage] = React.useState([]);
  

        const handleChange = (event) => {
            const {
              target: { value },
            } = event;
            setLanguage(
              // On autofill we get a stringified value.
              typeof value === 'string' ? value.split(',') : value,
            );
            console.log("Here is person name:")
            console.log(language)
          };
    


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
                <Col>
                </Col>
            </Row>
            <Row>
                <Col className="school-form_actions">
                    <button type="button" onClick={props.onCancel}>Cancel</button>
                </Col>
                <Col>
                    <button type="submit" onClick={submitHandler}>Add Institution</button>
                </Col>
            </Row>
        </Container>
    ); 
    
};

export default SchoolForm;
