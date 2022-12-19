import { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';
import StudentAppItem from './StudentAppItem';
import './ModifyFaultyData.css'
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

function ModifyFaultyData(props) {

  // set states for modals
  const [open, setOpen] = useState(false);
  const [openSemester, setOpenSemester] = useState(false);

  // handle dialog (modal) open and closings:
    const handleAllClickOpen = () => {
        setOpen(true);
      };

      const handleClose = () => {
        setOpen(false);
      };

      const handleSemesterClickOpen = () => {
        setOpenSemester(true);
      };

      const handleSemesterClose = () => {
        setOpenSemester(false);
      };

      // cancel current application handler:
      // send to main:
  const cancelCurrentApplicationHandler = () => {
        const statusData = {
          statType: "CURRENT",
          no: props.no }
        props.cancelApplicationStatusData(statusData);
        console.log("Cancel current application!")
        setOpen(false);
        window.location.reload(false);
  }

  // change semester handler:
  // send to main:
  const changeSemesterHandler = () => {
    const statusData = {
      statType: "SEMESTER_CHANGE",
      no: props.no }
    props.cancelApplicationStatusData(statusData);
    console.log("CHANGE SEMESTER ULAN!!!")
    setOpenSemester(false);
    window.location.reload(false);
  }

  // return buttons and dialogs:
    return (
        <Container className='modify'>
          <Row>
            <Col>
              Currently selected semester is not applicable to this institution:
          </Col>
          </Row>
          <Row>
            <Col>
            <StudentAppItem
                        key = {props.key}
                        no = {props.no}
                        school = {props.school}
                        semester = {props.semester}
            />
          </Col>
          </Row>
          <Row>
            <Col></Col>
            <Col>
            <button onClick={handleAllClickOpen}>Cancel Current Application</button>
            </Col>
            <Col>
            <button onClick={handleSemesterClickOpen}>Change Semester</button>
            </Col>
            <Col></Col>
            <Dialog open={open} onClose={handleClose}>
            <DialogTitle >
                    Cancel Current Application?
                </DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Are you sure do you want to cancel the current application?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Disagree</Button>
                    <Button onClick={cancelCurrentApplicationHandler} autoFocus>
                        Agree
                    </Button>
                 </DialogActions>
                </Dialog>
                <Dialog open={openSemester} onClose={handleSemesterClose}>
            <DialogTitle >
                    Change Current Application Semester?
                </DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Are you sure do you want to change current application semester?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleSemesterClose}>Disagree</Button>
                    <Button onClick={changeSemesterHandler} autoFocus>
                        Agree
                    </Button>
                 </DialogActions>
                </Dialog>
                </Row>
        </Container>
      );
}

export default ModifyFaultyData;