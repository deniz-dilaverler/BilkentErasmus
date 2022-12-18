import React, { useState } from "react";
import './SchoolForm.css';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';
import { useTheme } from '@mui/material/styles';
import OutlinedInput from '@mui/material/OutlinedInput';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Slide from '@mui/material/Slide';

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

// for multiple selection of languages:
const languages = [
  'English B1 ',
  'English B2 ',
  'English C1 ',
  'German B1 ',
  'German B2 ',
  'Italian B1 ',
  'Italian B2 ',
  'French B1 ',
  'French B2 ',
];

// to style language multiselect
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
  // states to get form input:
  const [enteredInstName, setEnteredInstName] = useState(''); // institution name
  const [enteredInstCountry, setEnteredInstCountry] = useState(''); // country
  const [language, setLanguage] = React.useState([]); // language
  const [enteredInstQuota, setEnteredInstQuota] = useState(''); // quota
  const [enteredProgramType, setEnteredProgramType] = useState(''); // program type (erasmus or exchange)
  const [enteredAllowance, setEnteredAllowance] = useState(''); // allowance
  const [enteredSemester, setEnteredSemester] = useState('FALL'); // semester

  // if there are empty fields, it warns the user with this state!
  const [open, setOpen] = React.useState(false);
  const handleClose = () => {
    setOpen(false);
  };


  // set name:
  const institutionChangeHandler = (event) => {
    setEnteredInstName(event.target.value);
  };

  // set quota
  const quotaChangeHandler = (event) => {
    setEnteredInstQuota(event.target.value);
  };

  // set allowance
  const allowanceChangeHandler = (event) => {
    setEnteredAllowance(event.target.value);
  }

  // set country
  const countryChangeHandler = (event) => {
    setEnteredInstCountry(event.target.value)
  }

  // set program type
  const radioButtonChangeHandler = (event) => {
    setEnteredProgramType(event.target.value);
  }

  // set program semester:
  const semesterHandler = (event) => {
    setEnteredSemester(event.target.value);
  }

  // this is the submit handler method
  // send new object to the database
  // also, display the new object in the table, too
  const submitHandler = async (event) => {
    event.preventDefault();
    // if program type is erasmus:
    if ( enteredProgramType === "erasmus" )
    {
      // if they are not null
      if (enteredInstName !== "" && enteredInstCountry !== "" && enteredInstQuota !== "" && enteredProgramType !== "" && enteredAllowance !== "" && language.length >= 1 )
      {
        const schoolData = {
          name: enteredInstName,
          languageRequirement: language,
          semester: enteredSemester,
          country: enteredInstCountry,
          allowence: enteredAllowance,
          coordinatorIds: 1,
          quota: enteredInstQuota,
        }
        // send it for display, and set values to null again in form:
        props.onSaveSchoolData(schoolData);
        setLanguage('');
        setEnteredInstName('');
        setEnteredInstCountry('');
        setEnteredInstQuota('');
        setEnteredAllowance('');

        // for sending it to database:
        var name = enteredInstName
        var languageRequirement = language[0].toString()
        console.log(language)
        console.log(language[0])
        console.log(language[0].toString())
        var semester = enteredSemester
        var country = enteredInstCountry
        var allowance = enteredAllowance
        var quota = enteredInstQuota
        var coordinatorId = 1

        // create JSON object, and send it:
        const response = await fetch("http://localhost:8080/university/erasmus", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ name, languageRequirement, semester, country, allowance, quota, coordinatorId })
        });
      }
      // if they are NULL, do not send, refill form!
      else 
      {
        setOpen(true);
      }
    }
    // if program type is this time exchange --> do not send to database
    else if ( enteredProgramType === "exchange" )
    {
      // nullcheck
      if (enteredInstName !== "" && enteredInstCountry !== "" && enteredInstQuota !== "" && enteredProgramType !== "" && language.length >= 1 )
      {
        const schoolData = {
          name: enteredInstName,
          languageRequirement: language,
          semester: enteredSemester,
          country: enteredInstCountry,
          allowence: enteredAllowance,
          coordinatorIds: 1,
          quota: enteredInstQuota,
        }
        // send it for display, and set values to null again in form:
        props.onSaveSchoolData(schoolData);
        setLanguage('');
        setEnteredInstName('');
        setEnteredInstCountry('');
        setEnteredInstQuota('');
      }
      // if NULL, alert user
      else 
      {
        
      }
    }
    // something must have went wrong :( , alert user
    else {

    }
  } // end of form submit handler



  // for styling multiselect:
  function getStyles(name, language, theme) {
    return {
      fontWeight:
        language.indexOf(name) === -1
          ? theme.typography.fontWeightRegular
          : theme.typography.fontWeightMedium,
    };
  }

  // language theme:
  const theme = useTheme();

  // handle language selection
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

  // form with language multipleselect, semester select, program type radio button, input fields      
  return (
    <Container className="school-form">
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
        <Col>
          <label>Semester</label>
          <select id="language" value={enteredSemester} onChange={semesterHandler}>
            <option value="FALL">Fall</option>
            <option value="SPRING">Spring</option>
            <option value="BOTH">Both</option>
          </select>
        </Col>
        <Col>
          <div className="select">
            <FormControl sx={{ m: 1, width: 300 }}>
              <InputLabel id="demo-multiple-name-label">Languages</InputLabel>
              <Select
                labelId="demo-multiple-name-label"
                id="demo-multiple-name"
                multiple
                value={language}
                onChange={handleChange}
                input={<OutlinedInput label="Name" />}
                MenuProps={MenuProps}
                sx={{ color: "#F0F8FF" }}
              >
                {languages.map((language) => (
                  <MenuItem
                    key={language}
                    value={language}
                    style={getStyles(language, language, theme)}
                  >
                    {language}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </div>
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
          {enteredProgramType === "erasmus" && <div className="new-expense__control">
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
      <Dialog
        open={open}
        TransitionComponent={Transition}
        keepMounted
        onClose={handleClose}
        aria-describedby="alert-dialog-slide-description"
      >
        <DialogTitle>{"There are empty fields in the form!"}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-slide-description">
            Please fill all information before submitting the form
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>OK</Button>
        </DialogActions>
      </Dialog>
    </Container>
  );

};

export default SchoolForm;
